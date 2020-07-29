/*
 * Copyright (c) 2020 Mastercard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Test class of XmlSignUtil
 */

package com.mastercard.ap.security.bah.utility;

import com.mastercard.ap.security.bah.utility.info.ReferenceSignInfo;
import com.mastercard.ap.security.bah.utility.info.SignatureInfo;
import com.mastercard.ap.security.bah.utility.info.SignatureKeyInfo;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.x509.XMLX509SKI;
import org.apache.xml.security.utils.XMLUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class XmlSignUtilTest {
    private X509Certificate x509Certificate;
    private PrivateKey privateKey;

    @Before
    public void setUp() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore ks = KeyStore.getInstance("JKS");
        InputStream keyStoreStream = this.getClass().getResourceAsStream("/keystore.jks");

        ks.load(keyStoreStream, "68eacb97d86f0c4621fa2b0e17cabd8c".toCharArray());
        x509Certificate = (X509Certificate) ks.getCertificate("test");

        privateKey = (PrivateKey) ks.getKey("test", "68eacb97d86f0c4621fa2b0e17cabd8c".toCharArray());

    }


    @After
    public void tearDown() {
    }

    @Test
    /**
     * Sign the document and verify it
     */
    public void signAndVerifyTest() throws Exception {
        Document signedDocument = getSignedDocument();
        Assert.assertTrue("Sign verification failed", XmlSignUtil.verify(signedDocument, x509Certificate.getPublicKey()));
        XMLUtils.outputDOM(signedDocument, System.out);
    }

    @Test
    /**
     * Sign the document and then try to verify with wrong public key and assert failure in verification
     */
    public void signAndVerifyWithWrongPublicKeyTest() throws Exception {
        Document signedDocument = getSignedDocument();

        //Verify with wrong public key and verification should fail
        SecureRandom random = new SecureRandom();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, random);
        KeyPair keyPair = generator.generateKeyPair();

        Assert.assertFalse("Sign verification passed", XmlSignUtil.verify(signedDocument, keyPair.getPublic()));
        XMLUtils.outputDOM(signedDocument, System.out);
    }

    @Test
    /**
     * Sign the document and tamper the payload and assert failure in verification
     */
    public void signAndVerifyWithWhenSignedPayloadTamperedTest() throws Exception {
        Document signedDocument = getSignedDocument();

        //Tamper the payload after signing it by added one Test Element in Document
        NodeList nodeList = signedDocument.getElementsByTagNameNS("*", "Document");
        Element element = (Element) nodeList.item(0);

        Element newElement = signedDocument.createElementNS(element.getNamespaceURI(), "Test");

        newElement.setPrefix(element.getPrefix());
        element.appendChild(newElement);

        Assert.assertFalse("Sign verification passed", XmlSignUtil.verify(signedDocument, x509Certificate.getPublicKey()));
        XMLUtils.outputDOM(signedDocument, System.out);
    }

    private Document getSignedDocument() throws ParserConfigurationException, SAXException, IOException, XMLSecurityException, XPathExpressionException, XPathFactoryConfigurationException {
        InputStream sourceDocument = this.getClass().getResourceAsStream("/source-unsigned.xml");
        Document unSignedDocument = XMLUtils.read(sourceDocument);

        SignatureKeyInfo signatureKeyInfo = SignatureKeyInfo.builder()
                .privateKey(privateKey)
                .skiIdBytes(getSKIBytesFromCert(x509Certificate))
                .build();
        ReferenceSignInfo referenceSignInfo = ReferenceSignInfo.builder()
                .digestMethodAlgorithm("http://www.w3.org/2001/04/xmlenc#sha256")
                .transformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#")
                .build();
        SignatureInfo signatureInfo = SignatureInfo.builder()
                .appHdrReferenceSignInfo(referenceSignInfo)
                .documentReferenceSignInfo(referenceSignInfo)
                .keyReferenceSignInfo(referenceSignInfo)
                .signatureCanonicalizationMethodAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#")
                .signatureExclusionTransformer("http://www.w3.org/2000/09/xmldsig#enveloped-signature")
                .signatureMethodAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256")
                .build();
        return XmlSignUtil.sign(unSignedDocument, signatureInfo, signatureKeyInfo);
    }

    /**
     * Method getSKIBytesFromCert
     *
     * @param cert
     * @return ski bytes from the given certificate
     * @throws XMLSecurityException
     * @see java.security.cert.X509Extension#getExtensionValue(java.lang.String)
     */
    private byte[] getSKIBytesFromCert(X509Certificate cert)
            throws XMLSecurityException {

        if (cert.getVersion() < 3) {
            Object exArgs[] = {Integer.valueOf(cert.getVersion())};
            throw new XMLSecurityException("certificate.noSki.lowVersion", exArgs);
        }

        /*
         * Gets the DER-encoded OCTET string for the extension value
         * (extnValue) identified by the passed-in oid String. The oid
         * string is represented by a set of positive whole numbers
         * separated by periods.
         */
        byte[] extensionValue = cert.getExtensionValue(XMLX509SKI.SKI_OID);
        if (extensionValue == null) {
            throw new XMLSecurityException("certificate.noSki.null");
        }

        /**
         * Strip away first four bytes from the extensionValue
         * The first two bytes are the tag and length of the extensionValue
         * OCTET STRING, and the next two bytes are the tag and length of
         * the ski OCTET STRING.
         */
        byte skidValue[] = new byte[extensionValue.length - 4];

        System.arraycopy(extensionValue, 4, skidValue, 0, skidValue.length);


        return skidValue;
    }


}
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
 * The document resolver to document resource if uri is null in reference validation
 */

package com.mastercard.ap.security.bah.utility.resolvers;

import com.mastercard.ap.security.bah.utility.context.DSNamespaceContext;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;


public class XmlSignDocumentResolver extends ResourceResolverSpi {

    private final static Logger LOG = LoggerFactory.getLogger(XmlSignDocumentResolver.class);

    private final String expression = String.format("//*[local-name()='%s']", "Document");


    private Document doc;

    public XmlSignDocumentResolver(Document doc) {
        this.doc = doc;
    }

    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context) {
        Node selectedElem = null;
        if (null == context.uriToResolve && doc != null) {
            NodeList documentNodes;
            try {
                XPathFactory xpf = new net.sf.saxon.xpath.XPathFactoryImpl();
                XPath xpath = xpf.newXPath();
                xpath.setNamespaceContext(new DSNamespaceContext());
                documentNodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
            } catch (Exception e) {
                LOG.error("Error occurred " + e);
                throw new SecurityException("Error occurred in document resolver:", e);
            }
            selectedElem = (Element) documentNodes.item(0);
            if (selectedElem == null) {
                return null;
            }
            XMLSignatureInput result = new XMLSignatureInput(selectedElem);
            result.setSecureValidation(context.secureValidation);
            result.setExcludeComments(true);
            result.setMIMEType("text/xml");
            result.setSourceURI(null);
            return result;
        }
        return null;
    }

    @Override
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (null == context.uriToResolve && doc != null) {
            return true;
        }
        return false;
    }

}

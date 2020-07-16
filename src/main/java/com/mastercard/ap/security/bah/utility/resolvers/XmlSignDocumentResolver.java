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

/**
 * The document resolver to document resource if uri is null in reference validation
 */
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

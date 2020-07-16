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
 * The document resolver to AppHdr resource if uri is "" in reference validation
 */

package com.mastercard.ap.security.bah.utility.resolvers;

import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static com.mastercard.ap.security.bah.utility.context.Constants.BAH_NAME;


public class XmlSignBAHResolver extends ResourceResolverSpi {


    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context) {
        Document doc = context.attr.getOwnerElement().getOwnerDocument();
        Node selectedElem = null;
        if (context.uriToResolve.equals("")) {
            NodeList bahNodes = doc.getElementsByTagNameNS(BAH_NAME.getNamespaceURI(), BAH_NAME.getLocalPart());
            selectedElem = bahNodes.item(0);
            if (selectedElem == null) {
                return null;
            }
            XMLSignatureInput result = new XMLSignatureInput(selectedElem);
            result.setSecureValidation(context.secureValidation);
            result.setExcludeComments(true);
            result.setMIMEType("text/xml");
            result.setSourceURI(context.uriToResolve);
            return result;
        }
        return null;
    }

    @Override
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (null != context.uriToResolve && context.uriToResolve.equals("")) {
            return true;
        }
        return false;
    }


}

// Copyright (c) 2017 VocaLink Ltd
package com.mastercard.ap.security.bah.utility.context;

import javax.xml.namespace.NamespaceContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A NamespaceContext implementation for digital signatures
 */
public class DSNamespaceContext implements NamespaceContext {

    private Map<String, String> namespaceMap = new HashMap<>();

    /**
     * Default constructor
     */
    public DSNamespaceContext() {
        namespaceMap.put("ds", "http://www.w3.org/2000/09/xmldsig#");
        namespaceMap.put("dsig", "http://www.w3.org/2000/09/xmldsig#");
    }

    /**
     * Constructor with namespaces argument
     *
     * @param namespaces the namespaces
     */
    public DSNamespaceContext(Map<String, String> namespaces) {
        this();
        namespaceMap.putAll(namespaces);
    }

    /**
     * Getter for NamespaceURI
     *
     * @param arg0 get namespaceUri by this key
     * @return the namespaceUri
     */
    public String getNamespaceURI(String arg0) {
        return namespaceMap.get(arg0);
    }

    /**
     * Add a Prefix
     *
     * @param prefix    the key
     * @param namespace the value
     */
    public void putPrefix(String prefix, String namespace) {
        namespaceMap.put(prefix, namespace);
    }

    /**
     * Getter for Prefix
     *
     * @param arg0 get prefix by this key
     * @return the namespace
     */
    public String getPrefix(String arg0) {
        for (Map.Entry<String, String> entry : namespaceMap.entrySet()) {
            if (entry.getValue().equals(arg0)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Getter for prefixes
     *
     * @param arg0 get prefixes by this key
     * @return the prefix iterator
     */
    public Iterator<String> getPrefixes(String arg0) {
        return namespaceMap.keySet().iterator();
    }
}

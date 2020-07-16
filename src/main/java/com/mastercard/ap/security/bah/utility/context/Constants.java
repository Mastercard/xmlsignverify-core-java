package com.mastercard.ap.security.bah.utility.context;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static final QName BAH_NAME = new QName("urn:iso:std:iso:20022:tech:xsd:head.001.001.01", "AppHdr");
    public static final QName WS_SECURITY_NAME = new QName("urn:iso:std:iso:20022:tech:xsd:head.001.001.01", "Sgntr");
    public static final String SECUREMENT_ACTION_SEPARATOR = " | ";
    public static Set<String> SECUREMENT_ACTION_SET = new HashSet<>(Arrays.asList("AppHdr", "KeyInfo", "Document"));
    public static String SECUREMENT_ACTION_TRANSFORMER_EXCLUSION = "AppHdr";
    public static String SECUREMENT_ACTION_EXCLUSION = "Document";
    public static final String DS_NS = "http://www.w3.org/2000/09/xmldsig#";
    public static final String SIGNATURE_LOCAL_NAME = "Signature";
}

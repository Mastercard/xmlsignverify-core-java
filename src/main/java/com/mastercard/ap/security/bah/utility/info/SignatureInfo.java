package com.mastercard.ap.security.bah.utility.info;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SignatureInfo {

    private String signatureMethodAlgorithm;
    private String signatureCanonicalizationMethodAlgorithm;
    private String signatureExclusionTransformer;
    private ReferenceSignInfo appHdrReferenceSignInfo;
    private ReferenceSignInfo documentReferenceSignInfo;
    private ReferenceSignInfo keyReferenceSignInfo;

}

package com.mastercard.ap.security.bah.utility.info;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ReferenceSignInfo {

    private String transformAlgorithm;
    private String digestMethodAlgorithm;


}

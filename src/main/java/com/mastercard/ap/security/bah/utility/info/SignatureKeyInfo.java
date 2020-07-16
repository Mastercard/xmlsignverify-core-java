package com.mastercard.ap.security.bah.utility.info;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Builder
@ToString
public class SignatureKeyInfo {

    private PrivateKey privateKey;
    private byte[] skiIdBytes;

}

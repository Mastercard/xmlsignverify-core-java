[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/Mastercard/ap-bah-crypto-utility/blob/master/LICENSE)


## Table of Contents
- [Overview](#overview)
  * [Compatibility](#compatibility)
- [Usage](#usage)
  * [Signing the request](#signrequest)
  * [Verifying the request](#verifyrequest)
  * [Adding the Library to Your Project](#adding-the-library-to-your-project)
  * [Keytool Command to generate private-pubic key pair](#keytool-command-to-generate-public-private-key-pair)

## Overview <a name="overview"></a>
This SDK library provides reference implementation of ISO 20022 Digital Signature specification to sign and verify XML messages. 
It assumes that you have read the specification prior to coming here.

Refer to [ISO20022 Signed Unsigned Message Examples](docs/ISO20022_Example_Signed_Unsigned_Message.md) for understanding the difference between a signed and unsigned message.

In Signed XML payload, you will see three reference nodes:
* Resource as uri="" - will get resolved to AppHdr
* Resource with no uri - will get resolved to Document
* Resource as uri="#id" - will get resolved to element as per that id value

### Compatibility <a name="compatibility"></a>
Java 11+

## Usage <a name="usage"></a>

### Signing the request <a name="signrequest"></a>

XmlSignUtil.sign(Document document, SignatureInfo signatureInfo, SignatureKeyInfo signatureKeyInfo) signs the XML Document where:
  * document - the unsigned payload 
  * signatureInfo - signature info which is used in signing the payload
  * signatureKeyInfo - signature key info which holds private key and ski bytes to be set in X509 Data element

Refer to [detailed message signing flow](docs/MessageSigningFlow.md)


### Verifying the request <a name="verifyrequest"></a>

XmlSignUtil.verify(Document document, PublicKey publicKey) verifies the signed payload where:
  *  document - the signed payload
  *  publicKey - the public key

Refer to [detailed message verification flow](docs/MessageVerificationFlow.md)


### Adding the Library to Your Project <a name="adding-the-library-to-your-project"></a>

#### Maven
```xml
<dependency>
    <groupId>com.mastercard.ap.security</groupId>
    <artifactId>ap-bah-crypto-utility</artifactId>
    <version>${bah-crypto-utility-version}</version>
</dependency>
```

### Keytool Command to generate private-pubic key pair <a name="keytool-command-to-generate-public-private-key-pair"></a>

Execute the command from command prompt 
```java
keytool -genkey -alias test -keyalg RSA -validity 3060 -keysize 2048 -keystore keystore.jks -storetype JKS
```

Enter keystore password:

  Re-enter new password:
  
  What is your first and last name?
  
    [Unknown]:  Test
	
  What is the name of your organizational unit?
  
    [Unknown]:  OU
	
  What is the name of your organization?
  
    [Unknown]:  O
	
  What is the name of your City or Locality?
  
    [Unknown]:  London
	
  What is the name of your State or Province?
  
    [Unknown]:  S
	
  What is the two-letter country code for this unit?
  
    [Unknown]:  UK
	
  Is CN=Test, OU=OU, O=O, L=London, ST=S, C=UK correct?
  
    [no]:  yes

  Enter key password for <test> 
          (RETURN if same as keystore password):
		  
  Re-enter new password:

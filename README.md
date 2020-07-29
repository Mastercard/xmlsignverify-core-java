
## Table of Contents
- [Overview](#overview)
  * [Compatibility](#compatibility)
- [Usage](#usage)
  * [Signing the request](#signrequest)
  * [Verifying the request](#verifyrequest)
  * [Adding the Library to Your Project](#adding-the-library-to-your-project)
  * [Keytool Command to generate private-pubic key pair](#keytool-command-to-generate-public-private-key-pair)

## Overview <a name="overview"></a>
Library for signing xml payload as per Iso20022 guidelines.

The Signed XML payload is consist of three references:
* Resource as uri "" - will get resolved to AppHdr
* Resource as uri null - will get resolved to Document
* Resource as uri with id equals to some value - will get resolve to element as per that id value

### Compatibility <a name="compatibility"></a>
Java 11+

## Usage <a name="usage"></a>

### Signing the request <a name="signrequest"></a>
XmlSignUtil.sign(Document document, SignatureInfo signatureInfo, SignatureKeyInfo signatureKeyInfo):
Sign the xml Document and argument to this method are:
  * document - the unsigned document payload 
  * signatureInfo - signature info which will used in signing xml payload
  * signatureKeyInfo - signature key info which hold private key and ski bytes to be set in X509 Data

[Developers guide on Signing flow](docs/MessageSigningFlow.md)


### Verifying the request <a name="verifyrequest"></a>
XmlSignUtil.verify(Document document, PublicKey publicKey):
Verify the Signed payload and  argument to this method are:
  *  document - the signed payload
  *  publicKey - the public key

[Developers guide on Verification flow](docs/MessageVerificationFlow.md)



[ISO20022 Signed Unsigned Examples](docs/ISO20022_Signed_Unsigned_Examples_Reference_Guide.md)

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

## Example signed and unsigned messages <a name="signedunsignedmessageexample"></a>

### Unsigned ISO message example

```xml
<rain1:RequestPayload xmlns:rain1="http://ap.com/xsd/message/iso20022/rain.001.001.01">
  <urn1:AppHdr xmlns:urn1="urn:iso:std:iso:20022:tech:xsd:head.001.001.01">
    <urn1:Fr>...</urn1:Fr>
    <urn1:To>...</urn1:To>
    <urn1:BizMsgIdr>AP0409201832404829384005</urn1:BizMsgIdr>
    <urn1:MsgDefIdr>rain.001.001.01</urn1:MsgDefIdr>
    <urn1:CreDt>2018-09-07T09:59:12.133Z</urn1:CreDt>
  </urn1:AppHdr>
  <urn:Document xmlns:urn="urn:iso:std:iso:20022:tech:xsd:rain.001.001.01">
    ... ISO Message content
  </urn:Document> 
</rain1:RequestPayload>
```

### Signed ISO message example

The signed message has "Sgntr" node added as a child node under "AppHdr" node. The digital signing process adds this node.

```xml

<rain1:RequestPayload xmlns:rain1="http://ap.com/xsd/message/iso20022/rain.001.001.01">
  <urn1:AppHdr xmlns:urn1="urn:iso:std:iso:20022:tech:xsd:head.001.001.01">
    <urn1:Fr>...</urn1:Fr>
    <urn1:To>...</urn1:To>
    <urn1:BizMsgIdr>AP0409201832404829384005</urn1:BizMsgIdr>
    <urn1:MsgDefIdr>rain.001.001.01</urn1:MsgDefIdr>
    <urn1:CreDt>2018-09-07T09:59:12.133Z</urn1:CreDt>
    <!-- Signing process adds the following "Sgntr" node to the message -->
    <urn1:Sgntr>
      <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
        <ds:SignedInfo>
          <ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#" />
          <ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256" />
          <ds:Reference URI="">
            <ds:Transforms>
              <ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature" />
              <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#" />
            </ds:Transforms>
            <ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256" />
            <ds:DigestValue>H3/8j/fKZhE/5K8X3A8P46na8At+SY1+hBfU25BOqrM=</ds:DigestValue>
          </ds:Reference>
          <ds:Reference URI="#65e9a001-d0b6-4b60-b36d-42f748e037ce">
            <ds:Transforms>
              <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#" />
            </ds:Transforms>
            <ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256" />
            <ds:DigestValue>OQifBAjuJR4qDDF05DadJ3AZJCdBpgEsFbp903ksfcg=</ds:DigestValue>
          </ds:Reference>
          <ds:Reference>
            <ds:Transforms>
              <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#" />
            </ds:Transforms>
            <ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256" />
            <ds:DigestValue>CLWJJ7dkYOouuTOVWtC4N/kO8F2LWJU03rYKL/ogqXM=</ds:DigestValue>
          </ds:Reference>
        </ds:SignedInfo>
        <ds:SignatureValue>X7LBmNOhMYR+OrRvUIVQ6WCNsTFd2EV7LUzHANWo604FHhQqEdXmMoY7zHb8j+B51RQyZYQVcyl8QtEFLYgmzta2WnbwI1AybAXncyl5a5wmfjsDd94TbvYr8IEHCZCoi7gNdj7vzb7CJ87fmqXLRDnFa8f7tLuYlJOhu0S2+PprVYEkmly5QKcg5tNk/axLLTrV9FEFO07fD/+3YZOkWQU0MlQB3KXwe3z1biGYcxBKgWuZBzx6JVzwKNHAuk7NaAduT0MZpuFqwnnq59Cw/pr5AjNkLk70TEhhyRCXDTv7HTYRUTzOO9fOsrkjqMzd9GCZIIq9Fqv8si8EdzJwdw==</ds:SignatureValue>
        <ds:KeyInfo Id="65e9a001-d0b6-4b60-b36d-42f748e037ce">
          <ds:X509Data>
            <ds:X509SKI>zl4IvE2Sjjfnmh1oQCrUNM9gy1M=</ds:X509SKI>
          </ds:X509Data>
        </ds:KeyInfo>
      </ds:Signature>
    </urn1:Sgntr>
  </urn1:AppHdr>
  <urn:Document xmlns:urn="urn:iso:std:iso:20022:tech:xsd:rain.001.001.01">
    ... ISO Message content
  </urn:Document> 
</rain1:RequestPayload>

```

Sgntr node structure

```xml

<urn1:Sgntr>
  <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
    <ds:SignedInfo>...</ds:SignedInfo>
    <ds:SignatureValue>...</ds:SignatureValue>
    <ds:KeyInfo Id="UNIQUE_ID">...</ds:KeyInfo>
  </ds:Signature>
</urn1:Sgntr>

```
Signature node elements

| Node | Description |
| ---- | ----------- |
| SignedInfo | Node that contains the digests of all the other nodes in the business message. The SignedInfo is the node, which is signed using private key.|
| SignatureValue | Node that contains the final Base64 encoded signature value |
| KeyInfo | Node that contains the information about key used to signed the message. It contains SubjectKeyIdentifier in "X509SKI" element, which should be used to lookup corresponding public key to verify the message signature. |

package uk.co.compendiumdev.javafortesters.domain.http;

import javax.xml.bind.DatatypeConverter;

public class Base64Encoder {

    // https://stackoverflow.com/questions/14413169/which-java-library-provides-base64-encoding-decoding

    public String encodeToString(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }
}

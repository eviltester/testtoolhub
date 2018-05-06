package uk.co.compendiumdev.javafortesters.domain.http;

//import javax.xml.bind.DatatypeConverter;

public class Base64Encoder {

    // https://stackoverflow.com/questions/14413169/which-java-library-provides-base64-encoding-decoding

    //JDK 1.7
//    public String encodeToString(byte[] bytes) {
//        return DatatypeConverter.printBase64Binary(bytes);
//    }

    // JDK1.8 and above
    public String encodeToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}

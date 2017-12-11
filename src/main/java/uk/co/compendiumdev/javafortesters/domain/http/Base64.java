package uk.co.compendiumdev.javafortesters.domain.http;

/**
 * Created by Alan on 27/09/2017.
 *
 * Used to replicate the Base64 class found in Java 1.8 for Java 1.7
 *
 * In java 1.8 we would just use `java.util.Base64`
 */
public class Base64{

    public static Base64Encoder getEncoder(){

        return new Base64Encoder();
    }
}
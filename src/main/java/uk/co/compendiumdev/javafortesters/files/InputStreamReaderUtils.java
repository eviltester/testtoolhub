package uk.co.compendiumdev.javafortesters.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamReaderUtils {

    public static String stringFromStream(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}

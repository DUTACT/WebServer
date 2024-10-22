package com.dutact.web.common.utils;

import lombok.SneakyThrows;
import org.apache.tika.Tika;

import java.io.InputStream;

public class MIMEMappingUtils {
    private MIMEMappingUtils() {
    }

    public static String getMIMEType(String extension) {
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }

        Tika tika = new Tika();
        return tika.detect(extension);
    }

    @SneakyThrows
    public static String getMIMEType(InputStream inputStream) {
        Tika tika = new Tika();
        return tika.detect(inputStream);
    }
}

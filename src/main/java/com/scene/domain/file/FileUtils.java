package com.scene.domain.file;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

public class FileUtils {

    public static String getFileExtension(byte[] data) throws MimeTypeException {
        return MimeTypes.getDefaultMimeTypes()
                .forName(new Tika().detect(data))
                .getExtension();
    }

    public static String getFileType(byte[] data) {
        return new Tika().detect(data);
    }

    public static String getFileContentType(String fileName) {
        return new Tika().detect(fileName);
    }
}

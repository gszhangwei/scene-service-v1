package com.scene.adapters.outbound.localstorage;

import com.scene.domain.file.StaticFileInfo;
import com.scene.domain.file.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class LocalStorageClient implements StorageClient {
    @Override
    public void uploadFile(byte[] data, StaticFileInfo fileInfo) {
        File imageFile = new File(String.format("%s.jpg", fileInfo.getName()));
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(data);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFilePath(String fileName) {
        return String.format("%s.jpg", fileName);
    }
}

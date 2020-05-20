package com.scene.adapters.outbound.localstorage;

import com.scene.domain.file.File;
import com.scene.domain.file.FileRepo;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class LocalFileRepo implements FileRepo {
    @Override
    public void save(File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(new java.io.File(file.getId().toString()));
            outputStream.write(file.getData());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

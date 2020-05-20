package com.scene.domain.file;

import com.scene.domain.core.DomainService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.apache.tika.Tika;

@AllArgsConstructor
public final class FileService implements DomainService {

    private final FileRepo fileRepo;

    private final FileTypeRepo fileTypeRepo;

    public File upload(byte[] data) {
        String fileType = new Tika().detect(data);
        if (!fileTypeRepo.getWhitelist().contains(fileType)) {
            throw new InvalidFileTypeException();
        }
        File file = new File(UUID.randomUUID(), data);
        fileRepo.save(file);
        return file;
    }
}

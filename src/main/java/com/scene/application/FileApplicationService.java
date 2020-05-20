package com.scene.application;

import com.scene.domain.file.File;
import com.scene.domain.file.FileRepo;
import com.scene.domain.file.FileService;
import com.scene.domain.file.FileTypeRepo;
import org.springframework.stereotype.Service;

@Service
public class FileApplicationService implements ApplicationService {
    private final FileService fileService;

    public FileApplicationService(FileRepo fileRepo, FileTypeRepo fileTypeRepo) {
        this.fileService = new FileService(fileRepo, fileTypeRepo);
    }

    public File upload(byte[] data) {
        return fileService.upload(data);
    }
}

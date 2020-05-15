package com.scene.application;

import com.scene.domain.file.FileTypeRepo;
import com.scene.domain.file.StorageClient;
import com.scene.domain.file.StaticFileService;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.stereotype.Service;

@Service
public class FileApplicationService implements ApplicationService {
    private final StorageClient storageClient;
    private final FileTypeRepo fileTypeRepo;
    private final StaticFileService staticFileService;

    public FileApplicationService(StorageClient storageClient, FileTypeRepo fileTypeRepo) {
        this.storageClient = storageClient;
        this.fileTypeRepo = fileTypeRepo;
        this.staticFileService = new StaticFileService(storageClient, fileTypeRepo);
    }

    public StaticFileService.UploadFileResult uploadFile(byte[] data) throws MimeTypeException {
        return staticFileService.uploadFile(data);
    }
}

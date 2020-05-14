package com.scene.application;

import com.scene.domain.file.IFileTypeRepo;
import com.scene.domain.file.IStorageClient;
import com.scene.domain.file.StaticFileService;
import org.apache.tika.mime.MimeTypeException;

public class FileApplicationService implements IApplicationService {
    private final IStorageClient iStorageClient;
    private final IFileTypeRepo iFileTypeRepo;
    private final StaticFileService staticFileService;

    public FileApplicationService(IStorageClient iStorageClient, IFileTypeRepo iFileTypeRepo) {
        this.iStorageClient = iStorageClient;
        this.iFileTypeRepo = iFileTypeRepo;
        this.staticFileService = new StaticFileService(iStorageClient, iFileTypeRepo);
    }

    public StaticFileService.UploadFileResult uploadFile(byte[] data) throws MimeTypeException {
        return staticFileService.uploadFile(data);
    }
}

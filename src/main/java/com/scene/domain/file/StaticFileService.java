package com.scene.domain.file;

import com.scene.domain.core.IDomainService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tika.mime.MimeTypeException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
public final class StaticFileService implements IDomainService {

    private final IStorageClient iStorageClient;

    private final IFileTypeRepo iFileTypeRepo;

    private String generateFileName(byte[] data) throws MimeTypeException {
        return String.format("%s%s", UUID.randomUUID().toString(), FileUtils.getFileExtension(data));
    }

    private void checkFileLegality(byte[] data) {
        String fileType = FileUtils.getFileType(data);
        if (!iFileTypeRepo.getWhitelist().contains(fileType)) {
            throw new InvalidFileTypeException();
        }
    }

    @Data
    @AllArgsConstructor
    public static final class UploadFileResult {

        @NotNull
        private String fileName;

        @NotNull
        private String filePath;

    }

}

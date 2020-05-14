package com.scene.adapters.outbound.config;

import com.scene.domain.file.IFileTypeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FileTypeConfiguration implements IFileTypeRepo {

    private final List<String> staticFileTypeLimit;

    public FileTypeConfiguration(@Value("${static-file.static-file-type-limit}") List<String> staticFileTypeLimit) {
        this.staticFileTypeLimit = staticFileTypeLimit;
    }

    @Override
    public List<String> getWhitelist() {
        return staticFileTypeLimit;
    }
}

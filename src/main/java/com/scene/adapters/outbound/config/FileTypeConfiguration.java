package com.scene.adapters.outbound.config;

import com.scene.domain.file.FileTypeRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FileTypeConfiguration implements FileTypeRepo {

    @Value("${file-type.whitelist}")
    private List<String> whitelist;

    @Override
    public List<String> getWhitelist() {
        return whitelist;
    }
}

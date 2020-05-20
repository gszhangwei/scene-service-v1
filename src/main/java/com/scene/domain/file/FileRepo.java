package com.scene.domain.file;

import org.springframework.stereotype.Component;

@Component
public interface FileRepo {
    void save(File file);
}

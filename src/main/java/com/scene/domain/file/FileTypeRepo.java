package com.scene.domain.file;

import com.scene.domain.core.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileTypeRepo extends Repository {
    List<String> getWhitelist();
}

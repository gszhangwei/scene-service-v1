package com.scene.domain.file;

import com.scene.domain.core.Repository;
import java.util.List;

public interface FileTypeRepo extends Repository {
    List<String> getWhitelist();
}

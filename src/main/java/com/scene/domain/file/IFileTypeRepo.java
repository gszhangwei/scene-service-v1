package com.scene.domain.file;

import com.scene.domain.core.IRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IFileTypeRepo extends IRepository {
    public List<String> getWhitelist();
}

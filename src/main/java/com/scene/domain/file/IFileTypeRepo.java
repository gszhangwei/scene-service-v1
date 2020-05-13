package com.scene.domain.file;

import com.scene.domain.core.IRepository;

import java.util.List;

public interface IFileTypeRepo extends IRepository {
    public List<String> getWhitelist();
}

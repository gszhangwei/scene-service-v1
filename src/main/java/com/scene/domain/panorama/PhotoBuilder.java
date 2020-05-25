package com.scene.domain.panorama;

import com.scene.domain.core.Builder;
import java.util.UUID;

public abstract class PhotoBuilder implements Builder<Photo> {
    abstract UUID getFileId();
    abstract Boolean isDefault();

    @Override
    public Photo build() {
        return new Photo(getFileId(), isDefault());
    }
}

package com.scene.domain.panorama;

import com.scene.domain.core.Entity;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Photo extends Entity<UUID> {
    private UUID fileId;
    private boolean isDefault;

    @Override
    public UUID getId() {
        return fileId;
    }
}

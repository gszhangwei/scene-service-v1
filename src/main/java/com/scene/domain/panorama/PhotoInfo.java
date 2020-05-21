package com.scene.domain.panorama;

import com.scene.domain.core.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoInfo extends Entity<Long> {
    @Override
    public Long getId() {
        return 0L;
    }
    private String photoUrl;
    private boolean isInitialShow;
}

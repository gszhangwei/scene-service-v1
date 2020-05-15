package com.scene.domain.panorama;

import com.scene.domain.core.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoInfo implements Entity {
    private String photoUrl;
    private boolean isInitialShow;
}

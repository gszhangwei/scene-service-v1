package com.scene.adapters.inbound.rest.output;

import com.scene.domain.panorama.PhotoInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoDetailOutputDTO {
    private String url;
    private boolean isDefault;

    public static PhotoDetailOutputDTO of(PhotoInfo photoInfo) {
        return new PhotoDetailOutputDTO(photoInfo.getPhotoUrl(), photoInfo.isInitialShow());
    }
}

package com.scene.domain.panorama;

import com.scene.domain.core.IEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoInfo implements IEntity {
    private String photoUrl;
    private boolean isInitialShow;
}

package com.scene.adapters.outbound.esstorage.panorama;

import com.scene.domain.panorama.Scene;
import com.scene.domain.panorama.SceneType;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
public class SceneEPO {
    @Nullable
    private Long id;
    @Nullable
    private Long panoramaId;
    @Nullable
    private String name;
    @Nullable
    private SceneType type;
    @Nullable
    private Boolean isInitialShow;
    @Nullable
    private Boolean isDeleted;
    @Nullable
    private Map photos;
    @Nullable
    private Date createTime;
    @Nullable
    private Date updateTime;

    public Object toDomainObject() {
        return new Scene(
                this.id,
                this.panoramaId,
                this.name,
                this.type,
                this.isInitialShow,
                this.isDeleted,
                this.photos,
                this.createTime,
                this.updateTime
        );
    }

    public static SceneEPO of(Scene scene) {
        return new SceneEPO(
                scene.getId(),
                scene.getPanoramaId(),
                scene.getName(),
                scene.getType(),
                scene.getIsInitialShow(),
                scene.getIsDeleted(),
                scene.getPhotos(),
                scene.getCreateTime(),
                scene.getUpdateTime()
        );
    }
}

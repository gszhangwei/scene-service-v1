package com.scene.domain.panorama;

import com.scene.domain.core.IEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Scene implements IEntity {
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
    private Map<String, PhotoInfo> photos;
    @Nullable
    private Date createTime;
    @Nullable
    private Date updateTime;

    public Scene update(Scene updatedScene, Date now) {
        name = updatedScene.name;
        type = updatedScene.type;
        isInitialShow = updatedScene.isInitialShow;
        photos = updatedScene.photos;
        updateTime = now;
        return new Scene(id, panoramaId, name, type, isInitialShow, isDeleted, photos, createTime, updateTime);
    }

    public Scene delete(Date now) {
        updateTime = now;
        return new Scene(id, panoramaId, name, type, isInitialShow, true, photos, createTime, updateTime);
    }
}

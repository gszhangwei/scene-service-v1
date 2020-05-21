package com.scene.domain.panorama;

import com.scene.domain.core.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Scene extends Entity<Long> {
    private Long id;
    private Long panoramaId;
    private String name;
    private SceneType type;
    private Boolean isInitialShow;
    private Boolean isDeleted;
    private Map<String, PhotoInfo> photos;
    private Date createTime;
    private Date updateTime;

    public Scene update(Scene updatedScene, Date now) {
        return new Scene(id, panoramaId, updatedScene.name, updatedScene.type, updatedScene.isInitialShow, isDeleted, updatedScene.photos, createTime, now);
    }

    public Scene delete(Date now) {
        return new Scene(id, panoramaId, name, type, isInitialShow, true, photos, createTime, now);
    }
}

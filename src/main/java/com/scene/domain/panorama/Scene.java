package com.scene.domain.panorama;

import com.scene.domain.core.IEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Scene implements IEntity {
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

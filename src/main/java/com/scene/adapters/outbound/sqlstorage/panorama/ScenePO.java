package com.scene.adapters.outbound.sqlstorage.panorama;

import com.scene.adapters.outbound.sqlstorage.core.IPersistenceObject;
import com.scene.domain.panorama.Scene;
import com.scene.domain.panorama.SceneType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Data
@Entity
@Table(name = "scene")
public class ScenePO implements IPersistenceObject<Scene> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "panorama_id")
    @Nullable
    private Long panoramaId;

    @Nullable
    private String name;

    @Nullable
    private SceneType type;

    @Column(name = "is_initial_show")
    @Nullable
    private Boolean isInitialShow;

    @Column(name = "is_deleted")
    @Nullable
    private Boolean isDeleted;

    @Column(name = "photos")
    @Convert(converter = MapConverter.class)
    @Nullable
    private Map photos;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    @Nullable
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    @Nullable
    private Date updateTime;


    public static ScenePO of(Scene scene) {
        return new ScenePO(
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

    @Override
    public Scene toDomainObject() {
        return new Scene(
                this.getId(),
                this.getPanoramaId(),
                this.getName(),
                this.getType(),
                this.getIsInitialShow(),
                this.getIsDeleted(),
                this.getPhotos(),
                this.getCreateTime(),
                this.getUpdateTime()
        );
    }
}

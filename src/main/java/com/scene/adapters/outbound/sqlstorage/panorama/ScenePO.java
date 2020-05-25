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

    @Column(name = "photos")
    @Convert(converter = MapConverter.class)
    @Nullable
    private Map photos;


    public static ScenePO of(Scene scene, Long panoramaId) {
        return new ScenePO(
                scene.getId(),
                panoramaId,
                scene.getName(),
                scene.getType(),
                scene.getIsDefault(),
                scene.getPhotos()
        );
    }

    @Override
    public Scene toDomainObject() {
        return new Scene(
                this.getId(),
                this.getName(),
                this.getType(),
                this.getIsInitialShow(),
                this.getPhotos()
        );
    }
}

package com.scene.adapters.outbound.sqlstorage.panorama;

import com.scene.adapters.outbound.sqlstorage.core.IPersistenceObject;
import com.scene.domain.panorama.Panorama;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PanoramaPO implements IPersistenceObject<Panorama> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Nullable
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "panorama_id")
    @NotNull
    private List<ScenePO> scenes;

    @Column(name = "is_deleted")
    @Nullable
    private Boolean isDeleted;

    @Column(name = "panorama_url")
    @Nullable
    private String panoramaUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    @Nullable
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    @Nullable
    private Date updateTime;

    public static PanoramaPO of(Panorama panorama) {
        return new PanoramaPO(
                panorama.getId(),
                panorama.getName(),
                panorama.getScenes()
                        .stream()
                        .map(s -> ScenePO.of(s, panorama.getId()))
                        .collect(toList()),
                panorama.getIsDeleted(),
                panorama.getShortUrl(),
                panorama.getCreateTime(),
                panorama.getUpdateTime()
        );
    }

    @Override
    public Panorama toDomainObject() {
        return null;
    }
}

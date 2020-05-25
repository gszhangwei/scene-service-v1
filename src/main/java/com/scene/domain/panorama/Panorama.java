package com.scene.domain.panorama;

import com.scene.domain.core.AggregateRoot;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

@AllArgsConstructor
@Getter
public class Panorama extends AggregateRoot<Long> {
    @Wither
    private Long id;
    private String name;
    private List<Scene> scenes;
    @Wither
    private String shortUrl;
    private Boolean isDeleted;
    private Date createTime;
    private Date updateTime;

    public Panorama(String name, List<Scene> scenes) {
        this.name = name;
        this.scenes = scenes;
        this.isDeleted = false;
        Date now = Date.from(Instant.now());
        this.createTime = now;
        this.updateTime = now;
    }
}

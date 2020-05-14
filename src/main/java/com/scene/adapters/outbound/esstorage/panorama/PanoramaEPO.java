package com.scene.adapters.outbound.esstorage.panorama;

import com.scene.adapters.outbound.esstorage.core.IEPersistenceObject;
import com.scene.domain.panorama.Panorama;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Document(indexName = "#{@indexName}", shards = 3)
@AllArgsConstructor
public class PanoramaEPO implements IEPersistenceObject<Panorama> {

    @Id
    @Field(name = "id", type = FieldType.Keyword)
    @Nullable
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    @Nullable
    private String name;

    @Field(name = "isDeleted", type = FieldType.Keyword)
    @Nullable
    private Boolean isDeleted;

    @Field(name = "panoramaUrl", type = FieldType.Keyword)
    @Nullable
    private String panoramaUrl;

    @Field(name = "createTime", type = FieldType.Date)
    @Nullable
    private Date createTime;

    @Field(name = "updateTime", type = FieldType.Date)
    @Nullable
    private Date updateTime;

    @Field(name = "tags", type = FieldType.Text)
    @Nullable
    private List tags;

    @Override
    public Panorama toDomainObject() {
        return new Panorama(
                this.id,
                this.name,
                Collections.emptyList(),
                this.isDeleted,
                this.panoramaUrl,
                this.createTime,
                this.updateTime
        );
    }

    public static PanoramaEPO of(Panorama panorama) {
        return new PanoramaEPO(panorama.getId(),
                panorama.getName(),
                panorama.getIsDeleted(),
                panorama.getPanoramaUrl(),
                panorama.getCreateTime(),
                panorama.getUpdateTime(),
                null);
    }
}

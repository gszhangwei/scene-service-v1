package com.scene.adapters.inbound.rest.output;

import com.scene.domain.panorama.Panorama;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PanoramaOutputDTO {
    private Long id;
    private String name;
    private Boolean isDeleted;
    private String panoramaUrl;
    private Date createTime;
    private Date updateTime;

    public static PanoramaOutputDTO of(Panorama panorama, String urlPrefix){
        return new PanoramaOutputDTO(panorama.getId(), panorama.getName(), panorama.getIsDeleted(),
                urlPrefix + panorama.getPanoramaUrl(), panorama.getCreateTime(), panorama.getUpdateTime());
    }
}

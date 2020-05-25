package com.scene.adapters.inbound.rest.input;

import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.Photo;
import com.scene.domain.panorama.Scene;
import com.scene.domain.panorama.SceneType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PanoramaInputDTO {
    @NotBlank(message = "请填写全景名称")
    @Length(max = 50, message = "全景名称不能超过50个字符")
    private String name;
    @Valid
    private List<SceneInputDTO> scenes;

    public final Panorama toDomainObject() {
        return new Panorama(name, scenes.stream().map(SceneInputDTO::toDomainObject).collect(toList()));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static final class SceneInputDTO {
        @NotBlank( message = "请填写场景名称")
        @Length(max = 50,message = "场景名称不能超过50个字符")
        private String name;
        @NotNull(message = "请选择场景类型")
        private SceneType type;
        private boolean isInitialShow;
        @NotEmpty( message = "请上传图片")
        private Map<String, PhotoInputDTO> photos;

        final Scene toDomainObject() {
            Map<String, Photo> photoInfoMap = photos.entrySet().stream().collect(toMap(Map.Entry::getKey, photoMap -> photoMap.getValue().toDomainObject()));
            return new Scene(null, name, type, isInitialShow, photoInfoMap);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PhotoInputDTO {
        @NotNull
        private UUID fileId;
        private boolean isInitialShow;

        @NotNull
        final Photo toDomainObject() {
            return new Photo(this.fileId, this.isInitialShow);
        }

    }
}

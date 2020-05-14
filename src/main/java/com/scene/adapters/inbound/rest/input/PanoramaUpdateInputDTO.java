package com.scene.adapters.inbound.rest.input;

import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.PhotoInfo;
import com.scene.domain.panorama.Scene;
import com.scene.domain.panorama.SceneType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PanoramaUpdateInputDTO {
    @NotBlank(message = "请填写全景名称")
    @Length(max = 50, message = "全景名称不能超过50个字符")
    private String name;
    @Valid
    private List<SceneUpdateInputDTO> scenes;

    public final Panorama toDomainObject() {
        Date currentDate = new Date();
        return new Panorama(null, name, scenes.stream().map(SceneUpdateInputDTO::toDomainObject).collect(toList()), false, null, currentDate, currentDate);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static final class SceneUpdateInputDTO {
        private Long id;
        @NotBlank( message = "请填写场景名称")
        @Length(max = 50,message = "场景名称不能超过50个字符")
        private String name;
        @NotNull(message = "请选择场景类型")
        private SceneType type;
        private boolean isInitialShow;
        private boolean isDeleted;
        @NotEmpty( message = "请上传图片")
        private Map<String, PhotoUpdateInputDTO> photos;

        public final Scene toDomainObject() {
            Map<String, PhotoInfo> photoInfoMap = photos.entrySet().stream().collect(toMap(Map.Entry::getKey, photoMap -> photoMap.getValue().toDomainObject()));
            Date currentTime = new Date();
            return new Scene(id, null, name, type, isInitialShow, isDeleted, photoInfoMap, currentTime, currentTime);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class PhotoUpdateInputDTO {
        @NotNull
        private String photoUrl;
        private boolean isInitialShow;

        @NotNull
        public final PhotoInfo toDomainObject() {
            return new PhotoInfo(this.photoUrl, this.isInitialShow);
        }

    }
}

package com.scene.adapters.inbound.rest.input;

import com.scene.domain.panorama.PanoramaBuilder;
import com.scene.domain.panorama.PhotoBuilder;
import com.scene.domain.panorama.SceneBuilder;
import com.scene.domain.panorama.SceneType;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PanoramaInputDTO extends PanoramaBuilder {
    @NotBlank(message = "请填写全景名称")
    @Length(max = 50, message = "全景名称不能超过50个字符")
    private String name;
    @Valid
    private List<SceneInputDTO> scenes;

    @Override
    public List<? extends SceneBuilder> getSceneBuilders() {
        return scenes;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SceneInputDTO extends SceneBuilder {
        @NotBlank( message = "请填写场景名称")
        @Length(max = 50,message = "场景名称不能超过50个字符")
        private String name;
        @NotNull(message = "请选择场景类型")
        private SceneType type;
        private boolean isInitialShow;
        @NotEmpty( message = "请上传图片")
        private Map<String, PhotoInputDTO> photos;

        @Override
        public Boolean isDefault() {
            return isInitialShow;
        }

        @Override
        public Map<String, ? extends PhotoBuilder> getPhotoBuilders() {
            return photos;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PhotoInputDTO extends PhotoBuilder {
        @NotNull
        private UUID fileId;
        private boolean isInitialShow;

        @Override
        public Boolean isDefault() {
            return isInitialShow;
        }
    }
}

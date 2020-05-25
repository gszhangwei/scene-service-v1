package com.scene.adapters.inbound.rest;

import com.alibaba.fastjson.JSON;
import com.scene.adapters.inbound.rest.input.PanoramaInputDTO;
import com.scene.domain.panorama.SceneType;
import java.nio.charset.StandardCharsets;
import static java.util.Collections.singletonList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({PanoramaController.class})
@Ignore
public class PanoramaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public final void should_valid_panorama_name_is_blank_when_create_panorama() throws Exception {
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO(UUID.randomUUID(), true);
        Map<String, PanoramaInputDTO.PhotoInputDTO> photoInputDTOMap = new HashMap<String, PanoramaInputDTO.PhotoInputDTO>(){{ put("0度", photoInputDTO); }};
        PanoramaInputDTO.SceneInputDTO sceneInputDTO = new PanoramaInputDTO.SceneInputDTO("场景", SceneType.ENVIRONMENT, true, photoInputDTOMap);
        PanoramaInputDTO panoramaInputDTO = new PanoramaInputDTO("", singletonList(sceneInputDTO));

        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/panoramas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(panoramaInputDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();
        String errorContent = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        JSONObject errorResponse = new JSONObject(errorContent);
        Assert.assertEquals("1003", errorResponse.get("errCode").toString());
        Assert.assertEquals("请填写全景名称", errorResponse.get("errMsg"));
    }

    @Test
    public final void should_valid_panorama_name_length_when_create_panorama() throws Exception {
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO(UUID.randomUUID(), true);
        Map<String, PanoramaInputDTO.PhotoInputDTO> photoInputDTOMap = new HashMap<String, PanoramaInputDTO.PhotoInputDTO>(){{ put("0度", photoInputDTO); }};
        PanoramaInputDTO.SceneInputDTO sceneInputDTO = new PanoramaInputDTO.SceneInputDTO("场景", SceneType.ENVIRONMENT, true, photoInputDTOMap);
        PanoramaInputDTO panoramaInputDTO = new PanoramaInputDTO("北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全", singletonList(sceneInputDTO));

        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/panoramas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(panoramaInputDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();
        String errorContent = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        JSONObject errorResponse = new JSONObject(errorContent);
        Assert.assertEquals("1003", errorResponse.get("errCode").toString());
        Assert.assertEquals("全景名称不能超过50个字符", errorResponse.get("errMsg"));
    }

    @Test
    public final void should_valid_scene_name_is_blank_when_create_panorama() throws Exception {
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO(UUID.randomUUID(), true);
        Map<String, PanoramaInputDTO.PhotoInputDTO> photoInputDTOMap = new HashMap<String, PanoramaInputDTO.PhotoInputDTO>(){{ put("0度", photoInputDTO); }};
        PanoramaInputDTO.SceneInputDTO sceneInputDTO = new PanoramaInputDTO.SceneInputDTO("", SceneType.ENVIRONMENT, true, photoInputDTOMap);
        PanoramaInputDTO panoramaInputDTO = new PanoramaInputDTO("全景", singletonList(sceneInputDTO));

        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/panoramas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(panoramaInputDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();
        String errorContent = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        JSONObject errorResponse = new JSONObject(errorContent);
        Assert.assertEquals("1003", errorResponse.get("errCode").toString());
        Assert.assertEquals("请填写场景名称", errorResponse.get("errMsg"));
    }

    @Test
    public final void should_valid_scene_name_length_when_create_panorama() throws Exception {
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO(UUID.randomUUID(), true);
        Map<String, PanoramaInputDTO.PhotoInputDTO> photoInputDTOMap = new HashMap<String, PanoramaInputDTO.PhotoInputDTO>(){{ put("0度", photoInputDTO); }};
        PanoramaInputDTO.SceneInputDTO sceneInputDTO = new PanoramaInputDTO.SceneInputDTO("北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全景北京故宫全", SceneType.ENVIRONMENT, true, photoInputDTOMap);
        PanoramaInputDTO panoramaInputDTO = new PanoramaInputDTO("全景", singletonList(sceneInputDTO));

        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/panoramas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(panoramaInputDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();
        String errorContent = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        JSONObject errorResponse = new JSONObject(errorContent);
        Assert.assertEquals("1003", errorResponse.get("errCode").toString());
        Assert.assertEquals("场景名称不能超过50个字符", errorResponse.get("errMsg"));
    }

    @Test
    public final void should_valid_scene_photos_is_empty_when_create_panorama() throws Exception {
        PanoramaInputDTO.SceneInputDTO sceneInputDTO = new PanoramaInputDTO.SceneInputDTO("场景1", SceneType.ENVIRONMENT, true, new HashMap<>());
        PanoramaInputDTO panoramaInputDTO = new PanoramaInputDTO("全景", singletonList(sceneInputDTO));

        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/panoramas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(panoramaInputDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();
        String errorContent = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        JSONObject errorResponse = new JSONObject(errorContent);
        Assert.assertEquals("1003", errorResponse.get("errCode").toString());
        Assert.assertEquals("请上传图片", errorResponse.get("errMsg"));
    }
}

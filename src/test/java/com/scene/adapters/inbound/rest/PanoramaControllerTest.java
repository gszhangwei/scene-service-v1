package com.scene.adapters.inbound.rest;

import com.alibaba.fastjson.JSON;
import com.scene.adapters.inbound.rest.input.PanoramaInputDTO;
import com.scene.application.PanoramaApplicationService;
import com.scene.domain.panorama.Panorama;
import com.scene.domain.panorama.PanoramaService;
import com.scene.domain.panorama.PhotoInfo;
import com.scene.domain.panorama.Scene;
import com.scene.domain.panorama.SceneType;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({PanoramaController.class})
public class PanoramaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PanoramaApplicationService panoramaService;
    @Value("${server-domain}")
    private String urlPrefix;

    @Test
    public final void should_search_panorama_list() throws Exception {
        when(panoramaService.searchPanoramas(null, 1, 30)).thenReturn(new PanoramaService.PanoramaSearchResult(1L, singletonList(getPanorama())));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/panoramas?pageNumber=1&pageSize=30"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total", is(1)))
                .andExpect(jsonPath("$.panoramas").isArray())
                .andExpect(jsonPath("$.panoramas").isNotEmpty())
                .andExpect(jsonPath("$.panoramas[0].id", is(1)))
                .andExpect(jsonPath("$.panoramas[0].name", is("Benz")))
                .andExpect(jsonPath("$.panoramas[0].panoramaUrl", is(urlPrefix + "74gonwE")));
    }

    @Test
    public final void should_get_panorama_by_url() throws Exception {
        String url = "74gonwE";
        when(panoramaService.get(url)).thenReturn(getPanorama());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/panoramas/" + url))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Benz")))
                .andExpect(jsonPath("$.scenes").isArray())
                .andExpect(jsonPath("$.scenes").isNotEmpty())
                .andExpect(jsonPath("$.scenes[0].id", is(1)))
                .andExpect(jsonPath("$.scenes[0].name", is("Benz 0")))
                .andExpect(jsonPath("$.scenes[0].type", is("ENVIRONMENT")))
                .andExpect(jsonPath("$.scenes[0].default", is(true)))
                .andExpect(jsonPath("$.scenes[0].photos").isMap())
                .andExpect(jsonPath("$.scenes[0].photos.0.url", is("url0")))
                .andExpect(jsonPath("$.scenes[0].photos.0.default", is(true)))
                .andExpect(jsonPath("$.scenes[0].photos.1.url", is("url1")));
    }

    @Test
    public final void should_return_404_when_panorama_not_found() throws Exception {
        String url = "74gonwE";
        when(panoramaService.get(url)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/panoramas/" + url))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    public final void should_valid_panorama_name_is_blank_when_create_panorama() throws Exception {
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO("http://www.baidu.com", true);
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
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO("http://www.baidu.com", true);
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
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO("http://www.baidu.com", true);
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
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO("http://www.baidu.com", true);
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

    private Panorama getPanorama() {
        return new Panorama(1L, "Benz",
                asList(
                        new Scene(1L, 1L, "Benz 0", SceneType.ENVIRONMENT, true, false, photoInfoMap,new Date(), new Date()),
                        new Scene(2L, 1L, "Benz 1", SceneType.ENVIRONMENT, true, true, singlePhotoInfoMap, new Date(), new Date())
                ), false, "74gonwE", new Date(), new Date());
    }


    Map<String, PhotoInfo> photoInfoMap = new HashMap<String, PhotoInfo>(){{
        put("0", new PhotoInfo("url0", true));
        put("1", new PhotoInfo("url1", false));
        put("2", new PhotoInfo("url2", false));
        put("3", new PhotoInfo("url3", false));
        put("4", new PhotoInfo("url4", false));
        put("5", new PhotoInfo("url5", false));
    }};

    Map<String, PhotoInfo> singlePhotoInfoMap = new HashMap<String, PhotoInfo>(){{
        put("0", new PhotoInfo("url0", true));
    }};


}

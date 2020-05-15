package com.scene.domain.panorama;

import com.google.common.collect.ImmutableMap;
import com.scene.adapters.inbound.rest.input.PanoramaInputDTO;
import org.hashids.Hashids;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class PanoramaServiceTest {

    private PanoramaService panoramaService;

    @Mock
    private IPanoramaRepository iPanoramaRepository;

    @Mock
    private ISearchPanoramaRepository iSearchPanoramaRepository;

    @Captor
    private ArgumentCaptor<Panorama> captor;

    private final Panorama benzPano;

    private final Panorama bmwPano = new Panorama(
            2L,
            "bmw",
            Collections.emptyList(),
            false,
            "0ZOXzlE",
            new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28"),
            new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28")
    );

    {
        Map<String, PhotoInfo> photos = new HashMap<String, PhotoInfo>() {{
            put("0", new PhotoInfo("url0", true));
            put("1", new PhotoInfo("url1", false));
            put("2", new PhotoInfo("url2", false));
            put("3", new PhotoInfo("url3", false));
            put("4", new PhotoInfo("url4", false));
            put("5", new PhotoInfo("url5", false));
        }};

        benzPano = new Panorama(
                1L,
                "Benz",
                asList(
                        new Scene(
                                1L,
                                1L,
                                "Benz 0",
                                SceneType.ENVIRONMENT,
                                true,
                                false,
                                photos,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28"),
                                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28")
                        ),
                        new Scene(
                                2L,
                                1L,
                                "Benz 1",
                                SceneType.ENVIRONMENT,
                                false,
                                false,
                                ImmutableMap.of("0", new PhotoInfo("url11", true)),
                                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28"),
                                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28")
                        )
                ),
                false,
                "74gonwE",
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-28")
        );

    }

    @Before
    public void setup() {
        panoramaService = new PanoramaService(
                iPanoramaRepository,
                iSearchPanoramaRepository,
                "This is VR SaaS salt"
        );

        Mockito.when(iPanoramaRepository.save(any(Panorama.class))).thenAnswer(
                (Answer<Panorama>) invocation -> {
                    Panorama panorama = (Panorama) invocation.getArguments()[0];
                    if (Objects.isNull(panorama.getId())) {
                        panorama.setId(1L);
                    }
                    return panorama;
                }
        );
    }

    @Test
    public void should_save_panorama_and_scene_success_when_create_new_panorama() {
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO("http://www.baidu.com", true);
        PanoramaInputDTO.SceneInputDTO sceneInputDTO = new PanoramaInputDTO.SceneInputDTO("场景", SceneType.ENVIRONMENT, true, ImmutableMap.of("0度", photoInputDTO));

        PanoramaInputDTO panoramaInputDTO = new PanoramaInputDTO("全景", Collections.singletonList(sceneInputDTO));
        String createPanorama = panoramaService.createPanorama(panoramaInputDTO.toDomainObject());
        Mockito.verify(iPanoramaRepository, times(2)).save(captor.capture());
        Panorama panorama = captor.getValue();
        assertNotNull(createPanorama);
        assertNotNull(panorama);
        assertEquals("全景", panorama.getName());
        assertEquals(1, panorama.getScenes().size());
        assertEquals("场景", panorama.getScenes().get(0).getName());
        assertNotNull(panorama.getScenes().get(0).getPhotos());
        assertEquals(new Hashids("This is VR SaaS salt", 7).encode(panorama.getId()), panorama.getPanoramaUrl());
    }

    @Test
    public void should_find_all_panoramas_by_page_and_size() {
        Mockito.when(iPanoramaRepository.findByPage(anyInt(), anyInt())).thenReturn(asList(benzPano, bmwPano));
        Mockito.when(iPanoramaRepository.countByPage()).thenReturn(2L);
        PanoramaService.PanoramaSearchResult result = panoramaService.searchPanoramas("", 0, 1);
        assertNotNull(result);

        assertEquals(2, result.getCount().intValue());
        assertEquals(2, result.getPanoramas().size());
        assertEquals(bmwPano.getName(), result.getPanoramas().get(1).getName());
        assertEquals(benzPano.getPanoramaUrl(), result.getPanoramas().get(0).getPanoramaUrl());
    }

    @Test
    public void should_search_panorama_by_name() {
        Mockito.when(iSearchPanoramaRepository.findAllByNameAndIsDeletedFalse(anyString(), anyInt(), anyInt()))
                .thenReturn(new PanoramaService.PanoramaSearchResult(1L, Collections.singletonList(bmwPano)));
        PanoramaService.PanoramaSearchResult result = panoramaService.searchPanoramas("bmw", 1, 1);
        assertNotNull(result);
        assertEquals(1, result.getCount().intValue());
        assertEquals(1, result.getPanoramas().size());
        assertEquals(bmwPano.getName(), result.getPanoramas().get(0).getName());
        assertEquals(bmwPano.getPanoramaUrl(), result.getPanoramas().get(0).getPanoramaUrl());
    }

    @Test
    public void should_get_panorama_by_url() {
        Mockito.when(iPanoramaRepository.findOneByUrl(benzPano.getPanoramaUrl())).thenReturn(Collections.singletonList(benzPano));
        Panorama result = panoramaService.get(benzPano.getPanoramaUrl());
        assertNotNull(result);
        assertEquals(benzPano.getName(), result.getName());
        assertEquals(benzPano.getId(), result.getId());
        assertEquals(benzPano.getPanoramaUrl(), result.getPanoramaUrl());
        assertEquals(2, benzPano.getScenes().size());
        assertEquals(6, benzPano.getScenes().get(0).getPhotos().size());
    }

    @Test
    public void should_delete_panorama_by_id() {
        Mockito.when(iPanoramaRepository.findById(anyLong())).thenReturn(benzPano);

        if (Objects.nonNull(benzPano.getId())) {
            panoramaService.deletePanorama(benzPano.getId());
        }

        Mockito.verify(iPanoramaRepository, times(1)).save(captor.capture());
        Panorama panorama = captor.getValue();
        assertNotNull(panorama);
        assertTrue(panorama.getIsDeleted());
        assertTrue(panorama.getScenes().get(0).getIsDeleted());
    }

    @Test
    public void should_update_panorama_and_corresponding_scenes() throws ParseException {
        Scene updatedScene = new Scene(
                1L,
                1L,
                "Benz 0 modified",
                SceneType.PRODUCT,
                false,
                false,
                ImmutableMap.of(
                        "0", new PhotoInfo("url0", true),
                        "1", new PhotoInfo("url1", false),
                        "2", new PhotoInfo("url2", false),
                        "3", new PhotoInfo("url3", false)
                ),
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-29"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-29")
        );
        Scene newScene = new Scene(
                3L,
                1L,
                "Benz 2 new",
                SceneType.ENVIRONMENT,
                true,
                false,
                ImmutableMap.of("0", new PhotoInfo("url11", true)),
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-29"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-29")
        );
        Panorama updatedBenzPano = new Panorama(
                1L,
                "Benz Modified",
                asList(updatedScene, newScene),
                false,
                "74gonwE",
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-29"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-29")
        );

        Mockito.when(iPanoramaRepository.findById(benzPano.getId())).thenReturn(benzPano);
        panoramaService.updatePanorama(benzPano.getId(), updatedBenzPano);

        Mockito.verify(iPanoramaRepository, times(1)).save(captor.capture());
        Panorama value = captor.getValue();
        assertEquals(benzPano.getId(), value.getId());
        assertEquals(updatedBenzPano.getName(), value.getName());
        assertFalse(value.getIsDeleted());
        assertEquals(benzPano.getPanoramaUrl(), value.getPanoramaUrl());
        assertEquals(benzPano.getCreateTime(), value.getCreateTime());
        assertNotEquals(benzPano.getUpdateTime(), value.getUpdateTime());

        Scene capturedUpdatedScene = value.getScenes().stream()
                .filter(scene -> scene.getId().intValue() == 1).findFirst().get();
        Scene originUpdatedScene = benzPano.getScenes().stream()
                .filter(scene -> scene.getId().intValue() == 1).findFirst().get();

        assertEquals(updatedScene.getName(), capturedUpdatedScene.getName());
        assertEquals(updatedScene.getType(), capturedUpdatedScene.getType());
        assertEquals(updatedScene.getIsInitialShow(), capturedUpdatedScene.getIsInitialShow());
        assertFalse(capturedUpdatedScene.getIsDeleted());
        assertEquals(updatedScene.getPhotos().size(), capturedUpdatedScene.getPhotos().size());
        assertEquals(originUpdatedScene.getCreateTime(), capturedUpdatedScene.getCreateTime());
        assertNotEquals(originUpdatedScene.getUpdateTime(), capturedUpdatedScene.getUpdateTime());

        Scene capturedDeletedScene = value.getScenes().stream()
                .filter(scene -> scene.getId().intValue() == 2).findFirst().get();
        Scene originDeletedScene = benzPano.getScenes().stream()
                .filter(scene -> scene.getId().intValue() == 2).findFirst().get();

        assertTrue(capturedDeletedScene.getIsDeleted());
        assertNotEquals(originDeletedScene.getUpdateTime(), capturedDeletedScene.getUpdateTime());

        Scene capturedNewScene = value.getScenes().stream()
                .filter(scene -> scene.getId().intValue() == 3).findFirst().get();

        assertEquals(newScene.getName(), capturedNewScene.getName());
        assertEquals(newScene.getType(), capturedNewScene.getType());
        assertEquals(newScene.getIsInitialShow(), capturedNewScene.getIsInitialShow());
        assertFalse(capturedNewScene.getIsDeleted());
        assertEquals(newScene.getPhotos().size(), capturedNewScene.getPhotos().size());
    }

    public PanoramaServiceTest() throws ParseException {
    }
}

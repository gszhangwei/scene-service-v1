package com.scene.domain.panorama;

import com.google.common.collect.ImmutableMap;
import com.scene.adapters.inbound.rest.input.PanoramaInputDTO;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import org.hashids.Hashids;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class PanoramaServiceTest {

    private PanoramaService panoramaService;

    @Mock
    private PanoramaRepository panoramaRepository;

    @Captor
    private ArgumentCaptor<Panorama> captor;

    @Before
    public void setup() {
        panoramaService = new PanoramaService(
                panoramaRepository,
                "This is VR SaaS salt"
        );

        Mockito.when(panoramaRepository.save(any(Panorama.class))).thenAnswer(
                (Answer<Panorama>) invocation -> {
                    Panorama panorama = (Panorama) invocation.getArguments()[0];
                    if (Objects.isNull(panorama.getId())) {
                        panorama = panorama.withId(1L);
                    }
                    return panorama;
                }
        );
    }

    @Test
    public void should_save_panorama_and_scene_success_when_create_new_panorama() {
        PanoramaInputDTO.PhotoInputDTO photoInputDTO = new PanoramaInputDTO.PhotoInputDTO(UUID.randomUUID(), true);
        PanoramaInputDTO.SceneInputDTO sceneInputDTO = new PanoramaInputDTO.SceneInputDTO("场景", SceneType.ENVIRONMENT, true, ImmutableMap.of("0度", photoInputDTO));

        PanoramaInputDTO panoramaInputDTO = new PanoramaInputDTO("全景", Collections.singletonList(sceneInputDTO));
        String createPanorama = panoramaService.create(panoramaInputDTO);
        Mockito.verify(panoramaRepository, times(2)).save(captor.capture());
        Panorama panorama = captor.getValue();
        assertNotNull(createPanorama);
        assertNotNull(panorama);
        assertEquals("全景", panorama.getName());
        assertEquals(1, panorama.getScenes().size());
        assertEquals("场景", panorama.getScenes().get(0).getName());
        assertNotNull(panorama.getScenes().get(0).getPhotos());
        assertEquals(new Hashids("This is VR SaaS salt", 7).encode(panorama.getId()), panorama.getShortUrl());
    }
}

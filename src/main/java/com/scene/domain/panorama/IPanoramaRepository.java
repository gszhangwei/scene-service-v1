package com.scene.domain.panorama;

import com.scene.domain.core.IRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IPanoramaRepository extends IRepository {
    Panorama save(Panorama panorama);

    Panorama findById(long id);

    List<Panorama> searchByName(String name, int page, int size);

    long countByName(String name);

    List<Panorama> findByPage(int page, int size);

    List<Panorama> findOneByUrl(String url);

    long countByPage();
}

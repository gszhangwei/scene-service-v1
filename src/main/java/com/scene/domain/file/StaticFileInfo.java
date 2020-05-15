package com.scene.domain.file;

import com.scene.domain.core.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class StaticFileInfo implements AggregateRoot {
    private String name;
    private String mediaType;
}

package com.scene.domain.file;

import com.scene.domain.core.IAggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class StaticFileInfo implements IAggregateRoot {
    private String name;
    private String mediaType;
}

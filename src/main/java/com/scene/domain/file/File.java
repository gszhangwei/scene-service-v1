package com.scene.domain.file;

import com.scene.domain.core.AggregateRoot;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class File implements AggregateRoot {
    private UUID id;
    private byte[] data;
}

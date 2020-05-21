package com.scene.domain.core;

public abstract class Entity<T> {
    public abstract T getId();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

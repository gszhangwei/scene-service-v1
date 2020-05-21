package com.scene.domain.core;

public abstract class Entity<T> {
    public abstract T getId();

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!obj.getClass().equals(getClass())) return false;
        Entity<T> other = (Entity<T>) obj;
        return other.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}

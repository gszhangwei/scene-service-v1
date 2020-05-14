package com.scene.adapters.outbound.sqlstorage.core;

public interface IPersistenceObject<T> {
    T toDomainObject();
}

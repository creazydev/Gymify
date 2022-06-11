package com.github.Gymify.core.service;

import java.util.Collection;

public interface CollectionParentDelegate<T> {
    Collection<?> updateCollection(Collection<T> persisted, Collection<T> received);
}

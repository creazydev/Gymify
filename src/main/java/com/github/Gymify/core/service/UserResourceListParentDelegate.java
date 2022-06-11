package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.UserResource;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class UserResourceListParentDelegate<T extends UserResource> implements CollectionParentDelegate<T> {
    private CrudService<T, Serializable> crudService;

    public List<T> updateCollection(Collection<T> persisted, Collection<T> received) {
        final List<T> newList = new ArrayList<>();

        Stream
            .concat(received.stream().sequential(), persisted.stream().sequential())
            .sequential()
            .collect(
                Collectors.groupingBy(el -> {
                    final boolean savedContains = persisted.stream().anyMatch(element -> Objects.equals(el.getId(), element.getId()));
                    final boolean receivedContains = received.stream().anyMatch(element -> Objects.isNull(element.getId())
                        || Objects.equals(el.getId(), element.getId()));

                    if (savedContains && receivedContains && persisted.contains(el)) {
                        return "BOTH_SAVED"; //SKIP
                    } else if (savedContains && receivedContains) {
                        newList.add(this.crudService.update(el));
                        return "BOTH_RECEIVED";
                    } else if (savedContains) {
                        this.crudService.delete(el.getId());
                        return "SAVED";
                    } else {
                        newList.add(this.crudService.add(el));
                        return "RECEIVED";
                    }
                })
            );

        return newList;
    }
}

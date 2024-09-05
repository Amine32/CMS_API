package com.example.demo.common;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public class EntityFetcher {

    public static <T> T getEntityById(UUID id, JpaRepository<T, UUID> repository, String entityName) {
        return repository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(entityName + " with id " + id + " not found"));
    }
}

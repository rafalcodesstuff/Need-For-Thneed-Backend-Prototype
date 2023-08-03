package com.example.NeedForThneed.service;

import com.example.NeedForThneed.api.AbstractCRUDLApi;
import com.example.NeedForThneed.dto.AbstractBaseDTO;
import com.example.NeedForThneed.dto.Dto;
import com.example.NeedForThneed.entity.DistributedEntity;
import com.example.NeedForThneed.repository.DistributedRepository;
import jakarta.persistence.Entity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

abstract public class AbstractCRUDLService<ENTITY extends DistributedEntity, DTO extends AbstractBaseDTO>
        implements AbstractCRUDLApi<ENTITY, DTO> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCRUDLService.class);
    private final DistributedRepository<ENTITY> repository;
    private final Class<ENTITY> entityClass; // used for creating an entity in database
    private final Class<DTO> dtoClass;

    public AbstractCRUDLService(DistributedRepository<ENTITY> repository) {
        this.repository = repository;

        // this gets the class of ENTITY & Dto Generic Type
        final Class<?>[] params = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractCRUDLService.class);

        if (params == null) {
            throw new IllegalArgumentException("Generic types for ENTITY and DTO not found.");
        }

        Class<?> entityParam = params[0];
        if (!entityParam.isAnnotationPresent(Entity.class)) {
            String entityClassName = entityParam.getSimpleName();
            LOG.error("'{}' is not a jakarta.persistence.Entity.", entityClassName);
            throw new IllegalArgumentException(entityClassName + " is not a jakarta.persistence.Entity.");
        }
        this.entityClass = (Class<ENTITY>) entityParam;

        Class<?> dtoParam = params[1];
        if (!dtoParam.isAnnotationPresent(Dto.class)) {
            String dtoClassName = dtoParam.getSimpleName();
            LOG.error("'{}' is not a dto.Dto.", dtoClassName);
            throw new IllegalArgumentException(dtoClassName + " is not a dto.Dto.");
        }
        this.dtoClass = (Class<DTO>) dtoParam;
    }

    @Override
    public DTO save(DTO dto) {
        ENTITY entity;

        if (dto.isNew()) { // this checks if the entity exists in the database (if id is set)
            entity = mapEntityOfDTO(dto);
            entity.setCreated(LocalDateTime.now());
        } else {
            entity = Optional.of(findEntityById(dto.getId())).orElseThrow(() -> {
                LOG.error("Failed to save entity of class '{}'", entityClass.getSimpleName());
                throw new RuntimeException("Failed to find " + entityClass.getSimpleName().replace("Entity", ""));
            });
        }

        entity.setModified(LocalDateTime.now());
        ENTITY savedEntity = repository.save(entity);
        return mapEntityToDTO(savedEntity);
    }

    @Override
    public DTO getById(Integer id) {
        ENTITY entity = Optional.of(findEntityById(id)).orElseThrow(() -> {
            LOG.error("Failed to find entity with ID '{}'", id);
            throw new RuntimeException(String.format("Failed to find %s with ID: %d",
                    entityClass.getSimpleName().replace("Entity", ""), id)
            );
        });
//        return converter.convert(entity);
        return mapEntityToDTO(entity);
    }

    @Override
    public List<DTO> list() {
        List<ENTITY> entities = repository.findAll(); // change later to pagination
        return getDtos(entities);
    }

    @Override
    public Boolean delete(Integer id) {
        ENTITY entity = findEntityById(id);

        if (entity == null) {
            LOG.error("Failed to delete entity with ID '{}' as it does not exist", id);
            return false;
        }

        try {
            repository.delete(entity);
            return true;
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    private ENTITY findEntityById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    // TODO fix this or bad things
    private List<DTO> getDtos(List<ENTITY> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return Optional.of(entities.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private ENTITY mapEntityOfDTO(DTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, entityClass);
    }

    private DTO mapEntityToDTO(ENTITY entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, dtoClass);
    }

}

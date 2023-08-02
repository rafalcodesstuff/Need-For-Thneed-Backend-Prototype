package com.example.NeedForThneed.service;

import com.example.NeedForThneed.api.AbstractCRUDLApi;
import com.example.NeedForThneed.dto.AbstractBaseDTO;
import com.example.NeedForThneed.entity.DistributedEntity;
import com.example.NeedForThneed.repository.DistributedRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

abstract public class AbstractCRUDLService<ENTITY extends DistributedEntity, DTO extends AbstractBaseDTO>
        implements AbstractCRUDLApi<ENTITY, DTO> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCRUDLService.class);
    private final DistributedRepository<ENTITY> repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Class<ENTITY> entityClass; // used for creating an entity in database

    public AbstractCRUDLService(DistributedRepository<ENTITY> repository) {
        this.repository = repository;

        // this gets the class of ENTITY Generic Type
        final Class<?>[] params = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractCRUDLService.class);
        this.entityClass = (Class<ENTITY>) params[0];
    }

    protected abstract void updateEntity(ENTITY entity, DTO dto);

    @Override
    public DTO save(DTO dto) {
        ENTITY entity;

        if (dto.isNew()) { // this checks if the entity exists in the database (if id is set)
            entity = initEntity();
        } else {
            entity = findEntityById(dto.getId());
        }

        if (entity == null) {
            LOG.error("Failed to save entity of class '{}'", entityClass.getSimpleName());
            throw new RuntimeException("Failed to find " + entityClass.getSimpleName().replace("Entity", ""));
//            return null;
        }

        // set modified to the current datetime
        entity.setModified(LocalDateTime.now());

        // update entity
        updateEntity(entity, dto);

        // save entity
        ENTITY savedEntity = repository.save(entity);

        // convert to DTO and return it
//        return converter.convert(savedEntity);
        return modelMapper(savedEntity, DTO);
    }

    @Override
    public DTO getById(Integer id) {
        ENTITY entity = findEntityById(id);

        if (entity == null) {
            LOG.error("Failed to find entity with ID '{}'", id);
            throw new RuntimeException("Failed to find " + entityClass.getSimpleName().replace("Entity", "") + " with ID: " + id);
//            return null;
        }

        return converter.convert(entity);
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

    private List<DTO> getDtos(List<ENTITY> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return converter.convertList(entities);
    }

    private ENTITY initEntity() {
        try {
            ENTITY entity = entityClass.getDeclaredConstructor().newInstance();
            entity.setCreated(LocalDateTime.now());

            return entity;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}

package com.example.NeedForThneed.api;

import com.example.NeedForThneed.dto.AbstractBaseDTO;
import com.example.NeedForThneed.entity.DistributedEntity;

import java.util.List;

public interface AbstractCRUDLApi<ENTITY extends DistributedEntity, DTO extends AbstractBaseDTO> {

    DTO save(DTO dto);

    DTO getById(Integer id);

    List<DTO> list();

    Boolean delete(Integer id);
}

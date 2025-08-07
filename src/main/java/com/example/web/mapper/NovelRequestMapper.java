package com.example.web.mapper;

import org.mapstruct.Mapper;

import com.example.web.domain.Novel;
import com.example.web.dto.NovelRequestDTO;

@Mapper(componentModel = "spring")
public interface NovelRequestMapper {

    Novel toEntity(NovelRequestDTO dto);

    NovelRequestDTO toDTO(Novel entity);

}

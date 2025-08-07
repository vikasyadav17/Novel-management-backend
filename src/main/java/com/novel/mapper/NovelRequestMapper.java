package com.novel.mapper;

import org.mapstruct.Mapper;

import com.novel.domain.Novel;
import com.novel.dto.NovelRequestDTO;

@Mapper(componentModel = "spring")
public interface NovelRequestMapper {

    Novel toEntity(NovelRequestDTO dto);

    NovelRequestDTO toDTO(Novel entity);

}

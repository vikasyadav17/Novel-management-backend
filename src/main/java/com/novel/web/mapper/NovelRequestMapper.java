package com.novel.web.mapper;

import org.mapstruct.Mapper;

import com.novel.web.domain.Novel;
import com.novel.web.dto.NovelRequestDTO;

@Mapper(componentModel = "spring")
public interface NovelRequestMapper {

    Novel toEntity(NovelRequestDTO dto);

    NovelRequestDTO toDTO(Novel entity);

}

package com.novel.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.novel.web.domain.Novel;
import com.novel.web.domain.NovelDetails;
import com.novel.web.dto.request.NovelDetailsRequestDTO;
import com.novel.web.dto.request.NovelRequestDTO;

@Mapper(componentModel = "spring")
public interface NovelRequestMapper {

    Novel toEntity(NovelRequestDTO dto);

    NovelRequestDTO toDTO(Novel entity);

    NovelDetailsRequestDTO toDTo(NovelDetails novelDetails);

    List<NovelRequestDTO> toDTOList(List<Novel> novels);

}

package com.novel.web.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.novel.web.controller.NovelController;
import com.novel.web.domain.Novel;

import com.novel.web.dto.request.NovelRequestDTO;
import com.novel.web.mapper.NovelRequestMapper;
import com.novel.web.service.NovelService;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@WebMvcTest(NovelController.class)
class NovelControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private NovelService novelService; // Mock service so DB is not called

        @MockitoBean
        private NovelRequestMapper novelRequestMapper; // Mock mapper

        @Test
        void testGetTotalNovels() throws Exception {
                log.info("Testing retreiving total number of novels");
                when(novelService.getNovelsCount()).thenReturn(5L);

                mockMvc.perform(get("/novels/count"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("5"));

                verify(novelService, times(1)).getNovelsCount();
        }

        @Test
        void testAddNovelsIfExists() throws Exception {
                log.info("Testing - novels addition");

                NovelRequestDTO dto = new NovelRequestDTO();
                when(novelService.addNovelIfNotExists(any(NovelRequestDTO.class))).thenReturn((1000L));

                mockMvc.perform(post("/novels").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
                                .andExpect(content().string("Novel added with id : 1000"));

                verify(novelService,
                                times(1)).addNovelIfNotExists(any(NovelRequestDTO.class));
        }

        @Test
        void testNovelsByGenre() throws Exception {
                log.info("Test : retrieve novels by genre");

                // Create any Novel list
                List<Novel> novelsList = Arrays.asList(new Novel());

                // Create any DTO list
                NovelRequestDTO dto = new NovelRequestDTO();
                dto.setGenre("Eastern Fantasy");
                List<NovelRequestDTO> dtoList = Arrays.asList(dto);

                // Mock with any() matchers
                when(novelService.findNovelByGenre(anyString()))
                                .thenReturn(novelsList);

                when(novelRequestMapper.toDTOList(any()))
                                .thenReturn(dtoList);

                // Test
                mockMvc.perform(get("/novels")
                                .param("genre", "Eastern Fantasy"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].genre").value("Eastern Fantasy"));

                verify(novelService).findNovelByGenre("Eastern Fantasy");
                verify(novelRequestMapper).toDTOList(any());

        }

        @Test
        void testUpdateNovel() throws Exception {
                log.info("Testing novel update");
                Novel updatedNovel = new Novel();
                updatedNovel.setGenre("Martial Arts");

                // forcefully setting the id value using reflection
                Field idField = Novel.class.getDeclaredField("iD");
                idField.setAccessible(true);
                idField.set(updatedNovel, 1L);

                // ID cannot be null
                // All Fields together cannot be null
                when(novelService.updateNovel(
                                anyLong(),
                                nullable(String.class),
                                nullable(String.class),
                                nullable(String.class),
                                anyString())).thenReturn(updatedNovel);

                mockMvc.perform(patch("/novels/{id}", 1L)
                                .param("genre", "Martial Arts")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.genre").value("Martial Arts"));

                verify(novelService, times(1))
                                .updateNovel(anyLong(),
                                                nullable(String.class),
                                                nullable(String.class),
                                                nullable(String.class),
                                                eq("Martial Arts"));

        }
}

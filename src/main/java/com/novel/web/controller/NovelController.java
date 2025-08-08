package com.novel.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.novel.web.domain.Novel;
import com.novel.web.dto.request.NovelRequestDTO;
import com.novel.web.mapper.NovelRequestMapper;
import com.novel.web.service.NovelService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NovelController {

    private NovelService novelService;

    @Autowired
    private NovelRequestMapper novelRequestMapper;

    public NovelController(NovelService novelService) {
        this.novelService = novelService;

    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String home() {
        return "Novel library";
    }

    @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
    public ResponseEntity<String> addNovelIfNotExists(@RequestBody NovelRequestDTO novelDTO) {
        log.info("User wants to add novel : " + novelDTO.toString() + " in the database");
        Long id = -1L;

        id = novelService.addNovelIfNotExists(novelDTO);
        if (id == -1L) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Novel not added due to error.");
        }
        return ResponseEntity.ok("Record added with ID " + id);

    }

    @RequestMapping(value = "/novel", method = RequestMethod.GET)
    public ResponseEntity<List<NovelRequestDTO>> getNovelByName(@RequestParam String name) {
        if (name != null && name.length() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "novel name must not be empty");

        List<Novel> novel = novelService.findNovelByName(name);
        if (novel.size() == 0) {
            log.error("no novel exists in the sytem that contains " + name + " keyword ");
        }
        log.info(novel.size() + " novel/s found that contains keyword " + name);
        List<NovelRequestDTO> dto = novelRequestMapper.toDTOList(novel);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    public ResponseEntity<List<NovelRequestDTO>> getNovelByGenre(@RequestParam String genre) {

        if (genre != null && genre.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "genre must not be empty");
        }
        List<Novel> novels = novelService.findNovelByGenre(genre);
        if (novels.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no novels found for the genre : " + genre);
        }

        List<NovelRequestDTO> novelsDTO = novelRequestMapper.toDTOList(novels);
        return ResponseEntity.ok(novelsDTO);
    }

}

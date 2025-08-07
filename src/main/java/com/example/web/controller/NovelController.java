package com.example.web.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.domain.Novel;
import com.example.web.dto.NovelRequestDTO;
import com.example.web.service.NovelService;

@RestController
public class NovelController {

    private NovelService novelService;

    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }

    @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
    public ResponseEntity<String> addNovelIfNotExists(@RequestBody NovelRequestDTO novelRequestDTO) {
        Long id = -1L;
        // Novel novel = new Novel("Ancient Martial God", "https://sto55.com/book/724/",
        // "Eastern Fantasy");
        // if (novel != null)
        // id = novelService.addNovelIfNotExists(novel);
        // if (id == -1L) {
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Novel
        // not added due to error.");
        // }
        // return ResponseEntity.ok("Record added with ID " + id);
        System.out.println(novelRequestDTO);
        return ResponseEntity.ok("Done");
    }

}

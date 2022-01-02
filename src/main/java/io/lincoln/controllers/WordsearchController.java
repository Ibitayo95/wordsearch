package io.lincoln.controllers;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.lincoln.services.WordGridService;

@RestController("/")
public class WordsearchController {

    @Autowired
    WordGridService wordGridService;
   
    @GetMapping("/wordgrid")
    @ResponseBody
    public String createWordGrid(@RequestParam int gridSize, @RequestParam String wordList) {

        List<String> words = Arrays.asList(wordList.split(","));
        char[][] grid = wordGridService.generateGrid(gridSize, words);
        String gridToString = "";
       
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridToString += grid[i][j] + " ";
            }
            gridToString += "\r\n";
        }
        return gridToString;
    }

}


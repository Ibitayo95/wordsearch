package io.lincoln;

import java.util.*;


public class WordSearchEarlyTest {
    public static void main(String[] args) {
        
        

            List<String> words = Arrays.asList("ONE", "TWO", "THREE");

            Grid grid = new Grid(10);

            grid.fillGrid(words);
            grid.displayGrid();
            

            
        }
    }


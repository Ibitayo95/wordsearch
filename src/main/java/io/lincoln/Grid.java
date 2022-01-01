package io.lincoln;

import java.util.*;

public class Grid {

    private int gridSize;
    private char[][] contents;
    private List<Coordinate> coordinates = new ArrayList<>();

    // inner class for coordinates
    private class Coordinate {
        int x, y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // constructor that initializes the grid size. Inside we generate coordinates
    // too
    public Grid(int gridSize) {
        this.gridSize = gridSize;
        contents = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                coordinates.add(new Coordinate(i, j));
                contents[i][j] = '_';
            }
        }
    }

    // method that fills the grid with word input
    public void fillGrid(List<String> words) {
        for (String word : words) {
            // randomise coordinates of grid
            Collections.shuffle(coordinates);

            for (Coordinate coordinate : coordinates) {
                int x = coordinate.x;
                int y = coordinate.y;
                if (doesFit(word, coordinate)) {
                    for (char c : word.toCharArray()) {
                        contents[x][y++] = c;
                    }
                    break;
                }
            }
        }
    }

    // method to display grid
    public void displayGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(contents[i][j] + " ");
            }
            System.out.println("");
        }
    }

    // method to check if word fits inside
    private boolean doesFit(String word, Coordinate coordinate) {
        if (coordinate.y + word.length() < gridSize) {
            for (int i = 0; i < word.length(); i++) {
                if (contents[coordinate.x][coordinate.y + i] != '_')
                    return false;
            }
            return true;
        }
        return false;
    }

}

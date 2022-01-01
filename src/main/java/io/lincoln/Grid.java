package io.lincoln;

import java.util.*;

public class Grid {

    private int gridSize;
    private char[][] contents;
    private List<Coordinate> coordinates = new ArrayList<>();

    private enum Direction {
        HORIZONAL,
        VERTICAL,
        DIAGONAL
    }

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
        Collections.shuffle(coordinates);

        for (String word : words) {
            // randomise coordinates of grid

            for (Coordinate coordinate : coordinates) {
                int x = coordinate.x;
                int y = coordinate.y;
                Direction selectedDirection = getDirectionForFit(word, coordinate);
                if (selectedDirection != null) {
                    switch (selectedDirection) {
                        case HORIZONAL:
                            for (char c : word.toCharArray()) {
                                contents[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y++] = c;
                            }
                            break;
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

    // method to get a random direction (vertical, horizontal or diagonal)
    private Direction getDirectionForFit(String word, Coordinate coordinate) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);

        for (Direction direction : directions) {
            if (doesFit(word, coordinate, direction)) {
                return direction;
            }
        }
        return null;
    }

    // check does the word fit inside the grid given the coordinates and direction
    private boolean doesFit(String word, Coordinate coordinate, Direction direction) {
        int wordLength = word.length();
        switch (direction) {
            case HORIZONAL:
                if (coordinate.y + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y + i] != '_')
                        return false;
                }
                break;
            case VERTICAL:
                if (coordinate.x + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y] != '_')
                        return false;
                }
                break;
            case DIAGONAL:
                if (coordinate.y + wordLength > gridSize || coordinate.x + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y + i] != '_')
                        return false;
                }
                break;

        }
        return true;
    }

}

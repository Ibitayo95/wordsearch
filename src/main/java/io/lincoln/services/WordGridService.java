package io.lincoln.services;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


import org.springframework.stereotype.Service;
@Service
public class WordGridService {

    private enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE
    }

    // inner class for coordinates
    private class Coordinate {
        int x, y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // method that fills the grid with word input
    public char[][] generateGrid(int gridSize, List<String> words) {
        List<Coordinate> coordinates = new ArrayList<>();

        // create new grid
        char[][] contents = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                coordinates.add(new Coordinate(i, j));
                contents[i][j] = '_';
            }
        }
        // randomise coordinates of grid
        Collections.shuffle(coordinates);
        for (String word : words) {

            for (Coordinate coordinate : coordinates) {
                int x = coordinate.x;
                int y = coordinate.y;
                Direction selectedDirection = getDirectionForFit(contents, word, coordinate);
                // take chosen words, put in charArray and lay out in given direction
                if (selectedDirection != null) {
                    switch (selectedDirection) {
                        case HORIZONTAL:
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
                        case HORIZONTAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x][y--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y--] = c;
                            }
                            break;
                    }
                    break;
                }
            }
        }
        randomfillGrid(contents);
        return contents;
    }

    // method to display grid
    public void displayGrid(char[][] contents) {
        int gridSize = contents[0].length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(contents[i][j] + " ");
            }
            System.out.println("");
        }
    }


    // populate the rest of the grid with random letters
    private void randomfillGrid(char[][] contents) {
        int gridSize = contents[0].length;
        String allCapLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (contents[i][j] == '_') {
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
                    contents[i][j] = allCapLetters.charAt(randomIndex);
                }
            }
        }
    }

    // method to get a random direction (vertical, horizontal or diagonal)
    private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);

        for (Direction direction : directions) {
            if (doesFit(contents, word, coordinate, direction)) {
                return direction;
            }
        }
        return null;
    }

    // check does the word fit inside the grid given the coordinates and direction
    private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {
        int gridSize = contents[0].length;
        int wordLength = word.length();
        switch (direction) {
            case HORIZONTAL:
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

            case HORIZONTAL_INVERSE:
                if (coordinate.y < wordLength)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y - i] != '_')
                        return false;
                }
                break;
            case VERTICAL_INVERSE:
                if (coordinate.x < wordLength)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y] != '_')
                        return false;
                }
                break;
            case DIAGONAL_INVERSE:
                if (coordinate.y < wordLength || coordinate.x < wordLength)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y - i] != '_')
                        return false;
                }
                break;

        }
        return true;
    }

}

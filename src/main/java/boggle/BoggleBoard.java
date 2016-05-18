package boggle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class BoggleBoard {
    private final int rows;
    private final int cols;
    private final char[][] board;
    final char outOfBounds = (char) 0;

    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard() {
        rows = 4;
        cols = 4;
        board = randomBoard(rows, cols);
    }

    // Initializes a random M-by-N Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int M, int N) {
        rows = M;
        cols = N;
        board = randomBoard(rows, cols);
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename) throws IOException {
        Stream<String> lines = Files.lines(Paths.get(filename));
        List<String> boardLines = new ArrayList<>();
        lines.forEach(boardLines::add);
        if (boardLines.size() > 0) {
            board = linesToBoard(boardLines);
            rows = board.length;
            cols = board[0].length;
        } else {
            rows = 0;
            cols = 0;
            board = new char[0][0];
        }
    }

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a) {
        rows = a.length;
        cols = a.length > 0 ? a[0].length : 0;
        board = a;
    }

    // Returns the number of rows.
    public int rows() {
        return rows;
    }

    // Returns the number of columns.
    public int cols() {
        return cols;
    }

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j) {
        if (i >= 0 && i < rows() && j >= 0 && j < cols) {
            return board[i][j];
        } else {
            return outOfBounds;
        }
    }

    // Returns a string representation of the board.
    public String toString() {
        List<String> rows = new ArrayList<>();
        for (char[] aBoard : board) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < board[0].length; c++) {
                line.append(aBoard[c]);
            }
            rows.add(line.toString());
        }
        return rows.toString();
    }

    private char[][] randomBoard(int rows, int cols) {
        char[][] board = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = randomLetter();
            }
        }
        return board;
    }

    private char randomLetter() {
        Random random = new Random();
        return (char) ('A' + random.nextInt(26));
    }

    private char[][] linesToBoard(List<String> boardLines) {
        int rows = boardLines.size();
        int cols = boardLines.get(0).length();
        char[][] board = new char[rows][cols];
        int r = 0;
        for (String line : boardLines) {
            int c = 0;
            for (char ch : line.toCharArray()) {
                board[r][c++] = ch;
            }
            r++;
        }
        return board;
    }
}

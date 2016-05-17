package boggle;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Tests {@link BoggleBoard}.
 */
public class BoggleBoardTest {
    @Test
    public void defaultBoardIsValid() {
        BoggleBoard boggleBoard = new BoggleBoard();

        assertEquals(4, boggleBoard.rows());
        assertEquals(4, boggleBoard.cols());
        assertTrue(validateBoard(boggleBoard));
    }

    @Test
    public void boardWithSpecifiedRowsColsIsValid() {
        BoggleBoard boggleBoard = new BoggleBoard(5, 5);

        assertEquals(5, boggleBoard.rows());
        assertEquals(5, boggleBoard.cols());
        assertTrue(validateBoard(boggleBoard));
    }

    @Test
    public void boardFromCharArrayIsValid() {
        BoggleBoard boggleBoard = new BoggleBoard();
        char[][] board = new char[boggleBoard.rows()][boggleBoard.cols()];

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = boggleBoard.getLetter(r, c);
            }
        }

        boggleBoard = new BoggleBoard(board);

        assertEquals(4, boggleBoard.rows());
        assertEquals(4, boggleBoard.cols());
        assertTrue(validateBoard(boggleBoard));
    }

    @Test
    public void boardFromFileIsValid() throws IOException {
        BoggleBoard boggleBoard = new BoggleBoard(createBoardFile());

        assertEquals(4, boggleBoard.rows());
        assertEquals(4, boggleBoard.cols());
        assertTrue(validateBoard(boggleBoard));
    }

    private String createBoardFile() throws IOException {
        File file = File.createTempFile("boggle-board-", "txt");
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(new FileOutputStream(file));
            BoggleBoard boggleBoard = new BoggleBoard();

            for (int r = 0; r < boggleBoard.rows(); r++) {
                StringBuilder line = new StringBuilder();
                for (int c = 0; c < boggleBoard.cols(); c++) {
                    line.append(boggleBoard.getLetter(r, c));
                }
                printWriter.write(String.format("%s%n", line.toString()));
            }
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return file.getPath();
    }

    private boolean validateBoard(BoggleBoard boggleBoard) {
        if (boggleBoard == null || boggleBoard.rows() == 0 || boggleBoard.cols() == 0) {
            return false;
        } else {
            for (int r = 0; r < boggleBoard.rows(); r++) {
                for (int c = 0; c < boggleBoard.cols(); c++) {
                    char letter = boggleBoard.getLetter(r, c);
                    if (letter < 'A' || letter > 'Z') {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

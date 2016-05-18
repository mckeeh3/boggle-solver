package boggle;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Tests {@link BoggleSolver}.
 */
public class BoggleSolverTest {
    private BoggleSolver boggleSolver;

    @Test
    public void loadLargeDictionaryFile() throws IOException {
        char[][] dice = {
                {'A', 'T', 'E', 'E'},
                {'A', 'P', 'Y', 'O'},
                {'T', 'I', 'N', 'U'},
                {'E', 'D', 'S', 'E'}
        };

        int score = solve(dice);
        assertEquals(281, score);
    }

    @Test
    public void loadLargeDictionaryFileSolve() throws IOException {
        char[][] dice = {
                {'G', 'N', 'E', 'S'},
                {'S', 'R', 'I', 'P'},
                {'E', 'T', 'A', 'L'},
                {'T', 'S', 'E', 'B'}
        };

        int score = solve(dice);
        assertEquals(4540, score);
    }

    @Test
    public void loadLargeDictionary4pointsBoard() throws IOException {
        char[][] dice = {
                {'Y', 'L', 'V', 'E'},
                {'T', 'J', 'S', 'N'},
                {'T', 'F', 'N', 'X'},
                {'P', 'M', 'D', 'N'}
        };

        int score = solve(dice);
        assertEquals(4, score);
    }

    @Test
    public void loadLargeDictionary100pointsBoard() throws IOException {
        char[][] dice = {
                {'X', 'E', 'H', 'E'},
                {'J', 'L', 'F', 'V'},
                {'D', 'E', 'R', 'L'},
                {'I', 'M', 'M', 'O'}
        };

        int score = solve(dice);
        assertEquals(100, score);
    }

    @Test
    public void solve1000boardsAndTimeIt() {
        long elapsed = solve(1000);
        assertTrue(elapsed < 2000);
    }

    @Before
    public void setup() throws IOException {
        String filename = "dictionary-large.dat";
        URL dictionaryUrl = getClass().getClassLoader().getResource(filename);
        boggleSolver = null;
        if (dictionaryUrl != null) {
            boggleSolver = new BoggleSolver(dictionaryUrl.getFile());
        } else {
            throw new FileNotFoundException(String.format("Unable to load dictionary file '%s'", filename));
        }
    }

    private int solve(char[][] dice) throws IOException {
        int score = 0;
        BoggleBoard boggleBoard = new BoggleBoard(dice);

        for (String word : boggleSolver.getAllValidWords(boggleBoard)) {
            score += boggleSolver.scoreOf(word);
        }
        return score;
    }

    private long solve(int iterations) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            BoggleBoard boggleBoard = new BoggleBoard();
            boggleSolver.getAllValidWords(boggleBoard);
        }

        return System.currentTimeMillis() - start;
    }
}

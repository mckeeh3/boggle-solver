package boggle;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Tests {@link BoggleSolver}.
 */
public class BoggleSolverTest {
    @Test
    public void loadSmallDictionaryFile() throws IOException {
        String filename = "dictionary-small.dat";
        URL dictionaryUrl = getClass().getClassLoader().getResource(filename);
        BoggleSolver boggleSolver = null;
        if (dictionaryUrl != null) {
            boggleSolver = new BoggleSolver(dictionaryUrl.getFile());
        } else {
            throw new FileNotFoundException(String.format("Unable to load dictionary file '%s'", filename));
        }

        char[][] dice = {
                {'A', 'T', 'E', 'E'},
                {'A', 'P', 'Y', 'O'},
                {'T', 'I', 'N', 'U'},
                {'E', 'D', 'S', 'E'}
        };

        BoggleBoard boggleBoard = new BoggleBoard(dice);
        for (String word : boggleSolver.getAllValidWords(boggleBoard)) {
            assertNotNull(word);
        }
    }

    @Test
    public void loadLargeDictionaryFile() throws IOException {
        String filename = "dictionary-large.dat";
        URL dictionaryUrl = getClass().getClassLoader().getResource(filename);
        BoggleSolver boggleSolver = null;
        if (dictionaryUrl != null) {
            boggleSolver = new BoggleSolver(dictionaryUrl.getFile());
        } else {
            throw new FileNotFoundException(String.format("Unable to load dictionary file '%s'", filename));
        }

        char[][] dice = {
                {'A', 'T', 'E', 'E'},
                {'A', 'P', 'Y', 'O'},
                {'T', 'I', 'N', 'U'},
                {'E', 'D', 'S', 'E'}
        };

        BoggleBoard boggleBoard = new BoggleBoard(dice);
        for (String word : boggleSolver.getAllValidWords(boggleBoard)) {
            assertNotNull(word);
        }
    }
}

package boggle;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests {@link Dictionary}.
 */
public class DictionaryTest {
    @Test
    public void loadAndLookupSomeWords() throws IOException {
        String filename = createDictionaryFile();
        Dictionary dictionary = new Dictionary();
        dictionary.addWords(filename);

        assertEquals(Dictionary.LookupResult.Partial, dictionary.lookup("P"));
        assertEquals(Dictionary.LookupResult.Partial, dictionary.lookup("PO"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("POP"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("POPE"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("POPES"));
        assertNotEquals(Dictionary.LookupResult.Partial, dictionary.lookup("POPES"));
        assertEquals(Dictionary.LookupResult.No, dictionary.lookup("POPESHIP"));
        assertEquals(Dictionary.LookupResult.No, dictionary.lookup("Z"));
    }

    private String createDictionaryFile() throws IOException {
        File dictionaryFile = File.createTempFile("boggle-dictionary-", "dat");
        PrintWriter dictionaryWriter = null;
        try {
            dictionaryWriter = new PrintWriter(new FileOutputStream(dictionaryFile));
            loadDictionaryFile(dictionaryWriter);
        } finally {
            if (dictionaryWriter != null) {
                dictionaryWriter.close();
            }
        }
        return dictionaryFile.getPath();
    }

    private void loadDictionaryFile(PrintWriter dictionaryWriter) {
        List<String> words = new ArrayList<>();
        words.add("POP");
        words.add("POPE");
        words.add("POPES");
        words.stream().forEach(dictionaryWriter::println);
    }
}

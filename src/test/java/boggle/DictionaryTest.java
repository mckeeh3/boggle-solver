package boggle;

import org.junit.Test;

import java.io.*;
import java.net.URL;
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

    @Test
    public void loadFromSmallWordFile() throws IOException {
        String filename = "dictionary-small.dat";
        URL dictionaryUrl = getClass().getClassLoader().getResource(filename);

        Dictionary dictionary = new Dictionary();
        if (dictionaryUrl != null) {
            dictionary.addWords(dictionaryUrl.getFile());
        }

        assertEquals(Dictionary.LookupResult.Partial, dictionary.lookup("D"));
        assertEquals(Dictionary.LookupResult.Partial, dictionary.lookup("DE"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("DEBUG"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("DEBUGGED"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("OBSERVATIONS"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("PALINDROME"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("UNIT"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("UNITE"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("UNITED"));
        assertNotEquals(Dictionary.LookupResult.Partial, dictionary.lookup("POPES"));
        assertEquals(Dictionary.LookupResult.No, dictionary.lookup("POPESHIP"));
        assertEquals(Dictionary.LookupResult.No, dictionary.lookup("X"));
    }

    @Test
    public void loadFromLargeWordFile() throws IOException {
        String filename = "dictionary-large.dat";
        URL dictionaryUrl = getClass().getClassLoader().getResource(filename);

        Dictionary dictionary = new Dictionary();
        if (dictionaryUrl != null) {
            dictionary.addWords(dictionaryUrl.getFile());
        }

        assertEquals(Dictionary.LookupResult.Partial, dictionary.lookup("D"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("DE"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("DEACIDIFICATIONS"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("DEBUG"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("DEBUGGED"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("OBSERVATIONS"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("PALINDROME"));
        assertNotEquals(Dictionary.LookupResult.Partial, dictionary.lookup("POPES"));
        assertEquals(Dictionary.LookupResult.Word, dictionary.lookup("POPESHIP"));
        assertEquals(Dictionary.LookupResult.No, dictionary.lookup("POPESHPI"));
        assertEquals(Dictionary.LookupResult.Partial, dictionary.lookup("X"));
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

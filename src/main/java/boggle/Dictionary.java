package boggle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Dictionary {
    private final Map<Character, Letter> letters = new HashMap<>();

    LookupResult lookup(String letters) {
        if (letters == null || letters.isEmpty()) {
            return LookupResult.No;
        } else if (this.letters.containsKey(letters.charAt(0))) {
            return this.letters.get(letters.charAt(0)).lookup(letters);
        } else {
            return LookupResult.No;
        }
    }

    void addWords(String dictionaryFilename) throws IOException {
        Stream<String> lines = Files.lines(Paths.get(dictionaryFilename));
        lines.forEach(this::addWord);
    }

    private void addWord(String word) {
        if (letters.containsKey(word.charAt(0))) {
            Letter letter = letters.get(word.charAt(0));
            letter.addWord(word);
        } else {
            Letter letter = new Letter(word);
            letters.put(letter.letter, letter);
        }
    }

    enum LookupResult {
        No, Partial, Word
    }

    private static class Letter {
        private final char letter;
        private final Map<Character, Letter> letters = new HashMap<>();
        private final char wordTerminator = (char) 0;

        Letter(String word) {
            this(stringToList(word));
        }

        Letter(List<Character> letters) {
            letter = letters.remove(0);
            addLetters(letters);
        }

        Letter(char letter) {
            this.letter = letter;
        }

        void addWord(String word) {
            if (letter == word.charAt(0)) {
                addLetters(stringToList(word.substring(1)));
            }
        }

        LookupResult lookup(String letters) {
            return lookup(stringToList(letters));
        }

        LookupResult lookup(List<Character> letters) {
            return lookup(letters, 0);
        }

        private LookupResult lookup(List<Character> letters, int pos) {
            if (pos < letters.size() && letters.get(pos) == letter) {
                pos++;
                if (pos >= letters.size()) {
                    if (this.letters.containsKey(wordTerminator)) {
                        return LookupResult.Word;
                    } else {
                        return LookupResult.Partial;
                    }
                }
                if (pos < letters.size() && this.letters.containsKey(letters.get(pos))) {
                    Letter nextLetter = this.letters.get(letters.get(pos));
                    return nextLetter.lookup(letters, pos);
                } else {
                    return LookupResult.No;
                }
            } else {
                return LookupResult.No;
            }
        }

        private static List<Character> stringToList(String word) {
            List<Character> letters = new ArrayList<>();
            for (char c : word.toCharArray()) {
                letters.add(c);
            }
            return letters;
        }

        private void addLetters(List<Character> letters) {
            if (letters.size() > 0) {
                char letter = letters.remove(0);
                Letter letterNode = addLetter(letter);
                letterNode.addLetters(letters);
            } else {
                this.letters.put(wordTerminator, new Letter(wordTerminator));
            }
        }

        private Letter addLetter(char letter) {
            Letter letterNode = letters.get(letter);
            if (letterNode == null) {
                letterNode = new Letter(letter);
                letters.put(letter, letterNode);
            }
            return letterNode;
        }

        @Override
        public String toString() {
            return String.format("%s[%c]", getClass().getSimpleName(), letter);
        }
    }
}

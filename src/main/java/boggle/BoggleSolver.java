package boggle;

import java.io.IOException;
import java.util.*;

/**
 * An implementation of a boggle board solver.
 */
public class BoggleSolver {
    private final Dictionary dictionary = new Dictionary();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary.addWords(dictionary);
    }

    public BoggleSolver(String dictionaryFilename) throws IOException {
        this.dictionary.addWords(dictionaryFilename);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        List<String> validWords = new ArrayList<>();
        Queue<Track> tracks = new LinkedList<>();

        for (int r = 0; r < board.cols(); r++) {
            for (int c = 0; c < board.cols(); c++) {
                tracks.add(new Track(new BoardLetter(board.getLetter(r, c), r, c)));
            }
        }
        while (!tracks.isEmpty()) {
            Track track = tracks.poll();
            List<Track> moves = track.moves(board);
            for (Track move : moves) {
                Dictionary.LookupResult lookupResult = dictionary.lookup(move.letters());
                if (lookupResult.equals(Dictionary.LookupResult.Partial)) {
                    tracks.add(move);
                } else if (lookupResult.equals(Dictionary.LookupResult.Word)) {
                    tracks.add(move);
                    if (scoreOf(move.letters()) > 0) {
                        validWords.add(move.letters());
                    }
                }
            }
        }
        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int length = word.length() + (word.indexOf('Q') < 0 ? 0 : 1);
        int score = 0;

        if (length > 2 && length <= 4) {
            score = 1;
        } else if (length == 5) {
            score = 2;
        } else if (length == 6) {
            score = 3;
        } else if (length == 7) {
            score = 4;
        } else if (length > 7) {
            score = 11;
        }
        return score;
    }

    private static class Track {
        enum Move {
            Up, UpRight, Right, DownRight, Down, DownLeft, Left, UpLeft
        }

        final BoardLetter start;
        final List<BoardLetter> letters = new ArrayList<>();
        final Set<Location> locations = new HashSet<>();

        Track(BoardLetter start) {
            this.start = start;
            letters.add(start);
            locations.add(start.location);
        }

        Track(BoardLetter start, List<BoardLetter> letters, Set<Location> locations) {
            this.start = start;
            this.letters.addAll(letters);
            this.locations.addAll(locations);
        }

        List<Track> moves(BoggleBoard boggleBoard) {
            List<Track> tracks = new ArrayList<>();

            addToTracks(move(Move.Up, boggleBoard), tracks);
            addToTracks(move(Move.UpRight, boggleBoard), tracks);
            addToTracks(move(Move.Right, boggleBoard), tracks);
            addToTracks(move(Move.DownRight, boggleBoard), tracks);
            addToTracks(move(Move.Down, boggleBoard), tracks);
            addToTracks(move(Move.DownLeft, boggleBoard), tracks);
            addToTracks(move(Move.Left, boggleBoard), tracks);
            addToTracks(move(Move.UpLeft, boggleBoard), tracks);

            return tracks;
        }

        private void addToTracks(Track track, List<Track> tracks) {
            if (track != null) {
                tracks.add(track);
            }
        }

        private Track move(Move direction, BoggleBoard boggleBoard) {
            BoardLetter last = letters.get(letters.size() - 1);
            Location next = move(last.location, direction);
            char letter = boggleBoard.getLetter(next.row, next.col);

            if (letter != boggleBoard.outOfBounds && !locations.contains(next)) {
                Track movedTrack = new Track(start, letters, locations);

                movedTrack.letters.add(new BoardLetter(letter, next.row, next.col));
                movedTrack.locations.add(next);

                return movedTrack;
            } else {
                return null;
            }
        }

        private Location move(Location location, Move direction) {
            int row = location.row;
            int col = location.col;
            if (direction.equals(Move.Up)) {
                row -= 1;
            } else if (direction.equals(Move.UpRight)) {
                row -= 1;
                col += 1;
            } else if (direction.equals(Move.Right)) {
                col += 1;
            } else if (direction.equals(Move.DownRight)) {
                row += 1;
                col += 1;
            } else if (direction.equals(Move.Down)) {
                row += 1;
            } else if (direction.equals(Move.DownLeft)) {
                row += 1;
                col -= 1;
            } else if (direction.equals(Move.Left)) {
                col -= 1;
            } else if (direction.equals(Move.UpLeft)) {
                row -= 1;
                col -= 1;
            }
            return new Location(row, col);
        }

        private String letters() {
            StringBuilder word = new StringBuilder();
            letters.stream().forEach(letter -> word.append(letter.letter));
            return word.toString();
        }

        @Override
        public String toString() {
            return String.format("%s[%s]", getClass().getSimpleName(), letters());
        }
    }

    private static class BoardLetter {
        final char letter;
        final Location location;

        BoardLetter(char letter, int row, int col) {
            this.letter = letter;
            this.location = new Location(row, col);
        }

        @Override
        public String toString() {
            return String.format("%s[%c, %s]", getClass().getSimpleName(), letter, location);
        }
    }

    private static class Location {
        final int row;
        final int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Location location = (Location) o;

            return row == location.row && col == location.col;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
        }

        @Override
        public String toString() {
            return String.format("%s[%d, %d]", getClass().getSimpleName(), row, col);
        }
    }
}

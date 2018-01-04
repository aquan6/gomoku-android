package andy_stefan.gomoku_android;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Tracks boards and pieces player has purchased. Saves to shared preferences file.
 */

public class UnlockManager {

    // path where unlooks will be stored. They will store the BoardType and PieceType enum Strings, line-separated
    private static final String UNLOCKED_BOARDS_KEY = "com.andy_stefan.gomoku_android.UNLOCKED_BOARDS_KEY";
    private static final String UNLOCKED_PIECES_KEY = "com.andy_stefan.gomoku_android.UNLOCKED_PIECES_KEY";

    private List<BoardType> unlockedBoards = new LinkedList<>();
    private List<PieceType> unlockedPieces = new LinkedList<>();

    // files where board and pieces are saved
    private File boardFile, piecesFile;

    // constuctor reads the files and populates unlockedBoards and unlockedPieces lists
    public UnlockManager(Context context) {
        boardFile = new File(context.getFilesDir(), UNLOCKED_BOARDS_KEY);
        piecesFile = new File(context.getFilesDir(), UNLOCKED_PIECES_KEY);
        try {
            String saved_boards = FileUtil.readFromFile(boardFile);
            for (String board_name : saved_boards.split("\n")) {
                unlockedBoards.add(BoardType.valueOf(board_name));
            }
        } catch (IOException e) {
            // add default boards (price = 0)
            for (BoardType type : BoardType.values()) {
                if (type.getPrice() == 0) {
                    unlockedBoards.add(type);
                }
            }
        }
        try {
            String saved_pieces = FileUtil.readFromFile(piecesFile);
            for (String piece_name : saved_pieces.split("\n")) {
                unlockedPieces.add(PieceType.valueOf(piece_name));
            }
        } catch (IOException e) {
            // add default pieces (price = 0)
            for (PieceType type : PieceType.values()) {
                if (type.getPrice() == 0) {
                    unlockedPieces.add(type);
                }
            }
        }
    }

    public List<BoardType> getUnlockedBoards() {
        return unlockedBoards;
    }

    public List<PieceType> getUnlockedPieces() {
        return unlockedPieces;
    }

    // adds given BoardType to list of unlocked boards if it isn't already
    public void addUnlockedBoardType(BoardType unlocked) {
        if (!unlockedBoards.contains(unlocked)) {
            unlockedBoards.add(unlocked);
        }
    }

    // adds given PieceType to list of unlocked pieces if it isn't already
    public void addUnlockedPieceType(PieceType unlocked) {
        if (!unlockedPieces.contains(unlocked)) {
            unlockedPieces.add(unlocked);
        }
    }

    // saves current unlocked BoardTypes to file
    public boolean saveUnlockedBoards() {
        // write to String, line-separated
        String saved = "";
        for (BoardType type : unlockedBoards) {
            saved += type.toString() + "\n";
        }
        return FileUtil.writeToFile(boardFile, saved);
    }

    // saves current unlocked pieceTypes to file
    public boolean saveUnlockedPieces() {
        // write to String, line-separated
        String saved = "";
        for (PieceType type : unlockedPieces) {
            saved += type.toString() + "\n";
        }
        return FileUtil.writeToFile(piecesFile, saved);
    }
}

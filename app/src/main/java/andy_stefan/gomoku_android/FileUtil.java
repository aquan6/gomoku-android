package andy_stefan.gomoku_android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Provides methods for reading/writing to internal storage files.
 */

public class FileUtil {

    // writes given String to file provided
    public static boolean writeToFile(File file, String textToWrite) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(textToWrite.getBytes());
            stream.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // reads given file provided and returns String. Throws IOException if file could not be read.
    public static String readFromFile(File file) throws IOException {
        byte[] bytes = new byte[(int) file.length()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            return new String(bytes);
        } catch (IOException e) {
            throw new IOException("File not found or could not be read");
        }
    }
}

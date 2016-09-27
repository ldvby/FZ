package mobi.foodzen.foodzen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * Created by artur.egiazarov on 27.09.2016.
 */
public class FileUtils {

    public static String generateNewFileNameWithUUID(String filenameModel, final UUID uuid) {
        String extension = getFileExtension(filenameModel);
        StringBuilder generatedFileNameBuilder = new StringBuilder(uuid.toString());
        if (extension.length() != 0) {
            generatedFileNameBuilder.append(".").append(extension);
        }
        return generatedFileNameBuilder.toString();
    }

    public static String generateNewFileName(String filenameModel) {
        if (filenameModel == null) {
            filenameModel = "";
        }
        return generateNewFileNameWithUUID(filenameModel, UUID.randomUUID());
    }

    public static String getFileExtension(String file) {
        String extension = file.substring(file.lastIndexOf('.') + 1, file.length()).intern();
        if (extension.contains(System.getProperty("file.separator"))) {
            return "";
        } else if (file.length() == extension.length()) {
            return "";
        } else {
            return extension;
        }
    }

    public static void copyFile(File res, File dest) throws IOException {
        FileChannel resChannel = null;
        FileChannel destChannel = null;
        try {
            resChannel = new FileInputStream(res).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(resChannel, 0, res.length());
        } finally {
            if (resChannel != null) {
                try {
                    resChannel.close();
                } catch (IOException e) {
                }
            }
            if (destChannel != null) {
                try {
                    destChannel.close();
                } catch (IOException e) {
                }
            }
        }
    }


}

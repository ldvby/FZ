package mobi.foodzen.foodzen.files;

import java.io.File;
import java.io.IOException;

import mobi.foodzen.foodzen.FoodzenApplication;
import mobi.foodzen.foodzen.utils.FileUtils;

/**
 * Created by artur.egiazarov on 27.09.2016.
 */
public class FileManager {
    private static FileManager ourInstance = new FileManager();

    public static FileManager getInstance() {
        return ourInstance;
    }

    private FileManager() {
    }

    public String getPhotosStoragePath() {
        File file = new File(FoodzenApplication.getCurrentApplicationContext().getFilesDir(), "photos");
        if (!file.exists()) {
            synchronized (this) {
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
        return file.getAbsolutePath();
    }

    public File copyToInternalStorageWithGeneratingNewName(File inputFile) throws IOException {
        File fileOnInternalStorage = new File(getPhotosStoragePath() + File.separatorChar
                + FileUtils.generateNewFileName(inputFile.getName()));
        FileUtils.copyFile(inputFile, fileOnInternalStorage);
        return fileOnInternalStorage;
    }
}

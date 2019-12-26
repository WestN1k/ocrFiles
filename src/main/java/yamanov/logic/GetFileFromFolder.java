package yamanov.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class GetFileFromFolder {

    private ArrayList<String> filesList = new ArrayList<>();

    public ArrayList<String> listFilesForFolder(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                filesList.add(fileEntry.getPath());
            }
        }

        return filesList;
    }
}

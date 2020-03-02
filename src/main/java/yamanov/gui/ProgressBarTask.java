package yamanov.gui;

import javafx.concurrent.Task;
import yamanov.database.entities.Inbox;
import yamanov.logic.AddFromFile;
import yamanov.logic.Value;

import java.util.ArrayList;
import java.util.List;

public class ProgressBarTask extends Task<List<Inbox>> {

    private static List<String> listFiles;
    private AddFromFile addFromFile;

    public ProgressBarTask(List<String> listFiles, AddFromFile addFromFile) {
        ProgressBarTask.listFiles = listFiles;
        this.addFromFile = addFromFile;
    }

    @Override
    protected List<Inbox> call() throws Exception {
        List<Inbox> vvv = new ArrayList<>();
        for (int i = 1; i <= listFiles.size(); i++) {
            if (isCancelled()) {
                break;
            }
            updateProgress(i, listFiles.size());
            Inbox val = addFromFile.addFromFile(listFiles.get(i - 1));
            vvv.add(val);
            Thread.sleep(100);
        }
        return vvv;
    }
}

import lombok.Setter;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

@Setter
public class EncryptorThread extends Thread {
    private GUIForm form;
    private File file;
    private String password;

    public EncryptorThread(GUIForm form) {
        this.form = form;
    }

    @Override
    public void run() {
        onStart();
        try {
            String archiveName = getArchiveName();
            ZipFile zipFile = new ZipFile(archiveName, password.toCharArray());
            if (file.isDirectory()) {
                zipFile.addFolder(file, ParametersContainer.getParameters());
            } else {
                zipFile.addFile(file, ParametersContainer.getParameters());
            }
        } catch (ZipException e) {
            form.showWarning(e.getMessage());
        }
        onFinish();
    }

    private void onFinish() {
        form.showFinished();
        form.setButtonsEnabled(true);
    }

    private void onStart() {
        form.setButtonsEnabled(false);
    }

    private String getArchiveName() {
        for (int i = 1; ; i++) {
            String number = i > 1 ? "(" + i + ")" : "";
            String archiveName = file.getAbsolutePath() + number + ".enc";
            if (!new File(archiveName).exists()) {
                return archiveName;
            }
        }
    }
}

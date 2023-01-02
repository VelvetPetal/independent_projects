import lombok.Setter;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;


@Setter
public class DecryptorThread extends Thread {
    private GUIForm form;
    private File file;
    private String password;

    public DecryptorThread(GUIForm form) {
        this.form = form;
    }

    @Override
    public void run() {
        onStart();
        String outputPath = getOutputPath();
        try {
            try (ZipFile zipFile = new ZipFile(file, password.toCharArray())) {
                zipFile.extractAll(outputPath);
            }
        } catch (Exception e) {
            form.showWarning(e.getMessage());
            try {
                FileUtils.deleteDirectory(new File(outputPath));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            onFinish(true);
            return;
        }
        onFinish(false);
    }

    private void onFinish(boolean isWrongPassword) {
        form.setButtonsEnabled(true);
        if (isWrongPassword) {
            return;
        }
        form.showFinished();
    }

    private void onStart() {
        form.setButtonsEnabled(false);
    }

    private String getOutputPath() {
        String path = file.getAbsolutePath().replaceAll("\\.enc$", "");
        for (int i = 0; ; i++) {
            String number = i > 1 ? "(" + i + ")" : "";
            String outPath = path + number;
            if (!new File(outPath).exists()) {
                return outPath;
            }
        }
    }
}

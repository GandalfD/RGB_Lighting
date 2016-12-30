import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;

/**
 * Created by Darwin on 12/29/2016.
 */
public class Patterns {
        RgbController controller = new RgbController("COM9");

        AudioInputStream input;
        Clip clip;

        public Patterns() {
            try {
                input = AudioSystem.getAudioInputStream(new File("C:\\Users\\Darwin\\IdeaProjects\\RGB_Lighting\\Everything.wav"));
                clip = AudioSystem.getClip();
                clip.open(input);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        public void coolPattern() throws IOException, InterruptedException {
            controller.wipeColor("5", "55", "55", "45");
            controller.setColor("50", "0", "0");
            TimeUnit.MILLISECONDS.sleep(3700);
            controller.setColor("0", "0", "50");
            TimeUnit.MILLISECONDS.sleep(1200);
            controller.setColor("0", "100", "0");
            TimeUnit.MILLISECONDS.sleep(900);
            controller.setColor("100", "100", "100");
            TimeUnit.MILLISECONDS.sleep(6000);
            controller.allOff();
        }

        public void playTSwizzle() throws InterruptedException, IOException, LineUnavailableException {
            clip.setFramePosition(0);
            clip.start();
        }

        public void stop() {
            clip.stop();
        }
}

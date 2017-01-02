import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;

/**
 * Created by Darwin on 12/29/2016.
 */
public class Patterns implements Runnable {
        RgbController controller = new RgbController("COM9");

        AudioInputStream input;
        Clip clip;

        private int delayTime;

        Thread runner;

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
         private void coolPattern() throws IOException, InterruptedException {
            controller.wipeColor("5", "55", "55", "45");
            controller.setColor("50", "0", "0");
            TimeUnit.MILLISECONDS.sleep(3700);
            controller.setColor("0", "0", "50");
            TimeUnit.MILLISECONDS.sleep(1200);
            controller.setColor("0", "100", "0");
            TimeUnit.MILLISECONDS.sleep(900);
            controller.setColor("100", "100", "100");
            TimeUnit.MILLISECONDS.sleep(4750);
            controller.setColor("200", "50", "50");
            TimeUnit.MILLISECONDS.sleep(3850);
            controller.setColor("50", "200", "50");
            TimeUnit.MILLISECONDS.sleep(2000);
            fade(0, 45);
            TimeUnit.MILLISECONDS.sleep(1700);
            controller.theaterChase("64", "26", "189", "1700", "8");
            controller.allOff();
            TimeUnit.MILLISECONDS.sleep(1000);
            controller.theaterChaseOpposite("255", "212", "7", "1700", "13");
            TimeUnit.MILLISECONDS.sleep(6750);

            for (int i = 0; i < 5; i++) {
                TimeUnit.MILLISECONDS.sleep(2000);
                controller.allOff();
                controller.wipeColor("153", "0", "153", Integer.toString(15));
                TimeUnit.MILLISECONDS.sleep(2000);
                controller.allOff();
                controller.wipeColorOpposite("153", "0", "153", Integer.toString(15));
            }

            TimeUnit.MILLISECONDS.sleep(500);
            fadeIntoChorus();
            TimeUnit.MILLISECONDS.sleep(1150);
            chorus();
        }

        private void fadeIntoChorus() throws  IOException {
            for (int i = 0; i < 154; i++) {
                controller.setColor(Integer.toString(153 - i), "0", Integer.toString(153 - i));
            }
        }
        private void chorus() throws  IOException {
            controller.combine("255", "102", "102", Integer.toString(31));
            controller.setColor("10", "88", "255");
        }

        public void beginThread() {
            runner = new Thread(this);
            runner.start();
        }

        public void playTSwizzle() throws InterruptedException, IOException, LineUnavailableException {
            clip.setFramePosition(0);
            clip.start();
        }

        public void stop() {
            clip.stop();
            runner.interrupt();
        }

        public void setDelayTime(int delay) {
            delayTime = delay;
        }

        private void fade(int color, int delay) throws IOException, InterruptedException {
            for (int i = 0; i < 50; i++) {
                color += 5;
                controller.setColor("0", Integer.toString(color), Integer.toString(color));
                TimeUnit.MILLISECONDS.sleep(delay);
                System.out.println("Adding: " + color);
            }

            while (color > 0) {
                color -= 5;
                controller.setColor("0", Integer.toString(color), Integer.toString(color));
                TimeUnit.MILLISECONDS.sleep(delay);
                System.out.println("Subtracting: " + color);
            }
        }

    @Override
    public void run()  {
        try {
            coolPattern();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

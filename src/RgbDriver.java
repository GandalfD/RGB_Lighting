import java.io.IOException;

/**
 * Created by Darwin on 12/28/2016.
 */
public class RgbDriver {
    public static void main(String[] args) {
        /*RgbController mainController = new RgbController("COM9");

        try {
            mainController.rainbow();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } */

        RgbControllerGUI gui = new RgbControllerGUI();
        gui.display();
    }
}

/**
 * Class for communicating with the RGB Arduino
 */
import com.sun.mail.iap.Response;
import org.ardulink.core.*;
import org.ardulink.core.convenience.Links;
import org.ardulink.core.events.*;
import org.ardulink.core.qos.ResponseAwaiter;
import org.ardulink.mqtt.compactors.AnalogReadChangeListenerAdapter;
import org.ardulink.util.URIs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RgbController {
    private Link link;
    private boolean commandFinished = false;

    public RgbController(String comPort) {
        link = Links.getLink(URIs.newURI("ardulink://serial-jssc?port=" + comPort + "&baudrate=9600&pingprobe=false"));
    }

    public void setColor(String redVal, String greenVal, String blueVal) throws IOException {
        link.sendCustomMessage("colorOn", redVal, greenVal, blueVal);
    }

    public void wipeColor(String redVal, String greenVal, String blueVal, String delay) throws IOException {
        link.sendCustomMessage("wipe", redVal, greenVal, blueVal, delay);
    }

    public void theaterChase(String redVal, String greenVal, String blueVal, String delay, String cycle) throws IOException {
        link.sendCustomMessage("theaterChase", redVal, greenVal, blueVal, delay, cycle);
    }

    public void allOff() throws IOException {
        link.sendCustomMessage("off");
    }

    public void theaterChaseOpposite(String redVal, String greenVal, String blueVal, String delay, String cycle) throws IOException {
        link.sendCustomMessage("theaterChaseOpp", redVal, greenVal, blueVal, delay, cycle);
    }

    public void combine(String redVal, String greenVal, String blueVal, String delay) throws IOException {
        link.sendCustomMessage("combine", redVal, greenVal, blueVal, delay);
    }

    public void combineOpposite(String redVal, String greenVal, String blueVal, String delay) throws IOException {
        link.sendCustomMessage("combineOpp", redVal, greenVal, blueVal, delay);
    }

    public void wipeColorOpposite(String redVal, String greenVal, String blueVal, String delay) throws IOException {
        link.sendCustomMessage("wipeOpp", redVal, greenVal, blueVal, delay);
    }

    public Link getLink() {
        return link;
    }


    // bad method
    /*private void waitForGo(String command, String redVal, String greenVal, String blueVal, String delay) {
        RplyEvent rplyEvent = null;
        try {

            if (command.equals("wipe")) {
                rplyEvent = ResponseAwaiter.onLink(link)
                        .waitForResponse(link.sendCustomMessage("wipe", redVal, greenVal, blueVal, delay));
            } else if (command.equals("combine")) {
                rplyEvent = ResponseAwaiter.onLink(link)
                        .waitForResponse(link.sendCustomMessage("combine", redVal, greenVal, blueVal, delay));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
}
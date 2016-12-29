/**
 * Class for communicating with the RGB Arduino
 */
import org.ardulink.core.*;
import org.ardulink.core.convenience.Links;
import org.ardulink.util.URIs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RgbController {
    private Link link;

    public RgbController(String comPort) {
        link = Links.getLink(URIs.newURI("ardulink://serial-jssc?port=" + comPort + "&baudrate=9600&pingprobe=false"));
    }

    public void setRed(String redVal, String greenVal, String blueVal) throws IOException {
        link.sendCustomMessage("wipe", redVal, greenVal, blueVal);
    }

}
/**
 * Created by Darwin on 12/29/2016.
 */
import javafx.event.ActionEvent;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RgbControllerGUI  {

    private JFrame frame;
    private JPanel pane;

    // Slider Declaration
    private JSlider red;
    private JSlider green;
    private JSlider blue;

    // Slider Label Declaration
    private JLabel redLabel;
    private JLabel greenLabel;
    private JLabel blueLabel;

    private Integer redValue = 0;
    private Integer greenValue = 0;
    private Integer blueValue = 0;

    private RgbController controller = new RgbController("COM9");
    public RgbControllerGUI() {
        frame = new JFrame("RGB Controller");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpSlider();

        pane = new JPanel();
        pane.add(red);
        pane.add(redLabel);

        pane.add(green);
        pane.add(greenLabel);

        pane.add(blue);
        pane.add(blueLabel);

        frame.add(pane);
    }

    // Sets up the sliders and the slider labels
    private void setUpSlider() {
        red = new JSlider(JSlider.VERTICAL, 0, 255, 1);
        red.addChangeListener(new SlideListener());
        red.setMajorTickSpacing(15);
        red.setMinorTickSpacing(5);
        red.setPaintTicks(true);
        red.setPaintLabels(true);

        green = new JSlider(JSlider.VERTICAL, 0, 255, 1);
        green.addChangeListener(new SlideListener());
        green.setMajorTickSpacing(15);
        green.setMinorTickSpacing(5);
        green.setPaintTicks(true);
        green.setPaintLabels(true);

        blue = new JSlider(JSlider.VERTICAL, 0, 255, 1);
        blue.addChangeListener(new SlideListener());
        blue.setMajorTickSpacing(15);
        blue.setMinorTickSpacing(5);
        blue.setPaintTicks(true);
        blue.setPaintLabels(true);

        redLabel = new JLabel("Red: 0");
        greenLabel = new JLabel("Green: 0");
        blueLabel = new JLabel("Blue: 0");
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }

    private class SlideListener implements ChangeListener {

        public void stateChanged(ChangeEvent event) {
            redValue = new Integer(red.getValue());
            redLabel.setText("Red: " + redValue);

            blueValue = new Integer(blue.getValue());
            blueLabel.setText("Blue: " + blueValue);

            greenValue = new Integer(green.getValue());
            greenLabel.setText("Green: " + greenValue);

            try {
                controller.setRed(redValue.toString(), greenValue.toString(), blueValue.toString());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}

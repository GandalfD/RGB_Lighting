/**
 * Created by Darwin on 12/29/2016.
 */
import javafx.event.ActionEvent;
import org.ardulink.core.events.RplyEvent;
import org.ardulink.core.qos.ResponseAwaiter;
import org.ardulink.gui.RGBController;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RgbControllerGUI  {
    private Patterns pattern = new Patterns();

    private JFrame frame;
    private JPanel pane;
    private JPanel pane2;

    // Slider Declaration
    private JSlider red;
    private JSlider green;
    private JSlider blue;

    // Slider Label Declaration
    private JLabel redLabel;
    private JLabel greenLabel;
    private JLabel blueLabel;

    private JButton stop;
    private JButton taylor;
    private JButton theatherChase;
    private JButton coolPattern;

    private JTextField taylorDelay;
    private JTextField coolPatternDelay;
    private JTextField coolPatternR;
    private JTextField coolPatternG;
    private JTextField coolPatternB;
    private JLabel cpDelayLabel;
    private JLabel cpRLabel;
    private JLabel cpGLabel;
    private JLabel cpBLabel;

    private Integer redValue = 0;
    private Integer greenValue = 0;
    private Integer blueValue = 0;

    private RgbController controller = new RgbController("COM9");
    public RgbControllerGUI() {
        frame = new JFrame("RGB Controller");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpSlider();
        setUpButton();

        pane = new JPanel();
        pane.add(red);
        pane.add(redLabel);

        pane.add(green);
        pane.add(greenLabel);

        pane.add(blue);
        pane.add(blueLabel);

        setUpSecondPane();

        frame.setLayout(new GridLayout(2, 1));
        frame.add(pane2);
        frame.add(pane);
    }

    private void setUpSecondPane() {
        pane2 = new JPanel();
        pane2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Delay Label
        c.gridx = 0;
        c.gridy = 0;
        pane2.add(cpDelayLabel, c);

        // Red Label
        c.gridx = 1;
        c.gridy = 0;
        pane2.add(cpRLabel, c);

        //Green label
        c.gridx = 2;
        c.gridy = 0;
        pane2.add(cpGLabel, c);

        //Blue label
        c.gridx = 3;
        c.gridy = 0;
        pane2.add(cpBLabel, c);

        //Delay Input
        c.gridx = 0;
        c.gridy = 1;
        pane2.add(coolPatternDelay, c);

        //Red Input
        c.gridx = 1;
        c.gridy = 1;
        pane2.add(coolPatternR, c);

        //Green Input
        c.gridx = 2;
        c.gridy = 1;
        pane2.add(coolPatternG, c);

        //Blue Input
        c.gridx = 3;
        c.gridy = 1;
        pane2.add(coolPatternB, c);

        //Cool Pattern Button
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        pane2.add(coolPattern, c);

        //Theather Chase Button
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        pane2.add(theatherChase, c);

        //Taylor
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 2;
        pane2.add(taylor, c);

        //Stop
        c.gridx = 6;
        c.gridy = 2;
        c.gridwidth = 2;
        pane2.add(stop, c);

        //Taylor Delay
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        pane2.add(taylorDelay, c);
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

    private void setUpButton() {
        coolPattern = new JButton("Cool Pattern!");
        coolPattern.addActionListener(new ButtonListener());

        coolPatternR = new JTextField(3);
        coolPatternG = new JTextField(3);
        coolPatternB = new JTextField(3);
        coolPatternDelay = new JTextField(3);
        taylorDelay = new JTextField(5);

        cpRLabel = new JLabel("Red");
        cpGLabel = new JLabel("Green");
        cpBLabel = new JLabel("Blue");
        cpDelayLabel = new JLabel("Delay");

        theatherChase = new JButton("Theather Chase");
        theatherChase.addActionListener(new ButtonListener());

        taylor = new JButton("???");
        taylor.addActionListener(new ButtonListener());

        stop = new JButton("stop");
        stop.addActionListener(new ButtonListener());

    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }

    public int getTaylorDelay() {
        return Integer.parseInt(taylorDelay.getText());
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
                controller.setColor(redValue.toString(), greenValue.toString(), blueValue.toString());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try {
                if (e.getSource() == coolPattern) {
                    controller.wipeColor(coolPatternR.getText(), coolPatternG.getText(), coolPatternB.getText(), coolPatternDelay.getText());
                }
                else if (e.getSource() == theatherChase) {
                    controller.theaterChase(coolPatternR.getText(), coolPatternG.getText(), coolPatternB.getText(), coolPatternDelay.getText(), "3");
                }
                else if (e.getSource() == taylor) {
                    pattern.playTSwizzle();
                    TimeUnit.MILLISECONDS.sleep(200);
                    pattern.setDelayTime(getTaylorDelay());
                    pattern.beginThread();
                } else if (e.getSource() == stop) {
                    pattern.stop();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            }
        }
    }


}

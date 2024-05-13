import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;


public class Main{

    //public variables
    int blush = 0;
    String message = "The very memory of my face will cause a manifestation of my being in the future. You will be asleep in bed. I will be there, and watch over you. When you wake, you will not be able to move any part of you. When the doctors eventually find you, they will not see me, but you will, and I'll see you too. Forever, I'll see you.";
    JLabel label = new JLabel("Stealer");
    JFrame f = new JFrame("Liver Stealer Desktop Pet");

    // all used images that will be switched around
    ImageIcon stealer = new ImageIcon("resources/stealer - kopia.jpg");
    ImageIcon stealer_blush = new ImageIcon("resources/stealer_blush - kopia.jpg");

    ImageIcon stealer_blush_slight = new ImageIcon("resources/stealer_slight_blush - kopia.jpg");

    ImageIcon stealer_blink = new ImageIcon("resources/stealer_blink - kopia.jpg");

    // sets the current image to the default image
    JLabel currentImage = new JLabel(stealer);


    void ImageJFrame()
    {
        //setup frame
        System.out.println(message);

        f.setAlwaysOnTop( true );
        f.setUndecorated(true);
        f.add(currentImage);

        label.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        f.setVisible(true);

        FrameDragListener frameDragListener = new FrameDragListener(f);
        f.addMouseListener(new myMouseListener());
        f.addMouseListener(frameDragListener);
        f.addMouseMotionListener(frameDragListener);

        f.setSize(280, 150);

        f.setLocationRelativeTo(null);


        //setup blink timer
        Timer timer = new Timer(7000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                currentImage.setIcon(stealer_blink);
                blinkWait();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    public void blinkWait(){
        //setup blink reset timer
        Timer timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //changes origin image depending on mood variable
                if(blush == 0){
                    currentImage.setIcon(stealer);
                }else if(blush == 1){
                    currentImage.setIcon(stealer_blush_slight);
                }else if(blush == 2){
                    currentImage.setIcon(stealer_blush);
                }

            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) throws IOException {
        //declaring breathing sound
        File lol = new File("resources/breathingV2.wav");

        try{
            //looping breathing sound
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(lol));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
        Main m = new Main();
        m.ImageJFrame();

    }


    // the function below listens for mouse inout and detects clicks
    public class myMouseListener implements MouseListener{
        public void mousePressed(MouseEvent e){
            // setup squish sound when pressed.
            File lol = new File("resources/squish - kopia.wav");

            try{
                //playing squish sound
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(lol));
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20f);
                clip.start();
            } catch (Exception a){
                a.printStackTrace();
            }
            blush = 2;
            //switching to blushed image
            currentImage.setIcon(stealer_blush);
        }
        public void mouseReleased(MouseEvent e){
            blush = 0;
            currentImage.setIcon(stealer);
        }

        public void mouseEntered(MouseEvent e){
            // switching to the slightly blushed state
            blush = 1;
            currentImage.setIcon(stealer_blush_slight);
        }

        public void mouseExited(MouseEvent e){
            //resets to default state image
            blush = 0;
            currentImage.setIcon(stealer);
        }

        public void mouseClicked(MouseEvent e){

        }
    }

    // the function below allows for mouse dragging of the program since it is borderless.
    public class FrameDragListener extends MouseAdapter {
        private final JFrame frame;
        private Point mouseDownCompCoords = null;
        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        // resets coords of mouse
        public void mouseReleased(MouseEvent e){
            mouseDownCompCoords = null;
        }


        // gets coords of mouse
        public void mousePressed(MouseEvent e){
            mouseDownCompCoords = e.getPoint();
        }

        // drags the frame
        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }

}
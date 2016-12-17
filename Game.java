package basicGame;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
 
import javax.swing.JFrame;
import javax.swing.Timer;
 
public class Game {
 
        public static void runGame(Render render, JFrame frame) {
 
                ActionListener taskPerformer = new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                render.run();
                                render.drawGame(frame);
                        }
                };
 
                Timer timer = new Timer(20, taskPerformer);
                timer.start();
 
                while(true) {
                        render.run();
                        render.drawGame(frame);
                }
        }
 
        public void startGame(String[] imageURLs) throws IOException {
 
                //create the game interface
                JFrame frame = new JFrame ("Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.createBufferStrategy(2);
 
                //allows the game to stop running upon closing the game window
                frame.addWindowListener(
                                new WindowAdapter() {
                                        @Override
                                        public void windowClosing( WindowEvent e) {
                                                System.exit(0);
                                        }
                                }
                                );
 
                Render render = new Render(imageURLs);
                render.drawGame(frame);
                frame.addKeyListener(render.keyhandler);
                frame.setIconImage(render.maincharImg);
 
                System.out.println("Game starting!");
                runGame(render, frame);
 
        }
 
}

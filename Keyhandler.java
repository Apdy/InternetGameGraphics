package basicGame;
 
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
 
public class KeyHandler extends KeyAdapter {
 
        //if face == true, face right; if face == false, face left
        public boolean pressJump = false, pressRight, pressLeft, pressSpace, face = true, faceRight = true, faceLeft;
        @Override
        public void keyPressed( KeyEvent e ) {
                //ESC key closes the program
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                        System.exit(0);
 
                //Up key makes the character jump
                if (e.getKeyCode() == KeyEvent.VK_UP)
                        pressJump = true;
 
                //Right key makes the character turn and move right
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        pressRight = true;
                        face = true;
                }
 
                //Left key makes the character turn and move left
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        pressLeft = true;
                        face = false;
                }
 
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pressSpace = true;
                }
 
        }
 
        @Override
        public void keyReleased(KeyEvent e) {
                //when up key is released, stop moving the character upwards
                if (e.getKeyCode() == KeyEvent.VK_UP)
                        pressJump = false;
 
                //when the right key is released, stop moving the character right
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                        pressRight = false;
 
                //when the left key is released, stop moving the character left
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                        pressLeft = false;
 
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                        pressSpace = false;
        }
}

package basicGame;
 
public class Weapon {
 
        int xco, yco, frames;
 
        public Weapon() {
                xco = -100;
                yco = -100;
                frames = 0;
        }
 
        //spacebar pressed
        public void attack(boolean face, int charxco, int charyco) {
                if (face == true) //facing right
                        xco = charxco + 20 + frames * 3;
                if (face == false) //facing left
                        xco = charxco - 20 - frames * 3;
                yco = charyco + 20;
 
                if (frames >= 30) {
                        xco = -100;
                        yco = -100;
                }
 
                frames++;
        }
 
        //spacebar released
        public void putawayWeapon() {
                xco = -100;
                yco = -100;
                frames = 0;
        }
}

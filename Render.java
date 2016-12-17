package basicGame;
 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
 
import javax.imageio.ImageIO;
import javax.swing.JFrame;
 
public class Render implements Runnable {
        public BufferedImage background, maincharImg, weaponImg, enemyImg; //Images that will be drawn on the double-buffer image
        public BufferedImage maincharImgleft, weaponImgleft, enemyImgleft;
        public BufferedImage maincharImmune, enemyImmune;
        boolean playing;
        MainChar mainchar = new MainChar();
        Enemy enemy = new Enemy();
        public KeyHandler keyhandler = new KeyHandler();
 
        //initiates all the stuff in the game
        public Render (String[] imageURLs) throws IOException {
 
                try {
                                System.out.println("Getting images from URLs...");
                                        background = ImageIO.read(new URL(imageURLs[0]));
                                        System.out.println("Background loaded");
                                        maincharImg = ImageIO.read(new URL(imageURLs[1]));
                                        System.out.println("Main character image loaded");
                                        weaponImg = ImageIO.read(new URL(imageURLs[2]));
                                        System.out.println("Weapon image loaded");
                                        enemyImg = ImageIO.read(new URL(imageURLs[3]));
                                        System.out.println("Enemy image loaded");
                        }
 
                catch (IllegalArgumentException | FileNotFoundException e) {
                        System.err.println("Error when loading " + e.toString());
                        System.exit(1);
                }
 
                System.out.println("Resizing images...");
                background = toBufferedImage(background.getScaledInstance(800, 600, Image.SCALE_SMOOTH));
                maincharImg = toBufferedImage(maincharImg.getScaledInstance(-1, 100, Image.SCALE_SMOOTH));
                weaponImg = toBufferedImage(weaponImg.getScaledInstance(80, 40, Image.SCALE_SMOOTH));
                enemyImg = toBufferedImage(enemyImg.getScaledInstance(-1, 130, Image.SCALE_SMOOTH));
 
                System.out.println("Making flipped and immune images...");
                maincharImgleft = flipImage(maincharImg);
                weaponImgleft = flipImage(weaponImg);
                enemyImgleft = flipImage(enemyImg);
 
                System.out.println(maincharImg.getHeight());
                mainchar.width = maincharImg.getWidth();
                enemy.width = enemyImg.getWidth();
 
                maincharImmune = makeImmune(maincharImg);
                enemyImmune = makeImmune(enemyImg);
        }
 
        public static BufferedImage makeImmune(BufferedImage image) {
                int row = image.getWidth() - 1;
                int col = image.getHeight() - 1;
 
                for (int r = 0; r < row; r++) {
                        for (int c = 0; c < col; c++)
                                image.setRGB(row, c, 255);
                }
 
                return image;
        }
 
        //from http://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
        public static BufferedImage flipImage(BufferedImage image) {
                AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
        return createTransformed(image, at);
        }
 
        //from http://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
    private static BufferedImage createTransformed(
            BufferedImage image, AffineTransform at)
        {
            BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.transform(at);
            g.drawImage(image, 0, 0, null);
            g.dispose();
            return newImage;
        }
 
        //from http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
        public static BufferedImage toBufferedImage(Image img)
        {
            if (img instanceof BufferedImage)
            {
                return (BufferedImage) img;
            }
 
            // Create a buffered image with transparency
            BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
 
            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();
 
            // Return the buffered image
            return bimage;
        }
 
        public void drawGame(JFrame frame) {
                BufferStrategy bf = frame.getBufferStrategy();
                Graphics g = null;
 
                try {
                        g = bf.getDrawGraphics(); //retrieves Graphics from the BufferStrategy
                        g.drawImage(background, 0, 0, null); //draw the background
                        if (mainchar.immune)
                                g.drawImage(maincharImmune, mainchar.xco, mainchar.yco, null);
                        else if (keyhandler.face) {
                                g.drawImage(maincharImg, mainchar.xco, mainchar.yco, null);
                        g.drawImage(weaponImg, mainchar.weapon.xco, mainchar.weapon.yco, null);
                        }
                        else {
                                g.drawImage(maincharImgleft, mainchar.xco, mainchar.yco, null);
                                g.drawImage(weaponImgleft, mainchar.weapon.xco, mainchar.weapon.yco, null);
                        }
 
                        if (enemy.immune)
                                g.drawImage(enemyImmune, enemy.xco, enemy.yco, null);
                        else                   
                                g.drawImage(enemyImg, enemy.xco, enemy.yco, null);
                } finally {
                        g.dispose();
                }
 
                bf.show(); //make the worked-on buffer the current JFrame buffer
 
                Toolkit.getDefaultToolkit().sync();
        }
 
        public void gameLogic() {
                if (keyhandler.pressRight)
                        mainchar.rightPressed();
                if (keyhandler.pressLeft)
                        mainchar.leftPressed();
                if (keyhandler.pressJump)
                        mainchar.jumpPressed();
                if (keyhandler.pressJump == false)
                        mainchar.jumpReleased();
                if (keyhandler.pressSpace)
                        mainchar.spacePressed(keyhandler.face); //face = true is right, face = false is left
                if (keyhandler.pressSpace == false)
                        mainchar.spaceReleased();
                enemy.decideAction();
 
                //check if mainchar got hit by enemy
                if (mainchar.checkCollision(mainchar.xco, mainchar.yco, mainchar.width, 100,
                                enemy.xco, enemy.yco, enemy.width, 130)) {
                        if (mainchar.immune == false) {
                                mainchar.takeDamage(mainchar);
                                //mainchar.immune = true;
                        }
 
                }
 
                //check if enemy got hit by weapon
                if (enemy.checkCollision(mainchar.weapon.xco, mainchar.weapon.yco, 80, 40,
                                enemy.xco, enemy.yco, enemy.width, 130)) {
                        if (enemy.immune == false) {
                                enemy.takeDamage(enemy);
                                //enemy.immune = true;
                        }
                }
 
                mainchar.checkDeath();
                mainchar.immuneCounter(mainchar);
                enemy.checkDeath();
                enemy.immuneCounter(enemy);
 
        }
 
        public void run() {
                gameLogic();
        }
 
}

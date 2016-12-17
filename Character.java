package basicGame;
 
public class Character {
        int xco, yco, groundheight; //x and y coordinates on the screen
        int jumpCount = 0, fallCount = 0; //"times" the jump and "times" a fall
        int maxJumpHeight = 250, yDelay = 5; //xDelay and yDelay slow down movement
        boolean midJump; //midJump must be false for a character to jump again
        double yvel; //y velocity
        double gravity = 0.5;
        boolean onGround, immune; //if immune = true, the character cannot be hit for a little while
        int width, height;
        int health, immuneCounter;
        String name;
        boolean lose;
 
        //changes x-coordinates
        public double moveX (double xco, boolean right) {
                //move to the right or wrap around if at the right edge
                if (right) {
                        if (xco >= 1000)
                                xco = 0;
                        else
                                xco += 1;
                }
                //move to the left or wrap around if at the left edge
                else {
                        if (xco <= 0)
                                xco = 1000;
                        else
                                xco -= 1;
                }
                return xco;
        }
 
        public int increaseY (int yco) {
 
                //on initial jump
                if (midJump == false) {
                        midJump = true;
                        yvel = -4;
                        yco += yvel;
                        jumpCount = 0;
                }
 
                //if still moving upwards from jump
                if (midJump) {
                        if (jumpCount < maxJumpHeight) {
                                if (yvel > -4)
                                        yvel = gravity(yvel);
                                if (jumpCount % yDelay == 0) //adds a small delay
                                        yco += yvel;
                                jumpCount++;
                        }
                }
 
                return yco;
 
        }
 
        public int fall(int yco) {
                if (yco == groundheight) {
                        onGround = true;
                        midJump = false;
                        jumpCount = 0;
                        fallCount = 0;
                        yvel = 0;
                }
                else {
                        if (yvel < 1)
                                yvel = gravity(yvel);
                        if ( (yco += yvel) > groundheight)
                                yco = groundheight;
                        else
                                if (fallCount % yDelay == 0)
                                        yco += yvel;
                        fallCount++;
                }
 
                return yco;
        }
 
        public double gravity(double yvel) {
                if (fallCount % 2 == 0)
                        yvel += 1;
                return yvel;
        }
 
        //takes the coordinates of two objects and checks if they have collided
        public boolean checkIfHit(int xco1, int yco1, int xco2, int yco2, int height2, int width2) {
                return (xco1 >= xco2 && xco1 <= xco2 + width2) &&
                                (yco1 >= yco2 && yco1 <= yco2 + height2);
        }
 
        public boolean checkCollision(int xco1, int yco1, int height1, int width1, int xco2, int yco2, int height2, int width2) {
                return checkIfHit(xco1, xco2, xco2, yco2, height2, width2) ||
                                checkIfHit(xco1 + width1, yco1, xco2, yco2, height2, width2) ||
                                checkIfHit(xco1, yco1 + height1, xco2, yco2, height2, width2) ||
                                checkIfHit(xco1 + width1, yco1 + height1, xco2, yco2, height2, width2);
 
        }
 
        public void takeDamage(Character character) {
                if (immune != true) {
                        character.health = character.health - 10;
                        character.immune = true;
                        character.immuneCounter = 0;
                        System.out.println(character.name + " lost health! New value: " + character.health);
                        System.out.println(character.name + " is now immune");
                }
        }
 
        public void immuneCounter(Character character) {
                if (character.immune == true && character.immuneCounter < 50) {
                        character.immuneCounter++;
                }
                else {
                        if (character.immune) {
                                character.immune = false;
                                character.immuneCounter = 0;
                                System.out.println(character.name + " is no longer immune");
                        }
                }
        }
 
}

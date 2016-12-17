package basicGame;
 
public class Enemy extends Character {
 
        int actionTime, currentAction, num;
 
        public Enemy() {
                name = "The enemy";
                xco = 600;
                yco = 370;
                yvel = 0;
                height = 130;
                groundheight = 370;
                actionTime = 0;
                num = 10;
                health = 150;
                immune = false;
        }
 
        //possible actions are: move left or right, jump, or rush left or right
        public void decideAction() {
                if (actionTime % 50 == 0) { //recalculate num periodically
                        num = (int) (Math.random() * 20);
                }
 
                if (num >= 19)
                        currentAction = 1;
                else if (num >= 18)
                        currentAction = 2;
                else if (num >= 11)
                        currentAction = 3;
                else if (num >= 4)
                        currentAction = 4;
                else if (num >= 0)
                        currentAction = 5;
 
                doAction();
                fall(yco);
                actionTime++;
        }
 
        public void doAction() {
                if (currentAction == 1)
                        rushLeft();
                if (currentAction == 2)
                        rushRight();
                if (currentAction == 3)
                        moveX(xco, false); //move left
                if (currentAction == 4)
                        moveX(xco, true); //move right
                if (currentAction == 5)
                        increaseY(yco); //jump
        }
 
        public void rushLeft() {
                if (xco <= 0)
                        xco = 1000;
                else
                        xco -= 2;
        }
 
        public void rushRight() {
                if (xco >= 1000)
                        xco = 0;
                else
                        xco += 2;
        }
 
        public void checkDeath() {
                if (health <= 0) {
                        xco = -1000;
                        yco = -1000;
                        if (lose != false) {
                                System.out.println("You beat the monster! You win. You should play again.");
                        }
                        lose = true;
                }
        }
 
}
 

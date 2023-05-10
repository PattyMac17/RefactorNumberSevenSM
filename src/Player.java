import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    BufferedImage left1,left2,right1,right2;
    String direction;
    int spriteCounter = 0;
    int spriteNum = 1;
    Rectangle2D hitBox;
    Pair position;
    Pair velocity;
    Pair acceleration;
    int playerHeight;
    int playerWidth;
    Color testColor;
    boolean atMiddle = false;
    boolean atStart = false;
    boolean ableToJump = false;
    boolean leftCollision = false;
    boolean rightCollision = false;
    //boolean topCollision = false;
    boolean alive = true;
    boolean sixFeetUnder = false;
    boolean inControl = true;
    boolean walkOff;
    int walkOffCounter = 0;
    public Player(){
        getImage();
        direction = "right";
        //set player height and width
        this.playerHeight = 60;
        this.playerWidth = 60;

        //set initial position
        this.position = new Pair(50, Main.HEIGHT - 99 - this.playerHeight);
        this.velocity = new Pair(0, 0);
        this.acceleration = new Pair(0, 0);

        //create hitBox
        this.hitBox = new Rectangle2D.Double(position.x, position.y, playerWidth, playerHeight);

        //set color for test rectangle
        this.testColor = Color.CYAN;
    }
    public void getImage(){ //pulls images needed for the character
        //we did this by referring to a YouTube tutorial

        try{
            right1 = ImageIO.read(getClass().getResourceAsStream("bebu_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("bebu_right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("bebu_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("bebu_left2.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /*public void NPCLeftCheck(World w){
        if(this.hitBox.intersects(w.cow.leftWall) && w.cow.alive && alive){
            alive = false;
            setVelocity(new Pair(0, -470));
            setAcceleration(new Pair(0, 670));
        }
    }
    public void NPCRightCheck(World w){
        if(this.hitBox.intersects(w.cow.rightWall) && w.cow.alive && alive){
            alive = false;
            setVelocity(new Pair(0, -470));
            setAcceleration(new Pair(0, 670));
        }
    }
    public void NPCBottomCheck(World w){
        if(this.hitBox.intersects(w.cow.bottomWall) && w.cow.alive && alive){
            alive = false;
            setVelocity(new Pair(0, -470));
            setAcceleration(new Pair(0, 670));
        }
    }
    public void engageNPC(World w){
        if(w.cow.position.x - position.x < 1000 && w.cow.engaged == false){
            w.cow.setVelocity(new Pair(-100, 0));
            w.cow.engaged = true;
        }
    }*/
    public void deathCheck(World w){
        if(position.y > w.height){
            sixFeetUnder = true;
        }
    }
    public void endSequence(World w){
        if(!inControl && !w.reachedFlag){
            w.reachedFlag = true;
            setAcceleration(new Pair(0, 670));
        }
    }
    public void walk(World w){
        if(walkOff){
            if(walkOffCounter == 0){
                setPosition(new Pair(position.x, Main.HEIGHT - 99 - this.playerHeight));
                walkOffCounter++;
            }
            setVelocity(new Pair(w.playerVelocity, 0));
        }
    }
    public void gameOverSwitch(World w){
        if(walkOff){
            if(position.x > Main.WIDTH){
                w.winner = true;
            }
        }
    }
    /*public void flagDetection(World w){
        if(hitBox.intersects(w.flag.hitBox) && inControl){
            inControl = false;
            w.reachedFlag = true;
            int a = (int)(w.flag.position.x - this.position.x);
            position = new Pair(w.flag.position.x - a, position.y);
            velocity = new Pair(0, 0);
            acceleration = new Pair(0, 670);
        }

    }*/
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        hitBox.setRect(position.x,position.y, playerWidth,playerHeight);
        velocity = velocity.add(acceleration.times(time));
        spriteCounter++;
        if (hitBox.intersects(w.ground.groundLevel) && (velocity.x != 0 || w.ground.velocity.x != 0)){
            if (spriteCounter > 100) { //the image switches after this many frames
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0; //reset the counter
            }
        }

        midCheck();
        wallCheck();

        if(alive){
            jumpStop(w);
        }
        deathCheck(w);
        jumpStart(w);
        //flagDetection(w);
        /*pipeLeftCheck(w);
        pipeRightCheck(w);

        brickLeftCheck(w);
        brickRightCheck(w);
        if(alive)
        brickBottomCheck(w);
        if(alive)
        blockBottomCheck(w);
        blockLeftCheck(w);
        blockRightCheck(w);
        if(alive)
        questionCubeBottomCheck(w);
        questionCubeLeftCheck(w);
        questionCubeRightCheck(w);*/

        w.allNPCs.allNPCEngage(w);
        w.allNPCs.playerNPCCollision(w);
        w.allNPCs.allCheckNPC(w, w.allObstacles);
        w.allObstacles.collisionCheck(w);

        if(inControl)
        w.special.specialDSRightCheck(w);

        endSequence(w);
        w.special.ending(w);
        walk(w);
        gameOverSwitch(w);


        //System.out.println(w.reachedFlag);


        //NPCLeftCheck(w);
        //NPCRightCheck(w);
        //NPCBottomCheck(w);
    }
    /*public void brickBottomCheck(World w){
        Rectangle2D brick = w.brick.bottomWall;
        if(this.hitBox.intersects(brick)){
            velocity = new Pair(velocity.x, -velocity.y);
        }
    }
    public void blockBottomCheck(World w){
        Rectangle2D block = w.block.bottomWall;
        if(this.hitBox.intersects(block)){
            velocity = new Pair(velocity.x, -velocity.y);
        }
    }
    public void questionCubeBottomCheck(World w){
        Rectangle2D questionCube = w.questionCube.bottomWall;
        if(this.hitBox.intersects(questionCube)){
            velocity = new Pair(velocity.x, -velocity.y);
        }
    }
    public void questionCubeLeftCheck(World w){
        Rectangle2D questionCube = w.questionCube.leftWall;
        if(this.hitBox.intersects(questionCube)){
            leftCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            leftCollision = false;
        }
    }
    public void questionCubeRightCheck(World w){
        Rectangle2D questionCube = w.questionCube.rightWall;
        if(this.hitBox.intersects(questionCube)){
            rightCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            rightCollision = false;
        }
    }
    public void blockLeftCheck(World w){
        Rectangle2D block = w.block.leftWall;
        if(this.hitBox.intersects(block)){
            leftCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            leftCollision = false;
        }
    }
    public void blockRightCheck(World w){
        Rectangle2D block = w.block.rightWall;
        if(this.hitBox.intersects(block)){
            rightCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            rightCollision = false;
        }
    }
    public void brickLeftCheck(World w){
        Rectangle2D brick = w.brick.leftWall;
        if(this.hitBox.intersects(brick)){
            leftCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            leftCollision = false;
        }
    }
    public void brickRightCheck(World w){
        Rectangle2D brick = w.brick.rightWall;
        if(this.hitBox.intersects(brick)){
            rightCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            rightCollision = false;
        }
    }*/
    public void midCheck(){
        Rectangle2D midline = new Rectangle2D.Double(Main.WIDTH/2 + playerWidth, 0, Main.WIDTH/2 -
                playerWidth, Main.HEIGHT);
        if(this.hitBox.intersects(midline)){
            atMiddle = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            atMiddle = false;
        }
    }
    /*public void pipeLeftCheck(World w){
        Rectangle2D pipe = w.pipe.leftWall;
        if(this.hitBox.intersects(pipe)){
            leftCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            leftCollision = false;
        }
    }
    public void pipeRightCheck(World w){
        Rectangle2D pipe = w.pipe.rightWall;
        if(this.hitBox.intersects(pipe)){
            rightCollision = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            rightCollision = false;
        }
    }*/
    public void wallCheck(){
        Rectangle2D start = new Rectangle2D.Double(-10, 0, 10, Main.HEIGHT);
        if(this.hitBox.intersects(start)){
            atStart = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            atStart = false;
        }
    }
    public void draw(Graphics g){
        BufferedImage image = null;
        switch (direction) {//connected to keyPressed in Main,
            //enables character's running animation to play when he's moving
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
        }
        g.drawImage(image,(int)position.x,(int)position.y + 15, playerWidth, playerHeight,null);

    }

    public void setPosition(Pair p){
        position = p;
    }
    public void setVelocity(Pair v){
        velocity = v;
    }

    public void setAcceleration(Pair acceleration) {
        this.acceleration = acceleration;
    }
    public void jumpStop(World w){
        if(hitBox.intersects(w.ground.groundLevel) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);
        }
        /*if(hitBox.intersects(w.pipe.topWall) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);
        }
        if(hitBox.intersects(w.brick.topWall) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);
        }
        if(hitBox.intersects(w.block.topWall) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);
        }
        if(hitBox.intersects(w.questionCube.topWall) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);
        }*/
    }
    public void jumpStart(World w){
        if(hitBox.intersects(w.ground.groundLevel) /*|| hitBox.intersects(w.pipe.topWall) ||
                hitBox.intersects(w.brick.topWall) || hitBox.intersects(w.block.topWall)
                || hitBox.intersects(w.questionCube.topWall)*/ || w.allObstacles.jumpStartCheck(w)){
            ableToJump = true;
        }
        else{
            ableToJump = false;
        }
    }
}

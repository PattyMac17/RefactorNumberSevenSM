import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC extends Obstacle implements Drawable{
    BufferedImage left1,left2,right1,right2;
    String direc;
    int spriteCounter = 0;
    int spriteNum = 1;
    int bounceCount;
    Color color;
    boolean engaged = false;
    boolean leftCollision;
    boolean rightCollision;
    boolean alive = true;
    int imageSize;
    int sideHeight;

    public NPC(int x, int y, World w) {
        super(x, y);
        sideLength = 50;
        sideHeight = 35;
        imageSize = 50;
        topWall = new Rectangle2D.Double(position.x, position.y,
                sideLength, wallWidth);
        leftWall = new Rectangle2D.Double(position.x - wallWidth, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        rightWall = new Rectangle2D.Double(position.x + sideLength, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        bottomWall = new Rectangle2D.Double(position.x, position.y + sideHeight - wallWidth,
                sideLength, wallWidth);
        getImage();
        direc = "left";
        color = Color.YELLOW;
        acceleration = new Pair(acceleration.x, 700);

    }
    public void updateDirection(){
        if(bounceCount%2 == 0){
            direc = "left";
        }
        else{
            direc = "right";
        }
    }

    @Override
    public void draw(Graphics g) {
        if(alive){
            BufferedImage image = null;
            switch (direc) {//connected to keyPressed in Main,
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
            g.drawImage(image,(int)position.x,(int)position.y - 5, imageSize, imageSize,null);
            g.setColor(Color.white);
            g.drawRect((int)leftWall.getMinX(), (int)leftWall.getMinY(),
                    (int)leftWall.getWidth(), (int)leftWall.getHeight());
            g.drawRect((int)rightWall.getMinX(), (int)rightWall.getMinY(),
                    (int)rightWall.getWidth(), (int)rightWall.getHeight());
            g.drawRect((int)topWall.getMinX(), (int)topWall.getMinY(),
                    (int)topWall.getWidth(), (int)topWall.getHeight());
            g.drawRect((int)bottomWall.getMinX(), (int)bottomWall.getMinY(),
                    (int)bottomWall.getWidth(), (int)bottomWall.getHeight());
        }
        /*g.setColor(color);
        if(alive)
        g.fillRect((int)this.position.x, (int)this.position.y, sideLength, sideLength);
        g.setColor(Color.white);
        g.drawRect((int)leftWall.getMinX(), (int)leftWall.getMinY(),
                (int)leftWall.getWidth(), (int)leftWall.getHeight());
        g.drawRect((int)rightWall.getMinX(), (int)rightWall.getMinY(),
                (int)rightWall.getWidth(), (int)rightWall.getHeight());
        g.drawRect((int)topWall.getMinX(), (int)topWall.getMinY(),
                (int)topWall.getWidth(), (int)topWall.getHeight());
        g.drawRect((int)bottomWall.getMinX(), (int)bottomWall.getMinY(),
                (int)bottomWall.getWidth(), (int)bottomWall.getHeight());*/
    }

    @Override
    public void update(World w, double time) {
        position = position.add(velocity.times(time));
        leftWall.setRect(position.x - wallWidth, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        rightWall.setRect(position.x + sideLength, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        topWall.setRect(position.x, position.y,
                sideLength, wallWidth);
        bottomWall.setRect(position.x, position.y + sideHeight - wallWidth,
                sideLength, wallWidth);
        velocity = velocity.add(acceleration.times(time));

        jumpStop(w);
        scrollAdjust(w);
        updateDirection();
        //pipeLeftCheck(w);
        //pipeRightCheck(w);

        //blkLeftCheck(w);
        //blkRightCheck(w);
        playerCheck(w);
        //System.out.println(bounceCount%2);
        spriteCounter++;
        if (velocity.x != 0 || w.ground.velocity.x != 0){
            if (spriteCounter > 100) { //the image switches after this many frames
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0; //reset the counter
            }
        }

    }
    public void playerCheck(World w){
        if(topWall.intersects(w.player.hitBox) && w.player.alive && alive){
            alive = false;
            w.score += 100;
        }
    }

    @Override
    public void setVelocity(Pair a) {
        super.setVelocity(a);
    }
    public void jumpStop(World w){
        if(bottomWall.intersects(w.ground.groundLevel)){
            velocity = new Pair(velocity.x,0);
        }
    }
    public void scrollAdjust(World w){
        if(engaged){
            if(w.isScrolling && w.ground.velocity.x != 0){
                if(bounceCount%2 ==0){
                    velocity = new Pair(-300, velocity.y);

                }
                else{
                    velocity = new Pair(-100, velocity.y);
                    //System.out.println("b");

                }
            }
            else{
                if(bounceCount%2 ==0){
                    velocity = new Pair(-100, velocity.y);
                    //System.out.println("c");
                }
                else{
                    velocity = new Pair(100, velocity.y);
                    //System.out.println("d");
                }
            }
        }
    }
    /*public <E extends Obstacle> void Collision(DataStructure<E> DS){
        if(leftCollision(DS) || rightCollision(DS)){
            this.velocity.flipX();
        }
    }
    public <E extends Obstacle> boolean leftCollision(DataStructure<E> DS){
        Node<E> node = DS.end;
        E elem = node.element;
        if(this.leftWall.intersects(elem.rightWall)){
            return true;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(this.leftWall.intersects(elem.rightWall)){
                return true;
            }
        }
        return false;
    }
    public <E extends Obstacle> boolean rightCollision(DataStructure<E> DS){
        Node<E> node = DS.end;
        E elem = node.element;
        if(this.rightWall.intersects(elem.leftWall)){
            return true;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(this.rightWall.intersects(elem.leftWall)){
                return true;
            }
        }
        return false;
    }
    public void pipeLeftCheck(World w){
        Rectangle2D pipe = w.pipe.rightWall;
        if(this.leftWall.intersects(pipe)){
            leftCollision = true;
            bounceCount++;
            if(w.isScrolling){
                velocity = new Pair(-100,0);
            }
            else{
                velocity = new Pair (100, 0);
            }
        }
        else{
            leftCollision = false;
        }
    }
    public void pipeRightCheck(World w){
        Rectangle2D pipe = w.pipe.leftWall;
        if(this.rightWall.intersects(pipe)){
            rightCollision = true;
            bounceCount++;
            if(w.isScrolling){
                velocity = new Pair(-300,0);
            }
            else{
                velocity = new Pair (-100, 0);
            }
        }
        else{
            rightCollision = false;
        }
    }
    public void blkLeftCheck(World w){
        Rectangle2D blk = w.blk.rightWall;
        if(this.leftWall.intersects(blk)){
            leftCollision = true;
            bounceCount++;
            if(w.isScrolling){
                velocity = new Pair(-100,0);
            }
            else{
                velocity = new Pair (100, 0);
            }
        }
        else{
            leftCollision = false;
        }
    }
    public void blkRightCheck(World w){
        Rectangle2D blk = w.blk.leftWall;
        if(this.rightWall.intersects(blk)){
            rightCollision = true;
            bounceCount++;
            if(w.isScrolling){
                velocity = new Pair(-300,0);
            }
            else{
                velocity = new Pair (-100, 0);
            }
        }
        else{
            rightCollision = false;
        }
    }*/
    public void getImage(){ //pulls images needed for the character
        //we did this by referring to a YouTube tutorial

        try{
            right1 = ImageIO.read(getClass().getResourceAsStream("eph_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("eph_right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("eph_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("eph_left2.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

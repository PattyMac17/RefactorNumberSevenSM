import java.awt.*;

public class NPC extends Obstacle implements Drawable{
    int bounceCount;
    Color color;
    boolean engaged = false;
    boolean leftCollision;
    boolean rightCollision;
    boolean alive = true;

    public NPC(int x, int y, World w) {
        super(x, y);
        color = Color.YELLOW;

        acceleration = new Pair(acceleration.x, 700);

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
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
                (int)bottomWall.getWidth(), (int)bottomWall.getHeight());
    }

    @Override
    public void update(World w, double time) {
        super.update(w, time);
        jumpStop(w);
        scrollAdjust(w);

        //pipeLeftCheck(w);
        //pipeRightCheck(w);

        //blkLeftCheck(w);
        //blkRightCheck(w);
        playerCheck(w);

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
                    //System.out.println("a");
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
}

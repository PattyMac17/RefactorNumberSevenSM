import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class Pipe extends Obstacle implements Drawable{

    int wallWidth = 1;
    int wallHeight;
    int height;
    int width;


    public Pipe(int x, int y, int width, int height){
        super(x, y);
        this.height = height;
        this.width = width;
        wallHeight = height - wallWidth;
        position = new Pair(x, y);
        leftWall = new Rectangle2D.Double(position.x, position.y + wallWidth, wallWidth, wallHeight);
        rightWall = new Rectangle2D.Double(position.x + width - wallWidth,position.y + wallWidth, wallWidth, wallHeight);
        topWall = new Rectangle2D.Double(position.x, position.y, width, wallWidth);
        this.bottomWall = new Rectangle2D.Double(position.x + wallWidth, position.y + height - wallWidth,
                width - (2 * wallWidth), wallWidth);
        this.velocity = new Pair(0, 0);
        this.acceleration = new Pair(0, 0);
    }
    @Override
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        leftWall.setRect(position.x, position.y + wallWidth, wallWidth, wallHeight);
        rightWall.setRect(position.x + width - wallWidth,position.y + wallWidth, wallWidth, wallHeight);
        topWall.setRect(position.x, position.y, width, wallWidth);
        bottomWall.setRect(position.x + wallWidth, position.y + height - wallWidth,
                width - (2 * wallWidth), wallWidth);
        velocity = velocity.add(acceleration.times(time));
    }
    public void draw(Graphics g){
        BufferedImage pipe = null;
        try {
            pipe = ImageIO.read(getClass().getResourceAsStream("pipe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(pipe, (int)position.x, (int)position.y,width,height, null);
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
}

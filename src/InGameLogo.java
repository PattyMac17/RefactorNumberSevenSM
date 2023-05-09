import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InGameLogo extends Obstacle implements Drawable{
    int sideWidth;
    public InGameLogo(int x, int y){
        super(x, y);
        //position = new Pair(100,100);
        sideLength = 600;
        sideWidth = 343;

    }
    @Override
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        leftWall.setRect(0,0,0,0);
        rightWall.setRect(0,0,0,0);
        topWall.setRect(0,0,0,0);
        bottomWall.setRect(0,0,0,0);
        velocity = velocity.add(acceleration.times(time));
    }
    @Override
    public void draw(Graphics g) {
        BufferedImage inGameLogo = null;
        try {
            inGameLogo = ImageIO.read(getClass().getResourceAsStream("inGameLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(inGameLogo, (int)position.x, (int)position.y,sideLength,sideWidth, null);
        g.setColor(Color.white);
        g.drawRect((int)leftWall.getMinX(), (int)leftWall.getMinY(),
                (int)leftWall.getWidth(), (int)leftWall.getHeight());
        g.drawRect((int)rightWall.getMinX(), (int)rightWall.getMinY(),
                (int)rightWall.getWidth(), (int)rightWall.getHeight());
        g.setColor(Color.black);
        g.drawRect((int)topWall.getMinX(), (int)topWall.getMinY(),
                (int)topWall.getWidth(), (int)topWall.getHeight());
        g.drawRect((int)bottomWall.getMinX(), (int)bottomWall.getMinY(),
                (int)bottomWall.getWidth(), (int)bottomWall.getHeight());
    }
}

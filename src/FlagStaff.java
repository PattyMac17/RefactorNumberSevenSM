import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FlagStaff extends Obstacle implements Drawable{
    int flagHeight;
    public FlagStaff(int x, int y, int height){
        super(x, y);
        flagHeight = height;
        sideLength += 30;
        leftWall.setRect(position.x + (3 * sideLength / 4)- wallWidth, position.y, wallWidth, flagHeight);
        rightWall.setRect(position.x + sideLength - wallWidth, position.y, wallWidth, flagHeight);
        topWall.setRect(position.x + (3 * sideLength / 4), position.y, sideLength/4, wallWidth);
        topWall.setRect(position.x + (3 * sideLength / 4), position.y + flagHeight, sideLength/4, wallWidth);
    }
    @Override
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        leftWall.setRect(position.x + (3 * sideLength / 4)- wallWidth, position.y, wallWidth, flagHeight);
        rightWall.setRect(position.x + sideLength - wallWidth, position.y, wallWidth, flagHeight);
        topWall.setRect(position.x + (3 * sideLength / 4), position.y, sideLength/4, wallWidth);
        bottomWall.setRect(position.x + (3 * sideLength / 4), position.y + flagHeight, sideLength/4, wallWidth);
        velocity = velocity.add(acceleration.times(time));
    }
    @Override
    public void draw(Graphics g){
        BufferedImage flagStaff = null;
        try {
            flagStaff = ImageIO.read(getClass().getResourceAsStream("flagStaff.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(flagStaff, (int)position.x, Main.HEIGHT - 100 - flagHeight, sideLength, flagHeight, null);
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

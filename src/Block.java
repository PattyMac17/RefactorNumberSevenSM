import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Block extends Obstacle implements Drawable{

    public Block(int x, int y){
        super(x, y);
    }
    @Override
    public void draw(Graphics g){
        BufferedImage block = null;
        try {
            block = ImageIO.read(getClass().getResourceAsStream("block.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(block, (int)position.x, (int)position.y,sideLength,sideLength, null);
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
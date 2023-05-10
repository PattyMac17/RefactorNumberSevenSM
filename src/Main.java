import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class Main extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 1000;
    //Rectangle2D midline;

    World world;



    class Runner implements Runnable{
        public void run() {
            while(true){
                if(!world.startSequence){
                    world.update(1.0 / (double)FPS);
                }
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e){}
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            world.startSequence = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_P){
            System.out.println(world.score);
        }
        if(e.getKeyCode() == KeyEvent.VK_V && world.getHelp == false){
            world.viewHighScore = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_B){
            world.viewHighScore = false;
            world.getHelp = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_N && world.viewHighScore == false){
            world.getHelp = true;
        }




        if(world.player.alive && world.player.inControl){
            if(e.getKeyCode() == KeyEvent.VK_W && world.player.ableToJump){
                world.player.setAcceleration(new Pair(world.player.acceleration.x, 670));
                world.player.setVelocity(new Pair(world.player.velocity.x, -470));
            }
            if(e.getKeyCode() == KeyEvent.VK_A && !world.player.atStart && !world.player.leftCollision){
                Pair a = new Pair(-world.playerVelocity, world.player.velocity.y);
                world.player.setVelocity(a);
            }
            if (e.getKeyCode() == KeyEvent.VK_D && !world.player.rightCollision){
                //System.out.println("here");
                Pair a = new Pair(world.playerVelocity, world.player.velocity.y);
                if (!world.player.atMiddle){
                    world.player.setVelocity(a);
                }
                else{
                    //System.out.println("now here");
                    a = new Pair(-world.playerVelocity, 0);
                    world.ground.setVelocity(a);
                    //world.flag.setVelocity(a);
                    //world.pipe.setVelocity(a);
                    //world.block.setVelocity(a);
                    //world.brick.setVelocity(a);
                    //world.blk.setVelocity(a);
                    //world.questionCube.setVelocity(a);
                    //world.coin.setVelocity(a);

                    world.allObstacles.setVelocity(a);
                    world.allCoins.setVelocity(a);
                    world.special.setVelocity(a);
                    //world.cow.velocity = (new Pair(-300, 0));
                    //there's a bug here
                    //if(world.ground.velocity.x != 0){}
                    //world.startLogo.setVelocity(a);
                    //world.everything.setVelocity(a);
                    //world.purpleCows.adjustXVelocity();
                    world.isScrolling = true;
                    //world.purpleCows.fullStop();
                }
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        if( (e.getKeyCode() == KeyEvent.VK_A) || (e.getKeyCode() == KeyEvent.VK_D)){
            Pair a = new Pair(0, world.player.velocity.y);
            world.player.setVelocity(a);
            world.ground.setVelocity(new Pair(0, 0));
            //world.flag.setVelocity(new Pair(0, 0));
            //world.pipe.setVelocity(new Pair(0, 0));
            //world.block.setVelocity(new Pair(0, 0));
            //world.brick.setVelocity(new Pair(0, 0));
            //world.questionCube.setVelocity(new Pair(0, 0));
            //world.blk.setVelocity(new Pair(0, 0));
            //world.coin.setVelocity(new Pair(0, 0));
            //world.startLogo.setVelocity(new Pair (0, 0));
            //world.everything.setVelocity(new Pair (0, 0));
            world.isScrolling = false;
            world.allObstacles.setVelocity(new Pair(0, 0));
            world.allCoins.setVelocity(new Pair(0, 0));
            world.special.setVelocity(new Pair(0, 0));
        }
    }


    public void keyTyped(KeyEvent e) {

    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public Main(){
        world = new World(WIDTH, HEIGHT);
        /*midline = new Rectangle2D.Double(WIDTH/2 + world.player.playerWidth, 0, WIDTH/2 -
                world.player.playerWidth, HEIGHT);*/
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Super Mammoth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(world.startSequence){
            if(world.viewHighScore){
                BufferedImage menu = null;
                BufferedImage test = null;
                try {
                    menu = ImageIO.read(getClass().getResourceAsStream("menu.png"));
                    test = ImageIO.read(getClass().getResourceAsStream("helpButton.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(menu, 0,0,Main.WIDTH,Main.HEIGHT, null);
                g.drawImage(test, 100,100, null);
            }else{
                if(world.getHelp){
                    BufferedImage menu = null;
                    BufferedImage test2 = null;
                    try {
                        menu = ImageIO.read(getClass().getResourceAsStream("menu.png"));
                        test2 = ImageIO.read(getClass().getResourceAsStream("quitButton.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.drawImage(menu, 0,0,Main.WIDTH,Main.HEIGHT, null);
                    g.drawImage(test2, 100,100, null);
                }
                else{
                    BufferedImage menu = null;
                    BufferedImage title = null;
                    try {
                        menu = ImageIO.read(getClass().getResourceAsStream("menu.png"));
                        title = ImageIO.read(getClass().getResourceAsStream("title.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.drawImage(menu, 0,0,Main.WIDTH,Main.HEIGHT, null);
                    g.drawImage(title, 100,100, null);
                }
            }
        }else{
            if(!world.player.sixFeetUnder && !world.winner){
                BufferedImage background = null;
                try {
                    background = ImageIO.read(getClass().getResourceAsStream("background.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(background, (int)world.ground.position.x,0,10000,885, null);

                world.draw(g);
            }
            else{
                g.setColor(Color.BLACK);
                Rectangle rect = new Rectangle(0, 0, WIDTH, HEIGHT);
                g.fillRect(0, 0, WIDTH, HEIGHT);
                Font f = new Font("TimesRoman", Font.PLAIN, 100);
                g.setColor(Color.white);
                if(world.winner){
                    String score = "Final Score: "+world.score;
                    drawCenteredString(g, score,rect, f);
                }
                if(world.player.sixFeetUnder){
                    String fail = "You Died";
                    drawCenteredString(g,fail, rect, f);
                }
            }
        }
    }
    //this method was sourced from StackExchange
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }
}
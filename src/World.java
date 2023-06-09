import java.awt.*;

//this is the world that contains all game elements
public class World {
    int score = 0;
    boolean isScrolling = false;
    boolean startSequence = true;
    boolean viewHighScore = false;
    boolean getHelp = false;
    //boolean reachedFlag;
    int width;
    int height;
    //player is our main character
    Player player;
    int playerVelocity;
    //ground is the actual level itself
    Ground ground;
    boolean reachedFlag = false;
    boolean winner = false;
    //FlagPole flag;

    //Pipe pipe;
    //Brick brick;
    //Block block;
    //QuestionCube questionCube;
    //NPC cow;
    //Block blk;
    //Coin coin;
    DataStructure<Obstacle> allObstacles;
    DataStructure<NPC> allNPCs;
    DataStructure<Coin> allCoins;
    DataStructure<Obstacle> special;

    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.player = new Player();
        this.ground = new Ground();
        this.playerVelocity = 200;
        //this.pipe = new Pipe(width/4, Main.HEIGHT - 100 - 100, 70, 100);
        //this.brick = new Brick(700, 500);
        //this.block = new Block(600, 500);
        //this.questionCube = new QuestionCube(500, 500);
        //this.cow = new NPC(700, 500, this);
        //this.blk = new Block(750, 628);
        //this.coin = new Coin(500, 460);
        this.allObstacles = new DataStructure<Obstacle>();
        this.allNPCs = new DataStructure<NPC>();
        this.allCoins = new DataStructure<Coin>();
        this.special = new DataStructure<Obstacle>();

        allObstacles.append(new InGameLogo(50, 100));
        allObstacles.append(new Pipe(width/4 + 500, Main.HEIGHT - 100 - 100, 70, 100));
        allObstacles.append(new Brick(1250, 500));
        allObstacles.append(new Block(1100, 500));
        allObstacles.append(new Brick(950, 500));
        allObstacles.append(new Block(1350, 628));
        allObstacles.append(new StairBlock(1400, 588));

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(1600 + (i * 40), 628 - (j * 40)));
            }
        }
        for (int i = 4; i > 0; i--){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(1800 + (i * 40), 628 - (j * 40)));
            }
        }

        allObstacles.append(new Pipe(2250, Main.HEIGHT - 100 - 140,70, 140));
        allCoins.append(new Coin(2250, 300));

        allNPCs.append(new NPC(2100, 300, this));
        allNPCs.append(new NPC(2270, 300, this));

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(2400 + (i * 40), 628 - (j * 40)));
            }
        }
        for (int i = 0; i < 8; i++){
            allObstacles.append(new StairBlock(2400 + (9 * 40), 628 - (i * 40)));
        }

        special.append(new FlagStaff(3100, Main.HEIGHT - 100 - 320, 320));
        //flag = new FlagPole(1600, 320);

        allCoins.append(new Coin(1100, 460));
        allNPCs.append(new NPC(1200, 500, this));
    }
    public void draw(Graphics g) {
        //draws all game elements
        player.draw(g);
        ground.draw(g);
        //flag.draw(g);
        //pipe.draw(g);
        //brick.draw(g);
        //block.draw(g);
        //questionCube.draw(g);
        //cow.draw(g);
        //blk.draw(g);
        //coin.draw(g);

        allObstacles.drawAll(g);
        allCoins.drawAll(g);
        allNPCs.drawAll(g);
        special.drawAll(g);
    }

    public void update(double time) {
        //updates all game elements
        player.update(this, time);
        ground.update(this, time);
        //flag.update(this, time);
        //pipe.update(this, time);
        //brick.update(this, time);
        //block.update(this, time);
        //questionCube.update(this, time);
        //cow.update(this, time);
        //blk.update(this, time);
        //coin.update(this, time);

        allObstacles.updateAll(this, time);
        allCoins.updateAll(this, time);
        allNPCs.updateAll(this, time);
        special.updateAll(this, time);
    }
}

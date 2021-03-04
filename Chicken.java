import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Chicken extends Rectangle{
    
    private int spd = 6;
    public boolean isPressedRight = false;
    public boolean isPressedLeft = false;
    private BufferedImage texture;
    private BufferedImage chickenTexture;

    public boolean exit = false;
    public int level_score = 250;
    public static int score = 0;
    
    
    public static final int HEIGHT_CHICKEN = 64;
    public static final int WIDTH_CHICKEN = 64;
    
    public LinkedList<Rectangle> cat;
    public LinkedList<Rectangle> target;
    
    
    public void newLevel(double difficulty, double difficultyTarget)
    {
        //Restart the game
        GameManager.catObject = new Cats(difficulty);
        GameManager.t = new Target(difficultyTarget);
        cat = GameManager.catObject.cat;
        target = GameManager.t.target;
        x = GameManager.WIDTH/2;
       
    }
    
    
    
    public Chicken(int x, int y, LinkedList<Rectangle> cat, LinkedList<Rectangle> target)
    {
        setBounds(x,y,WIDTH_CHICKEN,HEIGHT_CHICKEN);
        this.cat = cat;
        this.target = target;
        
        try{
            File file = new File("res/pixelchicken1.png");
            chickenTexture = ImageIO.read(file);
            
        }
        catch(IOException e){}
        
    }
    
    public void update()
    {
        
        if(isPressedRight)
        {
            x += spd;
            if(x>=GameManager.WIDTH - 70)
                GameManager.shouldChickenStop = true;
              
            if(GameManager.shouldChickenStop)
                spd = 0;
            else
                spd = 10;
          
        }
        else if(isPressedLeft)
        {
            x -= spd;
            
            if(x<= 0)
                GameManager.shouldChickenStop = true;
            
            if(GameManager.shouldChickenStop)
                spd = 0;
            
            else 
                spd = 10;
            
        }
        
        //collisions
        
        for(int i= 0;i<cat.size();i++)
        {
            if(this.intersects(cat.get(i)))
            {   
                if(GameManager.level == 1)
                {
                    newLevel((int)(84/GameManager.level), (int)(96/GameManager.level));
                }
                
                
                else if(GameManager.level == 4)
                {
                    newLevel((int)(84/(GameManager.level/3)), (int)(96/(GameManager.level/3.5)));
                }
                
                else
                {
                    newLevel((int)(84/(GameManager.level/2)), (int)(96/(GameManager.level/3)));  
                }
                
                score = 0;
            }    
        }
        
        
        if(score >= level_score)
        {
            GameManager.level++;
            level_score += 75;
            score = 0;
            
            if(GameManager.level == 4)
            {
                newLevel((int)(84/(GameManager.level/3)), (int)(96/(GameManager.level/3.5)));
            }
            else
            {    
                newLevel((int)(84/(GameManager.level/2)), (int)(96/(GameManager.level/3)));
            }
        }
        
        if(GameManager.level == 5)
        {
            JOptionPane.showMessageDialog(null,"Congratulations!");
            exit = true;
            
        }
       
        
    }
   
    public void render(Graphics g)
    {
        g.drawImage(chickenTexture,x,y,width,height,null);
    }
    
}

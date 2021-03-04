import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Egg extends Rectangle {

private int spd = 10;
private LinkedList<Rectangle> target;
private BufferedImage eggTexture;



private final int HEIGHT_EGG = 32;
private final int WIDTH_EGG = 32;



public Egg(int x1,int y1,LinkedList<Rectangle> targets)
{
    
   setBounds(x1,y1,WIDTH_EGG,HEIGHT_EGG);
   this.target = new LinkedList<Rectangle>();
   this.target = targets;
   
   try{
            File file = new File("res/egg.png");
            eggTexture = ImageIO.read(file);
            
        }
        catch(IOException e){}

}




public void update()
{   
    
    y += spd;
        
       
    
    //INTERSECTION
   
    for(int i= 0;i<target.size();i++)
        {
            if(this.intersects(target.get(i)))
            {
                Chicken.score += (int) target.get(i).width/3;
                target.remove(target.get(i));    
                GameManager.shoulEggBeDeleted = true;
            }    
        }
    
    
    
    
}

public void render(Graphics g)
{
  
        g.drawImage(eggTexture,x,y,WIDTH_EGG,HEIGHT_EGG,null);
           
        
}
    
}

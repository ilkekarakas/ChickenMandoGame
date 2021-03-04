
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;


public class Target extends Rectangle{
    
    public LinkedList<Rectangle> target;
    private double time;
    private double currentTime = 0;
    private int spd = 4;
    private Random random;
    private BufferedImage targetTexture;
    
    private int x = 0;
    private int width = 75;
    private int height = (int)(width / 1.64);
    public Target(double time)
    {
        random = new Random();
        width = random.nextInt(75)+35;
        height = (int)(width / 1.64);
        setBounds(x,y,width,height);
        
         try{
            File file = new File("res/reward.png");
            targetTexture = ImageIO.read(file);
            
        }
        catch(IOException e){}
        
        
        target = new LinkedList<>();
        this.time = time;
        
    }
    
    
     public void update()
    {
        currentTime++;
        if(currentTime == time)
        {
            currentTime = 0;
            target.add(new Rectangle(x,GameManager.HEIGHT,width,height));     
            width = random.nextInt(75)+35;
            height = (int)(width / 1.64);
            
            int control = random.nextInt(2);
            if(control== 1)
                x = GameManager.WIDTH - width;

            else
                x = 0;
        }
        
        for(int i=0; i<target.size(); i++)
        {
            Rectangle rect = target.get(i);
            rect.y -= spd;
            
            if(rect.y+rect.height<= 0)
            {
                target.remove(rect);
            }
            
        }
    
    }
     
     
    public void render(Graphics g)
    {
     
        for(int i=0; i<target.size(); i++)
        {
            Rectangle rect = target.get(i);
           // g.setColor(Color.red);
            g.drawImage(targetTexture,rect.x, rect.y, rect.width, rect.height,null);
        }
        
    } 
     
}

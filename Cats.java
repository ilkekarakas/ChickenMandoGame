import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;

public class Cats extends Rectangle {
    
    public LinkedList<Rectangle> cat;
    private double time;
    private double currentTime = 0;
    private int spd = 4;
    private BufferedImage catTexture;
    private Random random;
    
    private final int HEIGHT_CAT = 64;
    private final int WIDTH_CAT = 64;
    
    
    public Cats(double time)
    {
        setBounds(x,y,WIDTH_CAT,HEIGHT_CAT);
        
        try{
            File file = new File("res/cat1.png");
            catTexture = ImageIO.read(file);
            
        }
        catch(IOException e){}
        cat = new LinkedList<>();
        this.time = time;
        random = new Random();
    }
    
    public void update()
    {
        currentTime++;
        if(currentTime == time)
        {
            currentTime = 0;
            int x = random.nextInt(GameManager.WIDTH - WIDTH_CAT);
            cat.add(new Rectangle(x,GameManager.HEIGHT,WIDTH_CAT,HEIGHT_CAT));     

        }
        
        for(int i=0; i<cat.size(); i++)
        {
            Rectangle rect = cat.get(i);
            rect.y -= spd;
            
            if(rect.y+rect.height<= 0)
            { 
                cat.remove(rect);
                continue;
            }
            
        }
        
  
    }
    
    public void render(Graphics g)
    {
     
        for(int i=0; i<cat.size(); i++)
        {
            g.drawImage(catTexture,cat.get(i).x,cat.get(i).y,WIDTH_CAT,HEIGHT_CAT,null);
        }
        
    }
    
    
    
}

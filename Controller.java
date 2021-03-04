
import java.awt.Graphics;
import java.util.LinkedList;

public class Controller {
    
    public boolean isPressedFire = false;
    private LinkedList<Egg> e ;
    
    Egg tempEgg;
    Chicken chicken;
    
    public Controller(Chicken chick)
    {
        e = new LinkedList<Egg>();
        this.chicken = chick;

    }
    
  
    
    public void update()
    {
        
        if(isPressedFire)
        {
             addEgg(new Egg(chicken.x+15,chicken.y+chicken.height-10,chicken.target));
             isPressedFire = false;
        } 
        
        for(int i = 0;i<e.size();i++)
        {
            tempEgg = e.get(i);

            tempEgg.update();
            
            
            if(GameManager.shoulEggBeDeleted)
            {
                removeEgg(tempEgg);
                i++;
            }
            
            
            if(tempEgg.getY() >= GameManager.HEIGHT)
            {
                removeEgg(tempEgg);  
            }
            
            
        }
    }
    
    
    public void render(Graphics g)
    {
         for(int i = 0;i<e.size();i++)
        {
            tempEgg = e.get(i);
       
            tempEgg.render(g);
            
        }
    }
    
    public void addEgg(Egg block)
    {
        e.add(block);
    }
    
    public void removeEgg(Egg block)
    {
        e.remove(block);
    }
    
    
}

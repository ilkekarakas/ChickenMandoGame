import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class GameManager extends Canvas implements Runnable, KeyListener {

    public static final int WIDTH = 310, HEIGHT = 480;
    private boolean running = false;
    private Thread thread;
    public static Cats catObject;
    public Chicken chicken;
    private Controller c;
    public static boolean shoulEggBeDeleted = false;
    public static boolean shouldChickenStop = false;
    private Sound sound;
    public static double level = 1;
    public static Target t;
    
    private Image backGround;
    
    public GameManager()
    {
        Dimension d = new Dimension(GameManager.WIDTH,GameManager.HEIGHT);
        setPreferredSize(d);
        catObject = new Cats(84);
        t = new Target(96);
        chicken = new Chicken(WIDTH/2-(Chicken.WIDTH_CHICKEN/2),45,catObject.cat,t.target);
        c = new Controller(chicken);       
        sound = new Sound();
        
        
        try{
            File file = new File("res/farm.jpg");
            backGround = ImageIO.read(file);
            
        }
        catch(IOException e){}
        
        addKeyListener(this);
    }
    
    
    
    public synchronized void start()
    {
        if(running)
            return;
        else
        {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
       
    public synchronized void stop()
    {
        if(!running)
            return;
        else
        {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
   
    
    
    public static void main(String [] args)
    {
        JFrame frame = new JFrame("Chickenmando");
        GameManager gamemanager = new GameManager();
        
        frame.add(gamemanager);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null,"Left and Right Arrow Keys: Move\nSpace: Shoot Eggs","How to Play?",1);

        
        gamemanager.start();
        
    }

    @Override
    public void run() {
         int fps = 0;
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double ns = 1000000000 / 60;
        double delta = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            
            while(delta >= 1){
                update();
                render();
                fps++;
                delta--;
            }
        
            if (System.currentTimeMillis() - timer >= 100)
            {  
                fps = 0;
                timer += 1000;
            }
        }
        
    }
    
    private void render()
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs==null)
        {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(backGround,-340,-150,null);
        t.render(g);
        catObject.render(g); 
        chicken.render(g);
        c.render(g);
        
        
        g.setColor(Color.white);
        g.setFont(new Font(Font.DIALOG,Font.BOLD,19));
        g.drawString("Score: "+ chicken.score,2,20);
        g.drawString("Goal: "+ chicken.level_score,2,40);
        g.drawString("Level: "+(int)level,235,20);
        g.dispose();
        
        bs.show();
    }
    
    private void update()
    {
        if(!chicken.exit)
        {
            catObject.update();
            chicken.update();
            c.update();
            t.update();
            shoulEggBeDeleted = false;
            shouldChickenStop=false;
            
        }
      
        else
        {
            try {
                thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }

    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            chicken.isPressedRight = true;
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            chicken.isPressedLeft = true;
        
        else if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {       
                c.isPressedFire = true;
                
        }
        
        
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            chicken.isPressedRight = false;
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            chicken.isPressedLeft = false;
         else if(e.getKeyCode() == KeyEvent.VK_SPACE)
         {    
            c.isPressedFire = false;
         }
             
    }
    
}

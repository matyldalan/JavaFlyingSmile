/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flyingSmile;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 *
 * @author matyldalange
 */


public class FlyingSmile implements ActionListener, MouseListener, KeyListener  {
    
    public static FlyingSmile flyingSmile;
    
    public final int WIDTH=800, HEIGHT=800;
    
    public Renderer renderer;
    
    public Rectangle smile;
    
    public Panel panel;
    
    public int ticks, yMotion,score;
    
    public Random rand;
    
    public Image apple, smile1, food, food1;
    
    public ArrayList<Rectangle> fruits;
    
    public boolean gameOver=false,started=false;
    
    
    public FlyingSmile() {
        JFrame jframe= new JFrame();
        
        
        renderer = new Renderer();
        
        panel = new Panel();
 	    
        rand= new Random();
        
        Timer timer = new Timer(20,this);
        
        //ustawienia jframe
        jframe.add(renderer);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH,HEIGHT);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.setTitle("Flying smile");
        jframe.add(panel);
        
        //ladowanie obrazkow
        apple = new ImageIcon("/Users/matyldalange/NetBeansProjects/FlyingSmile/src/flyingSmile/jablko.png").getImage();
        smile1 = new ImageIcon("/Users/matyldalange/NetBeansProjects/FlyingSmile/src/flyingSmile/smile1.png").getImage();
        food = new ImageIcon("/Users/matyldalange/NetBeansProjects/FlyingSmile/src/flyingSmile/food.png").getImage();
        food1 = new ImageIcon("/Users/matyldalange/NetBeansProjects/FlyingSmile/src/flyingSmile/food1.png").getImage();
        
        
        //tworzenie rectangle jako usmiech, ktorym bedziemy grac
        smile = new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
        
        //tworzenie tablicy kolumn
        fruits = new ArrayList<Rectangle>();
       
        //dodawanie kolumn 
        addFruit(true);
        addFruit(true);
        addFruit(true);
        addFruit(true);
        
        
        timer.start();
    }
    
    //wiekszosc dzialan towarzyszacych rozgrywce zostalo wykonane w tej funkcji, takich jak zachowania usmiechu w stosunku do pozostalych elementow,
    //zbieranie punktow, sytaucje, ktore koncza rozgrywke
    @Override
    public void actionPerformed(ActionEvent e) {
        
        int speed=10;
        
        ticks++;
        
        if(started) {
        
        for ( int i=0; i<fruits.size();i++) {
            
        Rectangle fruit= fruits.get(i);
        fruit.x-=speed;
        
    }
        
        if (ticks%2==0 && yMotion < 15) {
            yMotion+=2;
            
        }
        
        for ( int i=0; i<fruits.size();i++) {
            
        Rectangle fruit= fruits.get(i);
        
        if(fruit.x+fruit.width<0) {
            
            fruits.remove(fruit);
            
            if(fruit.y==0) {
                
                addFruit(false);
                
            }
            
        }
        
    }
        
        smile.y+=yMotion;
        
        //dopisywanie punktu przy przejsciu usmiechu pomiedzy kolumnami
        
        for( Rectangle fruit : fruits) {
            
            if (fruit.y == 0 && smile.x + smile.width/2 > fruit.x+fruit.width/2-5 && smile.x+smile.width/2<fruit.x+fruit.width/2+5) {  //10-speed
                
                score++;
             
                }
            
       
        //zaprojektowanie zderzenia z kolumna
            
            if (fruit.intersects(smile)) {
                
                gameOver = true;  
                
                if (smile.x <= fruit.x) {
                     smile.x=fruit.x-smile.width;
                } else {
                    if(fruit.y!=0) {
                        smile.y=fruit.y-smile.height;
                    } else if (smile.y<fruit.height) {
                        smile.y=fruit.height;
                    }
                }
              
               
            }
        }
        
       
        if (smile.y>HEIGHT-120||smile.y<0) {
            
            gameOver=true;
            
        }
        
        if(smile.y + yMotion >= HEIGHT-120) {
            
            smile.y=HEIGHT-120-smile.height;
            
        }
    }
        
        renderer.repaint();
    }
    
    //dodawanie kolumn
    public void addFruit(boolean start) {
        int space=300; 
        int width=100;
        int height=50+rand.nextInt(300);
        
        if (start) {
        fruits.add(new Rectangle(WIDTH+width+fruits.size()*300, HEIGHT - height - 120,width,height));
        fruits.add(new Rectangle(WIDTH+width+(fruits.size()-1)*300, 0,width,HEIGHT - height - space));
        
        }
        else {
            fruits.add(new Rectangle(fruits.get(fruits.size()-1).x + 600, HEIGHT - height - 120,width,height));
            fruits.add(new Rectangle(fruits.get(fruits.size()-1).x, 0,width,HEIGHT - height - space));
        }
    }


    //wyswietlanie kolumn wraz z jablkiem pomiedzy nimi
    public void paintFruit(Graphics g, Rectangle fruit) {
        g.setColor(Color.BLACK);
        g.fillRect(fruit.x,fruit.y,fruit.width,fruit.height);
        
        g.setColor(Color.white);
        g.fillRect(fruit.x+20, fruit.y-100, 60, 60);
        g.drawImage(apple,fruit.x+20,fruit.y-100,60,60,null);
    }
    
    //skok
    public void jump() {
        
        if(gameOver) {
            
            smile = new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
            fruits.clear();
            
            yMotion=0;
            score=0;
        
            addFruit(true);
            addFruit(true);
            addFruit(true);
            addFruit(true);
            gameOver=false;
        }
        
        if(!started) {
            
            started=true;
            
        } else if (!gameOver) {
            
            if (yMotion>0) {
                
                yMotion=0;
                
            }
            
            yMotion-=10;
            
        }
 
    }
   
    
    //rysowanie tla gornego i dolnego, tla ogolnego i ustawianie usmiechu, rysowanie kolumn, ustawianie napisow
    public void repaint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.black);
        g.fillRect(0, HEIGHT -120, WIDTH, 150);
        g.drawImage(food, 0, HEIGHT-120,WIDTH,150, null);
        
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, 90);
        //g.drawImage(food1, 0, 0,WIDTH,90, null);
        
        g.setColor(Color.white);
        g.fillRect(smile.x, smile.y, smile.width, smile.height);
        g.drawImage(smile1, smile.x, smile.y,smile.width,smile.height, null);
        
        
        
        for(Rectangle fruit : fruits) {
            
            paintFruit(g, fruit);

        }
        
        //dodawanie napisow przy konkretnych warunkach
        g.setColor(Color.red.darker().darker());
        g.setFont(new Font("Arial",1,30));
        
        if (!gameOver && !started) {  
            
            g.drawString("Kliknij, aby rozpoczac gre :)", 200, HEIGHT/2-50);
        
        }
        g.setFont(new Font("Arial",1,100));
        if (gameOver) {
            
            g.drawString("GAME OVER", 75, HEIGHT/2-50);

        }
        
        if(!gameOver && started) {
            
            g.drawString(String.valueOf(score),WIDTH/2-25,100);
        }
        
        g.setFont(new Font("Arial",1,20));
        
        if(!gameOver && started || gameOver&&started||!gameOver&&!started) {
            g.drawString("Wcisnij ENTER, aby zakonczyc gre.", 20, HEIGHT-40);
            g.drawString("Matylda Lange 188916", 20, HEIGHT-60);
        }
        
        if(!gameOver && started || gameOver&&started||!gameOver&&!started) {
            g.drawString("Punkty:", 600, HEIGHT-40);
            g.drawString(String.valueOf(score), 680, HEIGHT-40);
        }
        
    }
    
    
    public static void main(String[] args) {
        flyingSmile= new FlyingSmile();
    }
    
    @Override
    public void mouseClicked (MouseEvent e){
        jump();
    }
    
    @Override
    public void mousePressed (MouseEvent e){
        
    }
    
    @Override
    public void mouseReleased (MouseEvent e){
        
    }
    
    @Override
    public void mouseEntered (MouseEvent e){
        
    }
    
    @Override
    public void mouseExited (MouseEvent e){
        
    }
    
    @Override
    public void keyTyped (KeyEvent e){
        jump();
    }
    
    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode()==KeyEvent.VK_ENTER) {
            closeProgram();
        }
    }
    
    @Override
    public void keyReleased (KeyEvent e){
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
            jump();
            
        }
    }
    
    public void closeProgram () {
        System.exit(0);
    }
   
}

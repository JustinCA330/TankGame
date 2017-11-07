import java.awt.*;
import java.util.*;
import java.awt.image.ImageObserver;


public class ObjectController {
    
    public int x, y, width, height, speed;
    public Image img;
    
    public ObjectController(Image img, int x, int y, int speed){
        
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
        this.speed = speed;
        
    }
    
    public int getX(){
        
        return this.x;
        
    }
    
    public int getY(){
        
        return this.y;
        
    }
    
    public int getWidth(){
        
        return this.width;
        
    }
    
    public int getHeight(){
        
        return this.height;
        
    }
    
    public void setX(int a){
        
        this.x = a;
        
    }
    
    public void setY(int b){
        
        this.y = b;
        
    }
    
    public void draw(Graphics g, ImageObserver obs){
        
        g.drawImage(img, x, y, obs);
        
    }
}

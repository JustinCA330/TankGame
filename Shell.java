/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author J_Carillo
 */
import java.awt.*;
import java.awt.image.ImageObserver;
public class Shell extends Collision{

    private Point location, speed;
    int strength;
    Tank owner;
    
    public Shell(Point location, Point speed, int strength, Tank owner){
        this.location = location;
        this.speed = speed;
        this.strength = strength;
        this.owner = owner;
        
    }
    public void draw(Graphics g, ImageObserver obs){}
    
}

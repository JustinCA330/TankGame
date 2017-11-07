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
import java.image.ImageObserver;
import java.util.Observable;

public class Tank extends Collision{

    String name;
    Shell weapon;
    int lives, health, strength, score, respawnCounter, height, width, direction;
    
    public Tank(Image img, int x, int y, int speed){
        
        super(img, x, y, speed);
        /*resetPoint = new Point(location);
        this.gunLocation = new Point (32, 32);*/
        
        this.name = name;
        weapon = new Shell();
        //motion = new InputController(this, controls, TankWorld.getInstance());
        lives = 2;
        health = 100;
        strength = 100;
        score = 0;
        respawnCounter=0;
        height = 64;
        width = 64;
        direction = 180;
        //this.location = new Rectangle(location.x,location.y, width, height);
        
    }

public void turn(int angle){
}

public void update(int w, int h){
}

public void draw (Graphics g, ImageObserver obs){
}

public void die(){
}

public void reset(){
}

}


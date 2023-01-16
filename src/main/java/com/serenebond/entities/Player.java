package com.serenebond.entities;

import com.serenebond.main.Game;
import com.serenebond.world.Camera;
import com.serenebond.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.serenebond.main.Game.gameState;
import static com.serenebond.world.World.isFree;

public class Player extends Entity {

    public boolean up, down, left, right;
    public int up_dir = 1, down_dir = 0, left_dir = 2, right_dir = 3; //todo enum maybe
    public int dir = down_dir;
    public int speed = 1;
    public boolean isRunning = false;

    public double life = 20, energy = 20;
    public static double maxLife = 20, maxEnergy = 20;

    public int coord_x = 0, coord_y = 0;

    public int frames = 0, index = 0;
    public int maxFrames = 10;
    private final int maxIndex = 3;
    public static boolean moved = false;
    private BufferedImage[] upPlayer;
    private BufferedImage[] downPlayer;
    private BufferedImage[] leftPlayer;
    private BufferedImage[] rightPlayer;

    private PlayerHairstyle hairStyle = new PlayerHairstyle(9, 0x4f1a00);

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        upPlayer = new BufferedImage[4];
        downPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        rightPlayer = new BufferedImage[4];

        for(int i = 0; i < 4; i++){
            upPlayer[i] = Game.spritesheet.getSprite(64 +(i * 16), 144, 16, 16);
        }
        for(int i = 0; i < 4; i++){
            downPlayer[i] = Game.spritesheet.getSprite(64 +(i*16), 160, 16, 16);
        }
        for(int i = 0; i < 4; i++){
            leftPlayer[i] = Game.spritesheet.getSprite(64 +(i*16), 176, 16, 16);
        }
        for(int i = 0; i < 4; i++){
            rightPlayer[i] = Game.spritesheet.getSprite(64 +(i*16), 192, 16, 16);
        }
    }

    public void tick(){
        moved = false;
        if(up && isFree(x,y-speed)){
            moved =  true;
            y-=speed;
            dir = up_dir;
            coord_y = coord_y - speed;
        }else if(down && isFree(x,y+speed)){
            moved =  true;
            y+=speed;
            dir = down_dir;
            coord_y = coord_y + speed;
        }
        if(left && isFree(x-speed,y)){
            moved =  true;
            x-=speed;
            dir = left_dir;
            coord_x = coord_x - speed;
        }else if(right && isFree(x+speed,y)){
            moved =  true;
            x+=speed;
            dir = right_dir;
            coord_x = coord_x + speed;
        }
        if(moved){
            frames++;
            if(frames == maxFrames){
                frames = 0;
                index++;
                if(index > maxIndex){
                    index = 0;
                }
            }
        }else {
            frames = 0;
            index = 0;
        }

        if(life <= 0){
            gameState = "dead";
            life = 0;
        }

        Camera.x = Camera.clamp(this.getX() - (Game.Width/2), 0, World.Width*16 - Game.Width);
        Camera.y = Camera.clamp(this.getY() - (Game.Height/2), 0, World.Height*16 - Game.Height);
    }

    public void render(Graphics g){
        //todo optimize, you already have the dir as an int and may use a 2D sprite lookup, like in hairStyle
        if(dir == up_dir){
            g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }else if(dir == down_dir){
            g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }else if(dir == left_dir){
            g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }else if(dir == right_dir){
            g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }

        //todo setColor not working, either implement CPU side tinting or go for full opengl
        g.setColor(hairStyle.getHairColor());
        //g.drawImage(hairStyle.getHairStyle(dir), this.getX() - Camera.x, this.getY() - Camera.y, null);
        g.setColor(Color.WHITE);
    }

    public void run(){
        if(isRunning && moved){
            energy--;
            if(energy <= 0){
                isRunning = false;
            }
        }

        if(life < maxLife && energy >= 1){
            energy--;
            life++;
        }

        if(energy <= 0){
            life--;
        }
    }
}

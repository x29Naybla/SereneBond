package com.x29Naybla.Graphics;

import com.x29Naybla.Main.Game;

import java.awt.*;

import static com.x29Naybla.Main.Game.gameState;
import static com.x29Naybla.Main.Game.rand;

public class VFX {

    public static int time = 0, dayParts = 1;
    public static int days = 0;
    public static int weather = 0;
    public static int nightA = 0;
    public static double dayTimer = 0;


    public void render(Graphics g){
        if(time == 0){
            //day
            g.setColor(new Color(0,0, 25, nightA));
            g.fillRect(0, 0,Game.Width, Game.Height);

        }else if(time == 1){
            //night
            g.setColor(new Color(0,0, 25, nightA));
            g.fillRect(0, 0,Game.Width, Game.Height);
        }

        if(weather == 0){
            //Normal
        }
        if(weather == 1 && time == 0){
            //Cloudy
            g.setColor(new Color(255,255,255, 128));
            g.fillRect(0,0, Game.Width, Game.Height);
        }
        if(weather == 2 && time == 0){
            //Rainy
            g.setColor(new Color(0,0,255, 40));
            g.fillRect(0,0, Game.Width, Game.Height);
        }
    }

    public void run(){
        if(gameState == "playing"){
            dayTimer++;

            if(dayTimer >= 30){
                VFX.time++;
                dayTimer = 0;
                if(VFX.time > VFX.dayParts){
                    VFX.time= 0;
                    if(rand.nextInt(100) < 75){
                        VFX.weather = 0;
                    }else if(rand.nextInt(100) < 50){
                        VFX.weather = 1;
                    }else
                        VFX.weather = 2;

                    VFX.days++;
                }
            }

            if(time == 1){
                if(nightA < 191){
                    nightA = nightA + 20;
                    if(nightA > 191)
                        nightA = 191;
                }
            }

            if(time == 0){
                if(nightA > 0){
                    nightA = nightA - 20;
                    if(nightA < 0)
                        nightA = 0;
                }
            }
        }
    }
}

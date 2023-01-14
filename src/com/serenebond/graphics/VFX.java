package com.serenebond.graphics;

import com.serenebond.main.Game;

import java.awt.*;

import static com.serenebond.main.Game.gameState;
import static com.serenebond.main.Game.rand;

public class VFX {

    public static int time = 0, dayParts = 1;
    public static int days = 0;
    public static int weather = 0;
    public static int timeTrans = 0;
    public static double dayTimer = 0;


    public void render(Graphics g){
        if(time == 0){
            //day
            g.setColor(new Color(0,0, 25, timeTrans));
            g.fillRect(0, 0,Game.Width, Game.Height);

        }else if(time == 1){
            //night
            g.setColor(new Color(0,0, 25, timeTrans));
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

            if(dayTimer >= (12*60)){
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
                if(timeTrans < 191){
                    timeTrans = timeTrans + 20;
                    if(timeTrans > 191)
                        timeTrans = 191;
                }
            }

            if(time == 0){
                if(timeTrans > 0){
                    timeTrans = timeTrans - 20;
                    if(timeTrans < 0)
                        timeTrans = 0;
                }
            }
        }
    }
}

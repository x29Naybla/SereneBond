package com.x29Naybla.Main;

import java.awt.*;

public class Pause {

    public int currOption = 1;
    public int maxOption = Language.pause.length - 1;

    public boolean up,down;

    public void tick(){
        if(up){
            up = false;
            currOption--;
            if(currOption < 0){
                currOption = maxOption;
            }
        }
        if(down){
            down = false;
            currOption++;
            if(currOption > maxOption){
                currOption = 0;
            }
        }
    }
    public void render(Graphics g){
        g.setColor(new Color(0,0,0, 128));
        g.fillRect(0,0,Game.Width*Game.Scale,Game.Height*Game.Scale);

        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.setColor(Color.WHITE);
        g.drawString(Language.paused, (Game.Width*Game.Scale) /2 - 130, (Game.Height*Game.Scale) /2);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString(Language.pause[0], (Game.Width*Game.Scale) /2 - 100,280);
        g.drawString(Language.pause[1],(Game.Width*Game.Scale) /2 - 100, 320);

        if(Language.pause[currOption].equals(Language.pause[0])){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 120,280);
        }else if(Language.pause[currOption].equals(Language.pause[1])){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 120,320);
        }
    }
}

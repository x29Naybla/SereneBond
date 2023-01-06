package com.x29Naybla.Main;

import java.awt.*;

public class Menu {

    public int currOption = 0;
    public int maxOption = Language.options.length - 1;

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
        g.setColor(Color.darkGray);
        g.fillRect(0,0,Game.Width*Game.Scale,Game.Height*Game.Scale);

        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.setColor(Color.WHITE);
        g.drawString("Serene Bond", (Game.Width*Game.Scale) /2 - 180, (Game.Height*Game.Scale) /2 - 150);


        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString(Language.options[0], (Game.Width*Game.Scale) /2 - 75,160);
        g.drawString(Language.options[1], (Game.Width*Game.Scale) /2 - 75,200);
        g.drawString(Language.options[2], (Game.Width*Game.Scale) /2 - 75,240);
        g.drawString(Language.options[3], (Game.Width*Game.Scale) /2 - 75,280);

        if(Language.options[currOption] == Language.options[0]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,160);
        }else if(Language.options[currOption] == Language.options[1]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,200);
        }else if(Language.options[currOption] == Language.options[2]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,240);
        }if(Language.options[currOption] == Language.options[3]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,280);
        }
    }
}

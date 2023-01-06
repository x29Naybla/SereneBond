package com.x29Naybla.Main;

import java.awt.*;

public class Pause {

    public void tick(){

    }
    public void render(Graphics g){
        g.setColor(new Color(0,0,0, 128));
        g.fillRect(0,0,Game.Width*Game.Scale,Game.Height*Game.Scale);

        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.setColor(Color.WHITE);
        g.drawString(Language.paused, (Game.Width*Game.Scale) /2 - 130, (Game.Height*Game.Scale) /2);
    }
}

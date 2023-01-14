package com.serenebond.graphics;

import com.serenebond.main.Game;

import java.awt.*;

public class UI {

    public void render(Graphics g){
        //Life
        g.setColor(Color.black);
        g.fillRect(5,(Game.Height - 15),50, 10);

        g.setColor(Color.red);
        g.fillRect(5,(Game.Height - 15),(int)((Game.player.life / Game.player.maxLife) * 50), 10);

        //Energy
        g.setColor(Color.black);
        g.fillRect(5,(Game.Height - 27),50, 10);

        g.setColor(Color.yellow);
        g.fillRect(5,(Game.Height - 27),(int)((Game.player.energy/ Game.player.maxEnergy) * 50), 10);
    }
}

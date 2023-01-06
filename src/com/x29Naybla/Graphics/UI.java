package com.x29Naybla.Graphics;

import com.x29Naybla.Entities.Player;
import com.x29Naybla.Main.Game;

import java.awt.*;

public class UI {

    public void render(Graphics g){
        //Life
        g.setColor(Color.black);
        g.fillRect(5,(Game.Height - 15),50, 10);

        g.setColor(Color.red);
        g.fillRect(5,(Game.Height - 15),(int)((Player.life / Player.maxLife) * 50), 10);

        //Energy
        g.setColor(Color.black);
        g.fillRect(5,(Game.Height - 27),50, 10);

        g.setColor(Color.yellow);
        g.fillRect(5,(Game.Height - 27),(int)((Player.energy/ Player.maxEnergy) * 50), 10);
    }
}

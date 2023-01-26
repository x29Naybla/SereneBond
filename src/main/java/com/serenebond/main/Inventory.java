package com.serenebond.main;

import com.serenebond.tile.Tile;
import com.serenebond.tile.WallTile;
import com.serenebond.world.Camera;
import com.serenebond.world.World;

import java.awt.*;
import java.util.ArrayList;

import static com.serenebond.main.Game.gameState;
import static com.serenebond.main.Game.player;
import static com.serenebond.tile.Tiles.itemsTileType;

public class Inventory {

    public int selected = 0;
    public int mx,my;
    public boolean mouseL = false, mouseR = false, invOpened = false;
    private ArrayList<Item> items;
    private int initPos = (((Game.Width*Game.Scale) / 2) - (10 *36) / 2);
    private int slots = 10, rows = 5;
    public Inventory(){
        items = new ArrayList<>();
        items.add(Item.stone);
        items.add(Item.sand);
        items.add(Item.dirt);
        items.add(Item.grass);
        items.add(Item.snow);
        items.add(Item.water);
    }
    public void tick(){

        if(gameState.equals("dead")){
            items.clear();
            return;

        }

        if(mouseL){
            mouseL = false;
            if(mx >= initPos && mx < initPos + (36 * slots)){
                if(my >= Game.Height*Game.Scale - 37 && my < Game.Height*Game.Scale - 1){
                    selected = (int)((mx-initPos)/36);
                    System.out.println(selected);
                }

            }
        }else if(mouseR){
            mouseR = false;
            mx = (int)mx/3 + Camera.x;
            my = (int)my/3 + Camera.y;

            int tilex = mx/16;
            int tiley = my/16;

            if(selected >= items.size()){
                return;
            }else if (selected < items.size()){
                Tile tileType = itemsTileType.get(items.get(selected).getID());
                if(tileType instanceof WallTile){
                    if((player.getX()/16 == tilex && player.getY()/16 == tiley) ||
                            (player.getX()/16 == tilex && (player.getY()+16)/16 == tiley) ||
                            ((player.getX()+16)/16 == tilex && player.getY()/16 == tiley) ||
                            ((player.getX()+16)/16 == tilex && (player.getY()+16)/16 == tiley)){

                    }else{
                        World.tiles[tilex][tiley] = tileType;
                    }
                }else{
                    World.tiles[tilex][tiley] = tileType;
                }
            }
        }

        if(Game.wheelUp){
            Game.wheelUp = false;
            selected++;
            if(selected > 9)
                selected = 0;
        }else if(Game.wheelDown){
            Game.wheelDown = false;
            selected--;
            if(selected < 0)
                selected = 9;
        }
    }
    public void render(Graphics g){
        for(int i = 0; i < slots; i++){
            g.setColor(new Color(50,50,50,123));
            g.fillRect(initPos + (i*36) + 1, Game.Height*Game.Scale - 37, 36, 36);
            g.setColor(Color.black);
            g.drawRect(initPos + (i*36) + 1, Game.Height*Game.Scale - 37, 36, 36);
            if(i < items.size()){
                g.drawImage(items.get(i).getSprite(), initPos + (i*36) + 6, Game.Height*Game.Scale - 32,27, 27, null);
            }
            if(selected == i){
                g.setColor(Color.WHITE);
                g.drawRect(initPos + (i*36) + 1, Game.Height*Game.Scale - 37, 35, 36);
            }
        }

        if (invOpened){
            for(int x = 0; x < slots; x++){
                for(int y = 0; y < rows; y++){
                    g.setColor(new Color(50,50,50,123));
                    g.fillRect(initPos + (x*36) + 1, (Game.Height*Game.Scale - 300) + (y*37), 36, 36);
                    g.setColor(Color.black);
                    g.drawRect(initPos + (x*36) + 1, (Game.Height*Game.Scale - 300) + (y*37), 36, 36);
                }
            }
        }
    }
}

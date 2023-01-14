package com.serenebond.original.main;

import java.awt.*;
import java.io.*;

public class Menu {

    public int currOption = 0;
    public int maxOption = Language.OPTIONS.length - 1;

    public boolean up, down, enter;
    public static boolean saveExists = false, saveGame = false;
    public void tick(){
        File file = new File(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/world.txt");
        if(file.exists()){
            saveExists = true;
        }else {
            saveExists = false;
        }
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
        if(enter){
            enter = false;
            if(Language.OPTIONS[currOption].equals(Language.OPTIONS[0])){
                Game.gameState = "playing";
                Game.newGame();
                file = new File(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/world.txt");
                file.delete();
            }else if(Language.OPTIONS[currOption].equals(Language.OPTIONS[1])){
                file = new File(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/world.txt");
                if(file.exists()){
                    String saver = loadGame(29);
                    applyGame(saver);
                }
            }else if(Language.OPTIONS[currOption].equals(Language.OPTIONS[2])){
                Game.language = Language.EMPTY;
            }else if(Language.OPTIONS[currOption].equals(Language.OPTIONS[3])){
                System.exit(0);
            }
        }
    }

    public static void applyGame(String str){
        String[] spl = str.split("/");
        for(int i = 0; i < spl.length; i++){
            String[] spl2 = spl[i].split(":");
            switch (spl2[0]){
                case "energy":
                    Game.newGame();
                    Game.player.energy = Double.parseDouble(spl2[1]);
                    Game.gameState = "playing";
                break;
                case "life":
                    Game.player.life = Double.parseDouble(spl2[1]);
                break;
                case "facing":
                    Game.player.dir = Integer.parseInt(spl2[1]);
                break;
                case "xpos":
                    Game.player.setX(Integer.parseInt(spl2[1]));
                break;
                case "ypos":
                    Game.player.setY(Integer.parseInt(spl2[1]));
                break;
                case "xcoord":
                    Game.player.coord_x = Integer.parseInt(spl2[1]);
                break;
                case "ycoord":
                    Game.player.coord_y = Integer.parseInt(spl2[1]);
                break;
                case "days":
                    Game.vfx.days = Integer.parseInt(spl2[1]);
                break;
                case "selected":
                     Game.inventory.selected = Integer.parseInt(spl2[1]);
                break;
                case "weather":
                    Game.vfx.weather = Integer.parseInt(spl2[1]);
                break;
                case "dayTimer":
                    Game.vfx.dayTimer = Double.parseDouble(spl2[1]);
                break;
                case "time":
                    Game.vfx.time = Integer.parseInt(spl2[1]);
                break;
                case "timeTrans":
                    Game.vfx.timeTrans = Integer.parseInt(spl2[1]);
                break;
            }
        }
    }
    public static String loadGame(int encode){
        String line = "";
        File file = new File(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/world.txt");
        if(file.exists()){
            try{
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/world.txt"));
                try{
                    while ((singleLine = reader.readLine()) != null){
                        String[] trans = singleLine.split(":");
                        char[] val = trans[1].toCharArray();
                        trans[1] = "";
                        for(int i = 0; i < val.length; i++){
                            val[i]-=encode;
                            trans[1]+=val[i];
                        }
                        line+=trans[0];
                        line+=":";
                        line+=trans[1];
                        line+="/";
                    }
                }catch (IOException e){}
            }catch (FileNotFoundException e){}
        }
        return line;
    }
    public static void saveGame(String[] val1, int[] val2, int encode){
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/world.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i < val1.length; i++){
            String current = val1[i];
            current+=":";
            char[] value = Integer.toString(val2[i]).toCharArray();
            for(int n = 0; n < value.length; n++){
                value[n]+=encode;
                current+=value[n];
            }
            try {
                writer.write(current);
                if(i < val1.length - 1)
                    writer.newLine();
            }catch (IOException e){}
        }
        try{
            writer.flush();
            writer.close();
        }catch (IOException e){}
    }
    public void render(Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(0,0,Game.Width*Game.Scale,Game.Height*Game.Scale);

        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.setColor(Color.WHITE);
        g.drawString("Serene Bond", (Game.Width*Game.Scale) /2 - 180, (Game.Height*Game.Scale) /2 - 150);


        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString(Game.language.get("new_world"), (Game.Width*Game.Scale) /2 - 75,160);
        g.drawString(Game.language.get("load_world"), (Game.Width*Game.Scale) /2 - 75,200);
        g.drawString(Game.language.get("change_language"), (Game.Width*Game.Scale) /2 - 75,240);
        g.drawString(Game.language.get("exit"), (Game.Width*Game.Scale) /2 - 75,280);

        if(Language.OPTIONS[currOption] == Language.OPTIONS[0]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,160);
        }else if(Language.OPTIONS[currOption] == Language.OPTIONS[1]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,200);
        }else if(Language.OPTIONS[currOption] == Language.OPTIONS[2]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,240);
        }if(Language.OPTIONS[currOption] == Language.OPTIONS[3]){
            g.drawString("*", (Game.Width*Game.Scale) /2 - 90,280);
        }
    }
}

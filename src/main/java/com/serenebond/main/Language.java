package com.serenebond.main;

import java.io.*;

public class Language {

    public static String day;
    public static String dead;
    public static String respawn;
    public static String paused;

    public static int currLang = 0;
    public int maxLang = 1;

    public static String[] options = {"0", "1", "2", "3"};
    public static String[] pause = {"0", "1"};

    public void tick() {

        if(currLang == 0){
            //English selected
            day = "Day: ";
            dead = "You Died";
            respawn = "Press Enter to Respawn";
            paused = "Paused";

            options[0] = "New World";
            options[1] = "Load World";
            options[2] = "Change Language";
            options[3] = "Exit";

            pause[0] = "Main Menu";
            pause[1] = "Save Progress";
        }
        if(currLang == 1){
            //Portuguese selected
            day = "Dia: ";
            dead = "Voc\u00EA Morreu";
            respawn = "Aperte Enter Para Respawnar";
            paused = "Pausado";

            options[0] = "Novo Mundo";
            options[1] = "Carregar";
            options[2] = "Mudar Idioma";
            options[3] = "Sair";

            pause[0] = "Menu Principal";
            pause[1] = "Salvar Progresso";

        }

        String langSaver = loadSettings(29);
        Language.applySettings(langSaver);
    }

    public void changeLang(){
            currLang = currLang +1;
            System.out.println(currLang);
            if(currLang > maxLang){
                currLang = 0;
            }
        String[] opt1 = {"language"};
        int[] opt2 = {currLang};
        saveSettings(opt1, opt2, 29);
    }

    public static void applySettings(String str){
        String[] spl = str.split("/");
        for(int a = 0; a < spl.length; a++){
            String[] spl2 = spl[a].split(":");
            switch (spl2[0]){
                case "language":
                    Language.currLang = Integer.parseInt(spl2[1]);
                break;

            }
        }
    }
    public static String loadSettings(int encode){
        String line = "";
        File file = new File(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/settings.txt");
        if(file.exists()){
            try{
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/settings.txt"));
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
    public static void saveSettings(String[] val1, int[] val2, int encode){
        BufferedWriter writer = null;
        try{
            new File(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/").mkdirs();
            writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/AppData/Roaming/SereneBond/settings.txt"));
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
}

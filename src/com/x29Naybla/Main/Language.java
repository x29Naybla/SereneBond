package com.x29Naybla.Main;

public class Language {

    public static String day;
    public static String dead;
    public static String respawn;
    public static String paused;

    public int currLang = 0;
    public int maxLang = 3;

    public static String[] options = {"0", "1", "2", "3"};
    public static String[] pause = {"0", "1"};

    public void tick(){

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
            dead = "Você Morreu";
            respawn = "Aperte Enter Para Respawnar";
            paused = "Pausado";

            options[0] = "Novo Mundo";
            options[1] = "Carregar";
            options[2] = "Mudar Idioma";
            options[3] = "Sair";

            pause[0] = "Menu Principal";
            pause[1] = "Salvar Progresso";

        }
        if(currLang == 2){
            //French selected
            day = "Jour: ";
            dead = "Tu es Mort";
            respawn = "Press Enter to Respawn";
            paused = "En Pause";

            options[0] = "Nouveau Monde";
            options[1] = "Charger";
            options[2] = "Changer la Langue";
            options[3] = "Quitter";

            pause[0] = "Main menu";
            pause[1] = "Save Progress";

        }
        if(currLang == 3){
            //German selected
            day = "Tag: ";
            dead = "Du Bist Gestorben";
            respawn = "Press Enter to Respawn";
            paused = "Pausiert";

            options[0] = "Neue Welt";
            options[1] = "Laden";
            options[2] = "Sprache Ändern";
            options[3] = "Verlassen";

            pause[0] = "Main Menu";
            pause[1] = "Save Progress";
        }
    }

    public void changeLang(){
            currLang = currLang +1;
            System.out.println(currLang);
            if(currLang > maxLang){
                currLang = 0;
            }
    }

}

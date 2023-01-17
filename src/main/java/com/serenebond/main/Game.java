package com.serenebond.main;

import com.serenebond.entities.Entity;
import com.serenebond.entities.Player;
import com.serenebond.graphics.VFX;
import com.serenebond.graphics.Spritesheet;
import com.serenebond.graphics.UI;
import com.serenebond.world.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javax.swing.JFrame.*;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseWheelListener {

    public static JFrame frame;
    public static boolean wheelUp, wheelDown;
    String frameName = "Serene Bond";
    public static final int Width = 240, Height = 160, Scale = 3;

    private boolean isRunning = false, debug = false;
    int fps = 0;

    private Thread thread;

    public static Random rand;

    private BufferedImage image;
    public static Spritesheet spritesheet;

    public static World world;
    public static Language language;
    public static Menu menu;
    public static Pause pause;
    public static String gameState = "menu";

    public static List<Entity> entities;
    public static Player player;
    public static Inventory inventory;

    public UI ui;
    public static VFX vfx;

    public Game(){
        frame();
        rand = new Random();
        language = new Language();
        menu = new Menu();
        pause = new Pause();
        ui = new UI();
        vfx = new VFX();
        image = new BufferedImage(Width,Height, BufferedImage.TYPE_INT_RGB);
        spritesheet = new Spritesheet("default.png");
        inventory = new Inventory();
        newGame();
    }

    public void frame(){
        setPreferredSize(new Dimension(Width*Scale, Height*Scale));
        frame = new JFrame(frameName);
        frame.add(this);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.pack();
        Image icon = null;
        try{ icon = ImageIO.read(getClass().getResource("/icon.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        frame.setIconImage(icon);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop(){
        isRunning = false;
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        game.addKeyListener(game);
        game.addMouseListener(game);
        game.addMouseWheelListener(game);
    }
    public static void newGame(){
        entities = new ArrayList<Entity>();
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(0, 48, 16, 16));
        entities.add(player);
        world = new World("world.png");
    }

    public void tick() {
        if(gameState.equals("playing")){
            for(int i = 0; i < entities.size(); i++){
                Entity e = entities.get(i);
                if(e instanceof Player){
                    //player updates
                    if(player.getX() < 0){
                        player.setX(World.ActW);
                        player.coord_x = World.ActW - world.spawnX;
                    }
                    if(player.getX() > World.ActW){
                        player.setX(0);
                        player.coord_x = -world.spawnX;
                    }
                    if(player.isRunning){
                        player.speed = 2;
                    }else if(!player.isRunning){
                        player.speed = 1;
                    }
                }
                e.tick();
            }
            inventory.tick();
        }else if(gameState.equals("dead")){

        }else if(gameState.equals("paused")){
            pause.tick();
            if(Menu.saveGame){
                Menu.saveGame = false;
                String[] opt1 = {"energy","life","facing","xpos","ypos","xcoord","ycoord","days","selected", "weather", "dayTimer", "time", "timeTrans"};
                int[] opt2 = {(int)player.energy, (int)player.life, player.dir, player.getX(), player.getY(), player.coord_x, player.coord_y, vfx.days, inventory.selected, vfx.weather, (int)vfx.dayTimer, vfx.time, vfx.timeTrans};
                Menu.saveGame(opt1, opt2, 29);
                System.out.println("You saved the game");
            }
        }else if(gameState.equals("menu")){
            menu.tick();
            language.tick();
        }


    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,Width,Height);


        world.render(g);
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.render(g);
        }

        vfx.render(g);
        ui.render(g);

        if(debug){
            g.setFont(new Font("Arial", Font.BOLD, 8));
            g.setColor(Color.black);
            g.drawString("FPS: " + fps, 4, 10);
            g.drawString(Language.day + VFX.days, 34, 10);
            g.drawString("X: "+ (player.coord_x / 16), 4, 26);
            g.drawString("Y: "+ (player.coord_y / 16), 4, 37);
        }

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image,0,0,Width*Scale,Height*Scale, null);

        if(gameState.equals("playing")){
            inventory.render(g);
        }else if(gameState.equals("dead")){
            g.setColor(new Color(0,0,0, 128));
            g.fillRect(0,0,Width*Scale,Height*Scale);

            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.setColor(Color.WHITE);
            g.drawString(Language.dead, (Width*Scale) /2 - 130, (Height*Scale) /2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString(Language.respawn, (Width*Scale) /2 - 110, (Height*Scale) /2 + 50);
        }else if(gameState.equals("paused")){
            pause.render(g);
        }else if(gameState.equals("menu")){
            menu.render(g);
        }
        bs.show();
    }

    public void run() {
        requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();

        while(isRunning){
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                fps = frames;
                frames = 0;
                timer+=1000;

                vfx.run();
                player.run();
            }

        }

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_1||
                e.getKeyCode() == KeyEvent.VK_2||
                e.getKeyCode() == KeyEvent.VK_3||
                e.getKeyCode() == KeyEvent.VK_4||
                e.getKeyCode() == KeyEvent.VK_5||
                e.getKeyCode() == KeyEvent.VK_6||
                e.getKeyCode() == KeyEvent.VK_7||
                e.getKeyCode() == KeyEvent.VK_8||
                e.getKeyCode() == KeyEvent.VK_9||
                e.getKeyCode() == KeyEvent.VK_0){
            if(e.getKeyCode() == KeyEvent.VK_1)
                inventory.selected = 0;
            if(e.getKeyCode() == KeyEvent.VK_2)
                inventory.selected = 1;
            if(e.getKeyCode() == KeyEvent.VK_3)
                inventory.selected = 2;
            if(e.getKeyCode() == KeyEvent.VK_4)
                inventory.selected = 3;
            if(e.getKeyCode() == KeyEvent.VK_5)
                inventory.selected = 4;
            if(e.getKeyCode() == KeyEvent.VK_6)
                inventory.selected = 5;
            if(e.getKeyCode() == KeyEvent.VK_7)
                inventory.selected = 6;
            if(e.getKeyCode() == KeyEvent.VK_8)
                inventory.selected = 7;
            if(e.getKeyCode() == KeyEvent.VK_9)
                inventory.selected = 8;
            if(e.getKeyCode() == KeyEvent.VK_0)
                inventory.selected = 9;

        }

        if(e.getKeyCode() == KeyEvent.VK_F1 && gameState.equals("playing")){
            if(!debug){
                debug = true;
            }else if(debug){
                debug = false;
            }

        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(gameState.equals("playing")){
                gameState = "paused";
            }else if(gameState.equals("paused")){
                gameState = "playing";
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(gameState.equals("menu")){
                menu.enter = true;
            }
            if(gameState.equals("dead")){
                gameState = "playing";
                player.life = player.maxLife;
                player.energy = player.maxEnergy;
                player.setX(world.spawnX);
                player.setY(world.spawnY);
                player.coord_x = 0;
                player.coord_y = 0;
            }
            if(gameState.equals("paused")){
                if(Language.pause[pause.currOption].equals(Language.pause[0])){
                    gameState = "menu";
                }else if(Language.pause[pause.currOption].equals(Language.pause[1])){
                    Menu.saveGame = true;
                }
            }

        }

        if(e.getKeyCode() == KeyEvent.VK_SHIFT && !(player.energy <= 0)){
            player.isRunning = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
            player.up = true;
            if(gameState.equals("menu")){
                menu.up = true;
            }
            if(gameState.equals("paused")){
                pause.up = true;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = true;
            if(gameState.equals("menu")){
                menu.down = true;
            }
            if(gameState.equals("paused")){
                pause.down = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = true;
        }else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right = true;
        }
    }

    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            player.isRunning = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
            player.up = false;
        }else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = false;
        }else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right = false;
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            inventory.mouseL = true;


        if(e.getButton() == MouseEvent.BUTTON3){
            inventory.mouseR = true;
        }

        inventory.mx = e.getX();
        inventory.my = e.getY();

    }

    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3)
            inventory.mouseR = false;

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() == 1){
            wheelUp = true;
        }
        if(e.getWheelRotation() == -1){
            wheelDown = true;
        }
    }
}
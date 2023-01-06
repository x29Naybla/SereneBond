package com.x29Naybla.Main;

import com.x29Naybla.Entities.Entity;
import com.x29Naybla.Entities.Player;
import com.x29Naybla.Graphics.VFX;
import com.x29Naybla.Graphics.Spritesheet;
import com.x29Naybla.Graphics.UI;
import com.x29Naybla.World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javax.swing.JFrame.*;

public class Game extends Canvas implements Runnable, KeyListener{

    public static JFrame frame;
    String frameName = "Serene bond";
    public static final int Width = 240, Height = 160;
    private final int Scale = 3;

    private boolean isRunning = false, debug = false;
    int fps = 0;

    private Thread thread;

    public static Random rand;

    private BufferedImage image;
    public static Spritesheet spritesheet;

    public static World world;

    public List<Entity> entities;
    public static Player player;
    public UI ui;
    public VFX vfx;


    public Game(){
        spritesheet = new Spritesheet("/default.png");
        rand = new Random();

        setPreferredSize(new Dimension(Width*Scale, Height*Scale));
        frame = new JFrame(frameName);
        frame.add(this);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.pack();
        frame.setIconImage(new ImageIcon("/icon.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        ui = new UI();
        vfx = new VFX();
        image = new BufferedImage(Width,Height, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();
        player = new Player(0, 0, 16, 16,spritesheet.getSprite(0,48,16,16));
        entities.add(player);
        world = new World("/world2.png");
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop(){
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        isRunning = false;
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        game.addKeyListener(game);
    }
    public void tick() {
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            if(e instanceof Player){
                //player updates
                if(player.getX() < 0){
                    player.setX(World.ActW);
                }
                if(player.getX() > World.ActW){
                    player.setX(0);
                }

            }
            e.tick();
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

        if(debug){
            g.setFont(new Font("Arial", Font.BOLD, 8));
            g.setColor(Color.black);
            g.drawString("FPS: " + fps, 4, 10);
            g.drawString(" Day: " + VFX.days, 34, 10);
            g.drawString("X: "+ (player.coord_x / 16), 4, 26);
            g.drawString("Y: "+ (player.coord_y / 16), 4, 37);
        }

        ui.render(g);

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image,0,0,Width*Scale,Height*Scale, null);
        bs.show();
    }

    public void run() {
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
        if(e.getKeyCode() == KeyEvent.VK_F1 && !debug){
                debug = true;
                return;
        }

        if(e.getKeyCode() == KeyEvent.VK_F1 && debug){
            debug = false;
            return;
        }

        if(e.getKeyCode() == KeyEvent.VK_SHIFT && !(player.energy <= 0)){
            player.isRunning = true;
            player.speed = 2;
        }

        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
            player.up = true;
        }else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = true;
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
            player.speed = 1;
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
}
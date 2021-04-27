package tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player extends Thread {
    protected String filename;
    private Player player;
    private boolean paused = true;
    private boolean loop = true;
    BufferedInputStream buffer;

    public MP3Player(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            buffer = new BufferedInputStream(new FileInputStream(filename));
            player = new Player(buffer);
            while (true) {
                // System.out.println("player.play()");
                try {
                    // System.out.println(player.play(0));
                    while(player.play(1)==true&&!paused);
                } catch (JavaLayerException e) {
                    System.out.println(e);
                }
                if(player.play(1)==false){
                    buffer = new BufferedInputStream(new FileInputStream(filename));
                    player = new Player(buffer);
                    if(loop&&!paused){
                        continue;
                    }
                    paused = true;
                }
                System.out.println("this.wait()");
                synchronized (this) {
                    this.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        paused = false;
        System.out.println("play");
        if (!isAlive()) {
            start();
        } else {
            synchronized (this) {
                this.notifyAll();
                // System.out.println("play notifyAll");
            }
        }
    }

    public void pause() {
        paused = true;
        System.out.println("pause");
    }

    public void close(){
        player.close();
    }

    void changeMusic(String filename){
        this.filename = filename;
        try {
            if (player != null) {
                player.close();
            }
            buffer = new BufferedInputStream(new FileInputStream(filename));
            player = new Player(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the closed
     */
    public boolean isClosed() {
        return paused;
    }

    public static void main(String[] args) {
        MP3Player mp3 = new MP3Player("res/1.mp3");
        mp3.play();
        try {
            sleep(1000);
            while (true) {
                System.out.println(mp3.player.getPosition());
                sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

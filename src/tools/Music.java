package tools;

import java.applet.*;
import java.io.File;
import java.net.MalformedURLException;

public class Music {

    private Music() {
    }

    private static AudioClip[] clip = new AudioClip[4];
    private static String[] filename = {
        // playBegin
        "res/bg.mp3",
        // playEliminate
        "res/SEB_platinum.wav",
        // palyTransform
        "res/SEB_mino3.wav",
        // playGameover
        "res/SEB_mino3.wav",
    };
    private static boolean paused = false;

    // 播放开始音效
    public static void playBegin() {
        if(paused){
            return;
        }
        if(clip[0] == null){
            try {
                clip[0] = Applet.newAudioClip(new File(filename[0]).toURI().toURL());
            } catch (MalformedURLException e) {
                System.out.println("开始音效播放错误:"+e.getMessage());
                return;
            }
        }
        clip[0].loop();
    }

    // 播放消除音效
    public static void playEliminate(){
        if(paused){
            return;
        }
        if(clip[1] == null){
            try {
                clip[1] = Applet.newAudioClip(new File(filename[1]).toURI().toURL());
            } catch (MalformedURLException e) {
                System.out.println("消除音效播放错误:"+e.getMessage());
                return;
            }
        }
        clip[1].play();
    }

    // 播放变形音效
    public static void palyTransform(){
        if(paused){
            return;
        }
        if(clip[2] == null){
            try {
                clip[2] = Applet.newAudioClip(new File(filename[2]).toURI().toURL());
            } catch (MalformedURLException e) {
                System.out.println("变形音效播放错误:"+e.getMessage());
                return;
            }
        }
        clip[2].stop();
        clip[2].play();
    }

    // 播放游戏结束音效
    public static void playGameover(){
        if(paused){
            return;
        }
        if(clip[3] == null){
            try {
                clip[3] = Applet.newAudioClip(new File(filename[3]).toURI().toURL());
            } catch (MalformedURLException e) {
                System.out.println("游戏结束音效播放错误:"+e.getMessage());
                return;
            }
        }
        clip[3].play();
    }

    // 暂停音效
    public static void setPaused(boolean paused){
        Music.paused = paused;
        if(paused){
            for (AudioClip a : clip) {
                if(a != null){
                    a.stop();
                }
            }
        }else{
            playBegin();
        }
    }

    // 获取音效是否暂停
    public static boolean getPaused(){
        return paused;
    }
}
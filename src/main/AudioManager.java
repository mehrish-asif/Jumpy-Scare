package src.main;

import javax.sound.sampled.*;
import java.io.File;

public class AudioManager {
    private Clip backgroundMusic;
    private String[] bgmTracks = { "src/audio/Background-Music.wav", "src/audio/backgroun-music-2.wav" };
    private int currentTrackIndex = 0;
    private boolean manualStop = false;
    private long clipTime = 0;

    public void stopBGM() {
        if (backgroundMusic != null) {
            manualStop = true;
            clipTime = backgroundMusic.getMicrosecondPosition();
            backgroundMusic.stop();
            System.out.println("BGM Paused at: " + clipTime);
        }
    }

    public void startAlternateBGM() {
        System.out.println("Attempting to start BGM...");
        this.manualStop = false;
        if (backgroundMusic != null && !backgroundMusic.isRunning()) {
            backgroundMusic.setMicrosecondPosition(clipTime); 
            backgroundMusic.start();
            System.out.println("BGM Resumed");
        } else if (backgroundMusic == null) {
            playNextTrack(); 
        }
    }

    private void playNextTrack() {
        if (manualStop)
            return;

        try {
            File file = new File(bgmTracks[currentTrackIndex]);

            // Console check taake pata chale file mil rahi hai ya nahi
            if (!file.exists()) {
                System.out.println("CRITICAL ERROR: Music file NOT FOUND at " + bgmTracks[currentTrackIndex]);
                return;
            }

            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(stream);

            backgroundMusic.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    // Clip ko foran close karein taake memory free ho
                    if (!manualStop) {
                        System.out.println("Track finished, switching...");
                        clipTime = 0;
                        currentTrackIndex = (currentTrackIndex + 1) % bgmTracks.length;
                        playNextTrack();
                    }
                }
            });

            backgroundMusic.start();
            System.out.println("Playing: " + bgmTracks[currentTrackIndex]);

        } catch (Exception e) {
            System.out.println("BGM Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void playEffect(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists())
                return;

            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (Exception e) {
            System.out.println("Effect Error: " + e.getMessage());
        }
    }

    public void playSound(String filePath) {
        playEffect(filePath);
    }
    
}
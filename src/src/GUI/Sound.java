package GUI;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    private long pausePosition = 0;

    public Sound(String path) {
        try {
            URL url = getClass().getResource(path);

            if (url == null) {
                System.err.println("❌ Could not find sound file: " + path);
                return;
            } else {
                System.out.println("✅ Found sound file: " + url);
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
            System.out.println("🎵 Loaded sound: " + path);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
    public void pause() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
        }
    }

}

package com.noinc.bloomsjeopardy.utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
	private static SoundManager instance;
	private static final String SOUNDS_BASE_PATH = "/Assets/sounds/";

	private Clip backgroundClip;
	private float backgroundVolume = 0.8f; // Default background music volume
	private float duckedVolume = 0.3f; // Lowered volume during sound effects
	private boolean isBackgroundPlaying = false;

	// Cache opened clips 
	private final Map<String, Clip> clipCache = new HashMap<>();
	public static synchronized SoundManager getInstance() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}

	public void play(String fileName) {
		System.out.println("[SoundManager] play requested: " + fileName);
		Clip clip = clipCache.get(fileName);
		try {
			if (clip == null) {
				clip = loadClip(fileName);
				if (clip == null) {
					System.err.println("[SoundManager] loadClip returned null for " + fileName);
					return; // Already logged
				}
				clipCache.put(fileName, clip);
			}

			if (clip.isRunning()) {
				clip.stop();
			}
			// Duck background music if playing
			duckBackgroundMusic();
			clip.setFramePosition(0);
			clip.start();
			// Restore background music after effect finishes
			clip.addLineListener(event -> {
				if (event.getType() == LineEvent.Type.STOP) {
					restoreBackgroundMusic();
				}
			});
		} catch (Exception e) {
			System.err.println("[SoundManager] Failed to play '" + fileName + "': " + e.getMessage());
		}
	}

	public void playBackgroundMusic() {
		if (isBackgroundPlaying) return;
		if (backgroundClip == null) {
			backgroundClip = loadClip("background_music.wav");
			if (backgroundClip == null) return;
		}
		backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
		applyVolume(backgroundClip, backgroundVolume);
		isBackgroundPlaying = true;
	}

	public void stopBackgroundMusic() {
		if (backgroundClip != null && backgroundClip.isRunning()) {
			backgroundClip.stop();
		}
		isBackgroundPlaying = false;
	}

	private void duckBackgroundMusic() {
		if (isBackgroundPlaying && backgroundClip != null) {
			applyVolume(backgroundClip, duckedVolume);
		}
	}

	private void restoreBackgroundMusic() {
		if (isBackgroundPlaying && backgroundClip != null) {
			applyVolume(backgroundClip, backgroundVolume);
		}
	}

	public void stop(String fileName) {
		Clip clip = clipCache.get(fileName);
		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
	}

	public void stopAll() {
		for (Clip clip : clipCache.values()) {
			if (clip.isRunning()) {
				clip.stop();
			}
		}
	}

	public void setVolume(String fileName, float volume) {
		if ("background_music.wav".equals(fileName) && backgroundClip != null) {
			applyVolume(backgroundClip, volume);
			backgroundVolume = volume;
			return;
		}
		Clip clip = clipCache.get(fileName);
		if (clip == null) {
			// Try eager load so setting volume can work before first play
			clip = loadClip(fileName);
			if (clip == null) {
				return;
			}
			clipCache.put(fileName, clip);
		}
		applyVolume(clip, volume);
	}

	public void setMasterVolume(float volume) {
		for (Clip clip : clipCache.values()) {
			applyVolume(clip, volume);
		}
	}

	private void applyVolume(Clip clip, float volume) {
		// Clamp volume
		float v = Math.max(0f, Math.min(1f, volume));
		// Convert linear [0,1] to decibels using a simple mapping
		// Avoid log(0): use a small floor
		float dB;
		if (v == 0f) {
			dB = -80f; // effectively silent
		} else {
			dB = (float) (20.0 * Math.log10(v));
			dB = Math.max(-80f, Math.min(6f, dB));
		}

		try {
			if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gain.setValue(dB);
			}
		} catch (IllegalArgumentException ignored) {

		}
	}

	private Clip loadClip(String fileName) {
		URL url = getClass().getResource(SOUNDS_BASE_PATH + fileName);
		if (url == null) {
			System.err.println("[SoundManager] Resource not found: " + SOUNDS_BASE_PATH + fileName);
			return null;
		}
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(url)) {
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			// Ensure clip releases system resources when finished if not reused
			clip.addLineListener(event -> {
				if (event.getType() == LineEvent.Type.STOP) {
					// No auto-close to allow replay from cache; comment below if desired.
					// clip.close();
				}
			});
			return clip;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.err.println("[SoundManager] Failed to load '" + fileName + "': " + e.getMessage());
			return null;
		}
	}

	public void dispose() {
		for (Map.Entry<String, Clip> entry : clipCache.entrySet()) {
			Clip clip = entry.getValue();
			try {
				if (clip.isRunning()) clip.stop();
				clip.close();
			} catch (Exception ignored) {
			}
		}
		clipCache.clear();
        if (backgroundClip != null) {
            try {
                if (backgroundClip.isRunning()) backgroundClip.stop();
                backgroundClip.close();
            } catch (Exception ignored) {}
            backgroundClip = null;
        }
	}

	public void playIntro() {
		play("intro.wav");
	}

	public void playIntroNext() {
		play("intro_next.wav");
	}

	public void playLevelIntro(int level) {
		String file = "lev" + level + "_intro.wav";
		play(file);
	}

	public void playCorrectRandom() {
		int max = 6; // correct1..correct6.wav available
		int idx = 1 + (int) Math.floor(Math.random() * max);
		play("correct" + idx + ".wav");
	}

	public void playWrongRandom() {
		int max = 4; // wrong1..wrong4.wav available
		int idx = 1 + (int) Math.floor(Math.random() * max);
		play("wrong" + idx + ".wav");
	}

	public void playExitHover() {
		play("exit_hover.wav");
	}

	public void playExitPress() {
		int idx = 1 + (int) Math.floor(Math.random() * 2);
		play("exit" + idx + ".wav");
	}

	public void playExitPressAndWait() {
		int idx = 1 + (int) Math.floor(Math.random() * 2);
		String file = "exit" + idx + ".wav";
		Clip clip = clipCache.get(file);
		if (clip == null) {
			clip = loadClip(file);
			if (clip == null) return;
			clipCache.put(file, clip);
		}
		if (clip.isRunning()) {
			clip.stop();
		}
		clip.setFramePosition(0);
		final Object lock = new Object();
		clip.addLineListener(event -> {
			if (event.getType() == LineEvent.Type.STOP) {
				synchronized (lock) {
					lock.notify();
				}
			}
		});
		clip.start();
		// Block until playback finishes
		synchronized (lock) {
			try {
				long timeout = 5000; // max 5 seconds
				long start = System.currentTimeMillis();
				while (clip.isRunning() && (System.currentTimeMillis() - start < timeout)) {
					lock.wait(20);
				}
			} catch (InterruptedException ignored) {}
		}
	}
}

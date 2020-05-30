package audio;

import javax.sound.sampled.*;

public class AudioPlayer {

	private Clip clip;
	private boolean isPlaying;

	public AudioPlayer(String path) {
		try {

			AudioInputStream inputAudio = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));

			AudioFormat baseFormat = inputAudio.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

			AudioInputStream decodedInputAudio = AudioSystem.getAudioInputStream(decodeFormat, inputAudio);

			clip = AudioSystem.getClip();
			clip.open(decodedInputAudio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play(boolean repeated) {
		isPlaying = false;
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		if (repeated)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
		isPlaying = true;
	}

	public void stop() {
		isPlaying = false;
		if (clip.isRunning())
			clip.stop();
	}

	public void close() {
		stop();
		clip.close();
	}

	public boolean isPlaying() {
		return this.isPlaying;
	}
}

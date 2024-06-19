package crackengine.jcrackengine.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * Audio represents audio file along with its player <br/>
 * It allows you to set the path to the audio file and automatically creates the player<br/>
 * It contains also helper variables:
 * <p>
 *     <ul>
 *         <li>helperName - Helper name for audio file to find it in audio manager</li>
 *         <li>audioType - lets you specify audio type of this file i.e background_music, dialogue etc...<br/>
 *              Audio Type can be used in audio manager to set volume of all sounds of certain type</li>
 *         <li>Volume - volume of this track</li>
 *         <li>Playback Rate - simply how fast the sound will play. Example: setting it to 2.0 will make sound play 2x faster</li>
 *     </ul>
 * </p>
 */
public class Audio {
    private String relativePath="";
    private String helperName="";
    private int audioType=0;
    private double volume=1.0;
    private double playbackRate=1.0;
    private MediaPlayer player;

    public Audio(String relativePath, String helperName, int audioType, double volume, double playbackRate) {
        this.relativePath=relativePath;
        this.helperName=helperName;
        this.audioType=audioType;

        if(volume < 0) this.volume=0;
        else this.volume=volume;

        this.playbackRate=playbackRate;

        String externalPath = Objects.requireNonNull(getClass().getResource(relativePath)).toExternalForm();
        player = new MediaPlayer(new Media(externalPath));
        player.setVolume(this.volume);
        player.setRate(this.playbackRate);
    }

    public Audio(String relativePath, String helperName, int audioType) {
        this.relativePath=relativePath;
        this.helperName=helperName;
        this.audioType=audioType;

        String externalPath = Objects.requireNonNull(getClass().getResource(relativePath)).toExternalForm();
        player = new MediaPlayer(new Media(externalPath));
    }

    public Audio(String relativePath, String helperName) {
        this.relativePath=relativePath;
        this.helperName=helperName;

        String externalPath = Objects.requireNonNull(getClass().getResource(relativePath)).toExternalForm();
        player = new MediaPlayer(new Media(externalPath));
    }

    public Audio(String relativePath) {
        this.relativePath=relativePath;

        String externalPath = Objects.requireNonNull(getClass().getResource(relativePath)).toExternalForm();
        player = new MediaPlayer(new Media(externalPath));
    }


    public String getHelperName() {
        return helperName;
    }

    public Audio setHelperName(String helperName) {
        this.helperName = helperName;
        return this;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public Audio setRelativePath(String relativePath) {
        this.relativePath = relativePath;
        return this;
    }

    public int getAudioType() {
        return audioType;
    }

    public Audio setAudioType(int audioType) {
        this.audioType = audioType;
        return this;
    }

    public double getVolume() {
        return volume;
    }

    public Audio setVolume(double volume) {
        if(volume < 0) return this;
        this.volume = volume;
        player.setVolume(this.volume);
        return this;
    }

    public double getPlaybackRate() {
        return playbackRate;
    }

    public Audio setPlaybackRate(double playbackRate) {
        this.playbackRate = playbackRate;
        player.setRate(playbackRate);
        return this;
    }

    public MediaPlayer getPlayer() {
        return player;
    }
}

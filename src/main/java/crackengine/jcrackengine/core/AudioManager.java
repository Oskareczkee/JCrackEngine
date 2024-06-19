package crackengine.jcrackengine.core;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Audio manager manages all the audio in the game<br/>
 * It allows for global management of audio in your game <br/>
 * You can set global sound volume, so all sounds will have the same volume multiplier<br/>
 * You can also group audio using audio type in Audio class. You can set volume of each audio type separately<br/>
 */
public class AudioManager {
    private List<Audio> Sounds = new LinkedList<>(); /*I use simple linked list, cause sets and trees do not allow for duplicates, while duplicates may be useful*/
    private HashMap<Integer, Double> AudioTypeVolumes = new HashMap<Integer, Double>();
    private double globalSoundVolume = 1.0;

    private void putNewAudioType(int audioType){
        if(AudioTypeVolumes.containsKey(audioType)) return;
        AudioTypeVolumes.put(audioType, 1.0);
    }


    private void onVolumeChanged(){
        for(Audio audio : Sounds)
        {
            double volume = audio.getVolume();
            int audioType = audio.getAudioType();
            Double typeVolume = AudioTypeVolumes.get(audioType);

            if(typeVolume==null)
                typeVolume=1.0;

            audio.getPlayer().setVolume(globalSoundVolume * volume * typeVolume);
        }
    }

    private void setPlayerVolume(MediaPlayer player,double volume, int audioType){
        double typeVolume = AudioTypeVolumes.get(audioType);;
        player.setVolume(globalSoundVolume * typeVolume * volume);
    }

    /**
     * Plays the sound. After ending playing it disposes and removes it from sounds list
     * @param sound sound to play
     */
    public void playSound(Audio sound){
        MediaPlayer player = sound.getPlayer();
        Sounds.add(sound);
        putNewAudioType(sound.getAudioType());
        setPlayerVolume(player, sound.getVolume(), sound.getAudioType());

        player.play();
        player.setOnEndOfMedia(() -> {
            Sounds.remove(sound);
            player.dispose();
        });
    }

    /**
     * Plays the sound in loop. In order to dispose the sound you must manually call stop() method of MediaPlayer in Audio class.
     * @param sound sound to play in loop
     */
    public void playInLoop(Audio sound){
        MediaPlayer player = sound.getPlayer();
        Sounds.add(sound);
        putNewAudioType(sound.getAudioType());
        setPlayerVolume(player, sound.getVolume(), sound.getAudioType());

        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
        player.setOnStopped(() -> {
            Sounds.remove(sound);
            player.dispose();
        });
    }

    /**
     * Sets volume multiplier of given audio type
     * @param type audio type
     * @param volume volume
     */
    public void setAudioTypeVolume(int type, double volume){
        if(volume < 0) return;
        AudioTypeVolumes.put(type, volume);
        onVolumeChanged();
    }

    /**
     * @param type audio type
     * @return volume multiplier of given audio type
     */
    public double getAudioTypeVolume(int type){
        return AudioTypeVolumes.get(type);
    }

    /**
     * @return gets global multiplier of volume of all sounds
     */
    public double getGlobalVolume(){
        return globalSoundVolume;
    }

    /**
     * Sets global volume multiplier for all sounds
     * @param globalVolume volume to be set
     */
    public void setGlobalVolume(double globalVolume){
        this.globalSoundVolume = globalVolume;
        onVolumeChanged();
    }

    /**
     * Tries to find audio of given name in actually playing sounds<br/>
     * In order to find the audio it must have set helperName property<br/>
     * If helper name is empty string method will return first audio file without helperName<br/>
     * @param helperName helper name of audio added
     * @return Audio if found, null if not
     */
    public Audio getAudioByName(String helperName){
        for (Audio audio : Sounds) {
            if(audio.getHelperName().equals(helperName))
                return audio;
        }
        return null;
    }
}

//REFERENCE: https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundFX {
    private static final int BUFFER_SIZE = 4096;

    void play(String pFilePath) {
        File audioFile = new File(pFilePath);

        try {
            AudioInputStream aStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat aFormat = aStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aFormat);
            SourceDataLine aLine = (SourceDataLine) AudioSystem.getLine(info);
            aLine.open(aFormat);
            aLine.start();
            System.out.println(pFilePath + "Has been played");

            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = aStream.read(bytesBuffer)) != 1) {
                aLine.write(bytesBuffer, 0, bytesRead);
            }

            aLine.drain();
            aLine.close();
            aStream.close();
        }

        catch(UnsupportedAudioFileException e) {
            System.err.println("The audio file is not supported");
        }
        catch(LineUnavailableException e) {
            System.err.println("Audio line for playing back is unavailable");
        }
        catch(IOException e) {
            System.err.println("Error playing the audio file");
        }
    }
}

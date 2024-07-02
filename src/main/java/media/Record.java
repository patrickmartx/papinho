package media;

import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

public class Record{
    File wavFile;
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;
    public String lastFileName;


    public Record () {

    }

        private AudioFormat getAudioFormat(){
            float sampleRate = 16000;
            int sampleSizeInBits = 8;
            int channels = 2;
            AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,channels, true, true);
            return format;
        }
    protected void start() {
        try {
            String time = new Date().toString();
            time = time.replace(":", "-");

            lastFileName = "./audio/" + "ENVIADO - " + time + ".wav";
            
            wavFile = new File(lastFileName);
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Começando a capiturar...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Começando a gravar...");
            
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {

            ioe.printStackTrace();
        }
        return;
    }
    public void finish() {
        line.stop();
        line.close();
        System.out.println("Finalizado");
    }


}

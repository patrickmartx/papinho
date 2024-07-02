package Dominio;
import media.Player;
import media.Record;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import  java.io.File;
import java.net.*;

public class mainClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8081);
        byte[] byte_array = new byte[45806];
        InputStream is  = socket.getInputStream();


        FileOutputStream fos = new FileOutputStream("./audio/arquivo2.wav");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = is.read(byte_array, 0, byte_array.length);

        bos.write(byte_array,0,bytesRead);
        bos.close();
        socket.close();

        Player player =  new Player(new Record());
        player.fileName = "./audio/arquivo2.wav";
        player.playSound();

    }
}
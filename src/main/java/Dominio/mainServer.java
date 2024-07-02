package Dominio;

import interface_grafica.InterfaceGrafica;

import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mainServer {
    private static String filePath = "./audio/arquivo.wav";



    public static void main(String[] args)   {
        try {
            ServerSocket serve = new ServerSocket(8081);
            File file = new File(filePath);
            
            while (true){
                Socket socket = serve.accept();
                byte[] byte_array = new byte[(int)file.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(byte_array, 0 ,byte_array.length);
                OutputStream os = socket.getOutputStream();
                os.write(byte_array, 0,byte_array.length);
                os.flush();
                socket.close();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
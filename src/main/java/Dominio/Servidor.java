package Dominio;


import media.Player;
import media.Record;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Servidor{
    int PortaTCP;
    int idServidor;
    ServerSocket server;
    Socket socket;
    public boolean estaOuvindo = false;
    private PrintStream saidaText;
    private BufferedInputStream entradaAudio;
    Scanner entradaText;
    public Servidor(int idServidor) {
        this.idServidor = idServidor;
    }

    public void identificacao(){
        System.out.println(">>>>>>> Servidor " + idServidor);
    }

    public void alocaPorta(int porta) throws Exception {
        if(porta != 0) PortaTCP = porta;
        try {
            server = new ServerSocket(PortaTCP);
        }
        catch (Exception e){
            System.out.println("Porta já utilizada");
            throw e;
        }
    }

    public void enviarArquivo(Player player){
        try {
            File file =  new File(player.fileName);
            int tamanho = (int) file.length();
            this.enviaMessagem("0"+tamanho);
            byte[] byte_array = new byte[tamanho];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(byte_array, 0 ,byte_array.length);
            OutputStream os = socket.getOutputStream();
            os.write(byte_array, 0,byte_array.length);
            os.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
    public Player recebeArquivo(String messagem){
        try{
            int tamanho = Integer.parseInt(messagem);
            byte[] byte_array = new byte[tamanho];
            InputStream is = socket.getInputStream();
            String time = new Date().toString();
            time = time.replace(":", "-");
            String fileName = "./audio/RECEBIDO - "+time+".wav";
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(byte_array, 0, byte_array.length);
            bos.write(byte_array,0,bytesRead);
            bos.close();
            Player p = new Player(new Record());
            p.fileName = fileName;
            return p;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public void esperaConectar() {
        try {
            socket = server.accept();
            estaOuvindo = true;

            entradaAudio = new BufferedInputStream(socket.getInputStream());
            entradaText = new Scanner(socket.getInputStream());
            saidaText = new PrintStream(socket.getOutputStream());
        }
        catch (Exception e){
            System.out.println("erro a conectar");
        }
    }

    public void desalocaPorta(){
        try {
            server.close();
        }
        catch (Exception e){
            System.out.println("Não foi possível fechar o servidor");
        }
    }

    public String proximaMessagem(){
        if (entradaText.hasNextLine())
            return entradaText.nextLine();
        else
            return "";
    }

    public void enviaMessagem(String messagem){
        if (messagem == "")
            return;
        saidaText.println(messagem);
    }
    public void receberAudio(){
        try {
            

            BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
            AudioInputStream ais = AudioSystem.getAudioInputStream(inputStream);
            
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("audio/Lucas.wav"));
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void interrompeEscuta(){
        if(socket != null){
            try {
                estaOuvindo = false;
                socket.close();
            } catch (IOException ex) {
                System.out.println("Não foi possível encerrar conexão");
                ex.printStackTrace();
            }
        }
    }

    public boolean socketIsClosed(){
        if(socket == null) return true;
        return socket.isClosed();
    }

    

    public String GetIP(){
        try {
            String ip = InetAddress.getByName("localhost").getHostAddress();
            
            return ip;
        }
        catch (Exception e){
            System.out.println("Não foi possível encontrar o endereço");
        }
        return null;//valor default talvez seja mais iteressante?
    }


}

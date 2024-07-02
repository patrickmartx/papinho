package Dominio;

import media.Player;
import media.Record;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Cliente{
    int idCliente;
    Socket socket;
    private PrintStream saida;
    private Scanner entrada;
    private OutputStream saidaFile;
    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void identificacao(){
        System.out.println(">>>>>>> Cliente " + idCliente);
    }

    public void abreConexao(String host, int porta) throws Exception{
        System.out.println("Tentando abrir conexao com host: " + host +" ba porta: " + porta);
        try{
            socket = new Socket(host, porta);
            saida = new PrintStream(socket.getOutputStream());
            entrada  = new Scanner(socket.getInputStream());
            saidaFile =  socket.getOutputStream();

        }
        catch (Exception e){
            System.out.println("Ocorreu um erro ao abrir a conexão");
            throw e;
        }
        System.out.println("Conexão aberta com sucesso");
    }
    public void enviaMessagem(String messagem){
        if (messagem == "")
            return;
        saida.println(messagem);
    }

    public String proximaMessagem(){
        if (entrada.hasNextLine())
            return entrada.nextLine();
        else
            return "";
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


    public boolean isConnected(){
        if(socket == null) return false ;
        return socket.isConnected();
    }

    public boolean socketIsClosed(){
        if(socket == null) return true;
        return socket.isClosed();
    }


    public void interrompeEscuta(){
        if(socket != null && socket.isConnected()){
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Não foi possível encerrar conexão");
                ex.printStackTrace();
            }
        }
    }
}

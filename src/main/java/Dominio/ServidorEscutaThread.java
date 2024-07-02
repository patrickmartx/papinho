package Dominio;

import interface_grafica.InterfaceGrafica;
import media.Player;
import messagem.AudioMessagem;
import messagem.MessagemGeral;
import messagem.TextMessagem;

public class ServidorEscutaThread implements  Runnable{
    private Servidor servidor;
    private InterfaceGrafica interfaceGrafica;
    private boolean running = true;
    private boolean interrupt = false;
    public ServidorEscutaThread(Servidor s, InterfaceGrafica i){
        servidor = s;
        interfaceGrafica = i;
    }
    @Override
    public void run() {
        System.out.println("Iniciando thread de escuta do " + interfaceGrafica.title + ", id = " + toString());
        servidor.esperaConectar();
        while (running){
            String messagemRecebida = servidor.proximaMessagem();
            if (messagemRecebida.startsWith("0")){
                Player p = servidor.recebeArquivo(messagemRecebida);
                AudioMessagem audioMessagem = new AudioMessagem(p);
                interfaceGrafica.getMessagemSessao().novoMessagem(audioMessagem,true);
            }
            else if (messagemRecebida.equals("#!#!#!#!#!Encerrando#!#!#!#!#!")){
                running = false;
                try {
                    interfaceGrafica.fechaConexao();
                    interfaceGrafica.abreConexao();
                    interfaceGrafica.verificaBotao();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (!messagemRecebida.equals("")) {
                MessagemGeral m = new TextMessagem(messagemRecebida);
                interfaceGrafica.getMessagemSessao().novoMessagem(m, true);
                interrupt = false;
            }
            else{
                if(interrupt){
                    try {
                        interfaceGrafica.fechaConexao();
                        interfaceGrafica.verificaBotao();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else interrupt = true;
            }
        }
        System.out.println("Encerrando thread do " + interfaceGrafica.title + ", id = " + toString());
    }


    public void finish(){
        running = false;
    }
}

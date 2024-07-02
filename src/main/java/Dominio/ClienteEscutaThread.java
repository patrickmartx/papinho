package Dominio;

import interface_grafica.InterfaceGrafica;
import interface_grafica.InterfaceGraficaCliente;
import media.Player;
import messagem.AudioMessagem;
import messagem.MessagemGeral;
import messagem.TextMessagem;

import java.util.concurrent.ExecutionException;

public class ClienteEscutaThread implements Runnable{
    public boolean running = true;
    private Cliente cliente;
    private boolean interrupt = false;
    private InterfaceGrafica interfaceGrafica;
    public ClienteEscutaThread(Cliente c, InterfaceGrafica i){
        cliente = c;
        interfaceGrafica =  i;
    }


    @Override
    public void run() {
        System.out.println("Iniciando thread do " + interfaceGrafica.title + ", id = " + toString());
        while (running){
            String messagemRecebida = cliente.proximaMessagem();
            if (messagemRecebida.startsWith("0")){
                Player p = cliente.recebeArquivo(messagemRecebida);
                AudioMessagem audioMessagem = new AudioMessagem(p);
                interfaceGrafica.getMessagemSessao().novoMessagem(audioMessagem,true);
            }
            else if (messagemRecebida.equals("#!#!#!#!#!Encerrando#!#!#!#!#!")){
                running = false;
                try {
                    interfaceGrafica.fechaConexao();
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

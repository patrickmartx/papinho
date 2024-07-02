package interface_grafica;

import Dominio.Servidor;
import Dominio.ServidorEscutaThread;
import media.Player;

public class InterfaceGraficaServidor extends InterfaceGrafica {
    Servidor servidor;
    ServidorEscutaThread servidorThread;
    Thread thread;

    public InterfaceGraficaServidor(String title, int id){
        super(title);
        servidor = new Servidor(id);
    }

    @Override
    public void abreConexao() throws Exception {
        try {

            System.out.println(conexcaoSessao.getPortaSevidor());
            servidor.alocaPorta(Integer.parseInt(conexcaoSessao.getPortaSevidor()));

            servidorThread =  new ServidorEscutaThread(servidor, this);
            thread = new Thread(servidorThread);
            thread.start();

            conexaoAberta = true;

        }
        catch (Exception e){
            System.out.println("Ocorreu um erro, verifique se o campo possui apenas números inteiros");
            throw e;
        }
    }

    @Override
    public void novaMessagem(String text, boolean ehDireita){
        super.novaMessagem(text, ehDireita);
        servidor.enviaMessagem( title + ": " + text);
    }

    @Override
    public  void  novaMessagem(Player player, boolean ehDireita){
        super.novaMessagem(player,ehDireita);
        servidor.enviarArquivo(player);
    }

    @Override
    public void fechaConexao() throws Exception {
        try {
            if(servidor.estaOuvindo){
                super.novaMessagem("!!!CONEXÃO ENCERRADA!!!", false);
                servidor.enviaMessagem("#!#!#!#!#!Encerrando#!#!#!#!#!");

            }
            if(thread!=null && thread.isAlive()){
                servidorThread.finish();
                thread.interrupt();
            }
            servidor.interrompeEscuta();
            servidor.desalocaPorta();
            conexaoAberta = false;

        }
        catch (Exception e){
            System.out.println("Ocorreu um erro, verifique se o campo possui apenas números inteiros");
            System.out.println(e.getStackTrace());
            throw e;
        }
    }


    @Override
    public boolean socketIsClosed(){
        return servidor.socketIsClosed();
    }

    @Override
    public void dispose(){
        try {
            fechaConexao();
        } catch (Exception e) {
            e.printStackTrace();
        }
        screen.dispose();
    }
}

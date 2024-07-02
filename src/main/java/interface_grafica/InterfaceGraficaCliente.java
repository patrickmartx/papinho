package interface_grafica;

import Dominio.Cliente;
import Dominio.ClienteEscutaThread;
import media.Player;

public class InterfaceGraficaCliente extends InterfaceGrafica {
    Cliente cliente;
    ClienteEscutaThread clienteThread;
    Thread thread;

    public InterfaceGraficaCliente(String title, int id){
        super(title);
        cliente = new Cliente(id);

    }

    @Override
    public void abreConexao() throws Exception{
        try {
            cliente.abreConexao(conexcaoSessao.getIpSevidor(),Integer.parseInt(conexcaoSessao.getPortaSevidor()));
            clienteThread = new ClienteEscutaThread(cliente, this);
            thread =  new Thread(clienteThread);
            thread.start();

            conexaoAberta = true;
        }
        catch (Exception e){
            System.out.println("Ocorreu um erro, verifique se o segundo campo possui apenas números inteiros");
            throw e;
        }
    }


    @Override
    public void fechaConexao() throws Exception{
        try {
            if(cliente.isConnected()) {
                super.novaMessagem("!!!CONEXÃO ENCERRADA!!!", false);
                cliente.enviaMessagem("#!#!#!#!#!Encerrando#!#!#!#!#!");
            }
            if(thread!=null && thread.isAlive()){
                clienteThread.finish();
                thread.interrupt();
            }
            cliente.interrompeEscuta();
            conexaoAberta = false;
        }
        catch (Exception e){
            System.out.println("Ocorreu um erro, verifique se o campo possui apenas números inteiros");
            throw e;
        }
    }


    @Override
    public void novaMessagem(String text, boolean ehDireita){
        super.novaMessagem(text, ehDireita);
        cliente.enviaMessagem(title + ": " + text);
    }

    @Override
    public void novaMessagem(Player player, boolean ehDireita){
        super.novaMessagem(player,ehDireita);
        cliente.enviarArquivo(player);
    }

    @Override
    public boolean socketIsClosed(){
        return cliente.socketIsClosed();
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

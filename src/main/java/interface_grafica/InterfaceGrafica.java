package interface_grafica;
import media.Player;
import messagem.AudioMessagem;
import messagem.MessagemGeral;
import messagem.TextMessagem;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

abstract public class InterfaceGrafica {
    JFrame screen;
    public String title;
    boolean conexaoAberta = false;
    ConexcaoSessao conexcaoSessao;
    MensagemSessao mensagemSessao;
    SessaoInferior sessaoInferior;



    public InterfaceGrafica(String title){
        System.out.println("Interface iniciada");
        // configuracao da janela
        this.title = title;
        screen = new JFrame(title);
        screen.setResizable(false);
        screen.setLayout(new FlowLayout(FlowLayout.CENTER));
        screen.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mensagemSessao = new MensagemSessao(this);
        conexcaoSessao = new ConexcaoSessao(this);
        sessaoInferior = new SessaoInferior(this);

        screen.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });

        screen.setSize(800,500);

    }

    public void start(){
        screen.setVisible(true);
    }

    public void dispose(){
        screen.dispose();
    }

    public String getIp(){
        return conexcaoSessao.getIpSevidor();
    }

    public String getPorta(){
        return conexcaoSessao.getPortaSevidor();
    }

    public void novaMessagem(String text, boolean ehDireita){
        
        MessagemGeral m;
        if(ehDireita) m = new TextMessagem("você: " + text);
        else m = new TextMessagem(text);
        this.mensagemSessao.novoMessagem(m, ehDireita);
    }
    public void novaMessagem(Player player, boolean ehDireita){
        MessagemGeral m = new AudioMessagem(player);
        this.mensagemSessao.novoMessagem(m, ehDireita);
    };
    public boolean isBotaoDeConexcaoClicado(){
        return conexcaoSessao.isButtonClicked;
    }
    public void botaoDeConexcaoNaoMaisClica(){ conexcaoSessao.isButtonClicked = false; }

    abstract public void abreConexao() throws Exception ;

    abstract public void fechaConexao() throws Exception;

    public boolean socketIsClosed(){
        return false;
    }

    public void verificaBotao(){
        conexcaoSessao.verificaBotao();
    }

    public MensagemSessao getMessagemSessao(){
        return mensagemSessao;
    }
}

package interface_grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import media.PlayerThread;
import media.Record;
import media.RecordThread;
import media.Player;

public class SessaoInferior {
    private JPanel jpanel;
    private JButton botaoGravar;
    private Record record = new Record();
    private Player player = null;
    private JPanel panelMessagem;
    private TextField textAreaMessagem;
    private JButton botaoEnviar;
    private InterfaceGrafica interfaceGrafica;

    //actionListener
    private ActionListener gravarActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            RecordThread tarefa = new RecordThread(record);
            textAreaMessagem.setEnabled(false);
            Thread thread = new Thread(tarefa);
            thread.start();
            botaoGravar.setText("Interromper");
            botaoGravar.removeActionListener(botaoGravar.getActionListeners()[0]);
            botaoGravar.addActionListener(interromperGravacaoActionListener);
        }
    };

    private  ActionListener interromperGravacaoActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            record.finish();
            player = new Player(record);
            setPlayerToPainel();
            jpanel.remove(textAreaMessagem);
            jpanel.add(panelMessagem,0);
            botaoGravar.setText("Cancelar");
            botaoGravar.removeActionListener(botaoGravar.getActionListeners()[0]);
            botaoGravar.addActionListener(cancelarGravacaoActionListener);
        }
    };

    private ActionListener cancelarGravacaoActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            player = null;
            textAreaMessagem.setEnabled(true);
            jpanel.remove(panelMessagem);
            jpanel.add(textAreaMessagem,0);
            botaoGravar.setText("Gravar");
            botaoGravar.removeActionListener(botaoGravar.getActionListeners()[0]);
            botaoGravar.addActionListener(gravarActionListener);
        }
    };

    private  ActionListener playRecordActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton b = (JButton) actionEvent.getSource();

            PlayerThread tarefa = new PlayerThread(player, b, playRecordActionListener);
            Thread thread = new Thread(tarefa);
            thread.start();
            b.removeActionListener(b.getActionListeners()[0]);
            b.addActionListener(pararPlayActionListener);
            b.setText("Parar");
        }
    };

    private ActionListener pararPlayActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            player.finish();
        }
    };

    public SessaoInferior(final InterfaceGrafica interfaceGrafica){
        //panel inferior
        jpanel = new JPanel();
        jpanel.setPreferredSize(new Dimension(700,150));

        this.interfaceGrafica = interfaceGrafica;
        JFrame screen = interfaceGrafica.screen;
        screen.add(jpanel);

        //panel da gravação
        panelMessagem = new JPanel();
        panelMessagem.setPreferredSize(new Dimension(400,30));

//        jpanel.add(panelMessagem);

        //TextField
        textAreaMessagem = new TextField();
        textAreaMessagem.setPreferredSize(new Dimension(400,30));
        panelMessagem.add(textAreaMessagem);
        jpanel.add(textAreaMessagem);


        //botão de gravação
        botaoGravar = new JButton("Gravar");
        botaoGravar.addActionListener(gravarActionListener);
        jpanel.add(botaoGravar);

        //botão de enviar
        botaoEnviar = new JButton("Enviar");
        final InterfaceGrafica finalInterfaceGrafica = interfaceGrafica;
        botaoEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!interfaceGrafica.conexaoAberta || interfaceGrafica.socketIsClosed()){
                    botaoEnviar.setBackground(Color.red);
                }
                else{
                    botaoEnviar.setBackground(Color.white);
                    if (player != null){
                        finalInterfaceGrafica.novaMessagem(player,true);
                        player = null;
                        textAreaMessagem.setEnabled(true);
                        jpanel.remove(panelMessagem);
                        jpanel.add(textAreaMessagem,0);
                        botaoGravar.setText("Gravar");
                        botaoGravar.removeActionListener(botaoGravar.getActionListeners()[0]);
                        botaoGravar.addActionListener(gravarActionListener);

                    }
                    else if (textAreaMessagem.getText().length() > 0 ){
                        
                        finalInterfaceGrafica.novaMessagem(textAreaMessagem.getText(), true);
                        textAreaMessagem.setText("");
                    }
                    else{
                        System.out.println("Não Enviar");
                    }
                }
            }
        });
        jpanel.add(botaoEnviar);

    }

    private void setPlayerToPainel(){
        String fileName = player.fileName.replace("./audio/","");
        panelMessagem.removeAll();
        JButton b = new JButton("Play");
        b.addActionListener(playRecordActionListener);
        panelMessagem.add(b);
        JLabel l = new JLabel(fileName);
        panelMessagem.add(l);

    }
}



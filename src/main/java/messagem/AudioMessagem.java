package messagem;

import media.Player;
import media.PlayerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AudioMessagem extends MessagemGeral {

    private ActionListener playRecordActionListener = new ActionListener() {
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


    private Player player;
    public AudioMessagem(Player p){
        super();
        player = p;
        JButton b = new JButton("Play");
        b.addActionListener(playRecordActionListener);
        panel.add(b);
        JLabel l = new JLabel(player.fileName.replace("./audio/",""));
        panel.add(l);
    }
    @Override
    void setPanelConteudo() {

    }
}

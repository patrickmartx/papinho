package media;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PlayerThread implements Runnable {
    private Player player;
    private JButton b;
    private ActionListener a;
    public PlayerThread(Player player, JButton b, ActionListener a){
        this.player = player;
        this.b = b;
        this.a = a;
    }
    @Override
    public void run() {
        player.playSound();
        b.removeActionListener(b.getActionListeners()[0]);
        b.addActionListener(a);
        b.setText("Play");
    }
}

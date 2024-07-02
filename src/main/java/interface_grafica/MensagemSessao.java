package interface_grafica;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

import messagem.*;

public class MensagemSessao {
    private JPanel jpanel;
    private ArrayList<JPanel> messagens= new ArrayList<JPanel>();
    private InterfaceGrafica interfaceGrafica;
    private JPanel list;
    private JScrollPane scrollPane;
    public MensagemSessao(InterfaceGrafica interfaceGrafica) {

        jpanel = new JPanel();
        jpanel.setPreferredSize(new Dimension(475,350));

        this.interfaceGrafica = interfaceGrafica;
        JFrame screen = interfaceGrafica.screen;

        screen.add(jpanel);

        list = new JPanel();
        list.setLayout(new BoxLayout(list,BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(475,350));
        jpanel.add(scrollPane);



    }
    public void novoMessagem(MessagemGeral messagemGeral,boolean isDireita){
        
        
        list.add(messagemGeral.getComponent(), JComponent.RIGHT_ALIGNMENT);
        scrollPane.updateUI();


    }
}

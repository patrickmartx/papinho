package messagem;


import javax.swing.*;
import java.awt.*;

public abstract class MessagemGeral {
    protected String dono;
    protected String isDireita;
    protected JPanel panel;

    public MessagemGeral(){

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(70,40));
        
        panel.setBackground(Color.white);

    }


    public JPanel getComponent(){
        return  this.panel;
    }




    abstract void setPanelConteudo();
}

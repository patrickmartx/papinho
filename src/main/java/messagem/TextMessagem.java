package messagem;

import javax.swing.*;
//

public class TextMessagem extends MessagemGeral{
    private JLabel textField;

    public TextMessagem(String text){
        super();
        this.textField = new JLabel(text);
        setPanelConteudo();
    }


    @Override
    void setPanelConteudo() {
        super.panel.add(this.textField);

    }
}

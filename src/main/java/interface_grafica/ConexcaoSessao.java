package interface_grafica;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConexcaoSessao {
    private JTextField ipSevidorField;
    private JTextField portaSevidorField;
    private JPanel panel;
    private final InterfaceGrafica interfaceGrafica;
    private JButton button;
    protected boolean isButtonClicked;

    public ConexcaoSessao (InterfaceGrafica interfaceGrafica){
        isButtonClicked = false;
        if(interfaceGrafica instanceof InterfaceGraficaCliente) ipSevidorField = new JTextField("localhost",15);
        portaSevidorField = new JTextField("8081",15);

        String text;
        if(interfaceGrafica instanceof InterfaceGraficaCliente) text = "Conectar";
        else text = "Abrir conexão";
            button = new JButton(text);
        this.interfaceGrafica = interfaceGrafica;

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buttonAction();
                isButtonClicked = true;
            }
        });

        panel = new JPanel();
        if(interfaceGrafica instanceof InterfaceGraficaCliente) panel.add(ipSevidorField);
        panel.add(portaSevidorField);
        panel.add(button);
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        interfaceGrafica = interfaceGrafica;
        JFrame screen = interfaceGrafica.screen;
        screen.add(panel);
    }

    public void buttonAction(){
        try {
            if(!interfaceGrafica.conexaoAberta)interfaceGrafica.abreConexao();
            else interfaceGrafica.fechaConexao();

            if(interfaceGrafica.conexaoAberta){
                button.setBackground(Color.green);
                button.setForeground(Color.white);
            }
            else{
                button.setBackground(Color.white);
                button.setForeground(Color.black);
            }
        }
        catch (Exception e){
            button.setBackground(Color.red);
            button.setForeground(Color.white);
        }
    }

    public void verificaBotao(){
        if(interfaceGrafica.conexaoAberta){
            button.setBackground(Color.green);
            button.setForeground(Color.white);
        }
        else{
            button.setBackground(Color.white);
            button.setForeground(Color.black);
        }
    }

    public String getIpSevidor() {
        if(interfaceGrafica instanceof InterfaceGraficaCliente) return ipSevidorField.getText();
        else return "Não disponível";
    }
    public String getPortaSevidor() {
        return portaSevidorField.getText();
    }

}

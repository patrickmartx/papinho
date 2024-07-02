package interface_grafica;

import media.Player;
import messagem.AudioMessagem;
import messagem.MessagemGeral;
import messagem.TextMessagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class InterfaceGraficaMain {
    JFrame screen;
    static List<InterfaceGraficaCliente> interfacesGraficasCliente = new ArrayList<InterfaceGraficaCliente>();
    static List<InterfaceGraficaServidor> interfacesGraficasServidor = new ArrayList<InterfaceGraficaServidor>();
    static int idCliente = 0;
    static int idServidor = 0;

    JPanel jpanel;


    public InterfaceGraficaMain(){
        screen = new JFrame("Controle");
        screen.setResizable(false);
        screen.setLayout(new FlowLayout(FlowLayout.CENTER));
        screen.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        screen.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });
        screen.setSize(500,500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        screen.setLocation(dim.width/2-screen.getSize().width/2, dim.height/2-screen.getSize().height/2);
        JPanel panel = new JPanel();
        screen.add(panel);
        JButton button = new JButton("Abrir cliente");
        panel.add(button);
        button.addActionListener (new Action1());

        JButton button2 = new JButton("Abrir servidor");
        panel.add(button2);
        button2.addActionListener (new Action2());
    }

    static class Action1 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            System.out.println("iniciando nota interface de cliente");
            InterfaceGraficaCliente interfaceGrafica = new InterfaceGraficaCliente("Cliente " + idCliente,idCliente);
            interfaceGrafica.start();
            interfacesGraficasCliente.add(interfaceGrafica);
            idCliente = idCliente + 1;
        }
    }
    static class Action2 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            System.out.println("iniciando nota interface de servidor");
            InterfaceGraficaServidor interfaceGrafica = new InterfaceGraficaServidor("Servidor " + idServidor,idServidor);
            interfaceGrafica.start();
            interfacesGraficasServidor.add(interfaceGrafica);
            idServidor = idServidor + 1;
        }
    }


    public void dispose(){
        for(InterfaceGrafica ui: interfacesGraficasServidor){
            ui.dispose();
        }
        for(InterfaceGrafica ui: interfacesGraficasCliente){
            ui.dispose();
        }
        interfacesGraficasCliente = null;
        interfacesGraficasServidor = null;
        screen.dispose();
    }

    public void open(){
        screen.pack();
        screen.setVisible(true);
    }
}

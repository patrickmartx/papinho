import javax.media.*;

import Dominio.Servidor;
import interface_grafica.InterfaceGrafica;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import interface_grafica.InterfaceGraficaCliente;
import interface_grafica.InterfaceGraficaServidor;
import media.Record;

public class main {
    public static void main(String[] args) {


        List<InterfaceGraficaCliente> interfacesGraficasCliente = new ArrayList<InterfaceGraficaCliente>();
        List<InterfaceGraficaServidor> interfacesGraficasServidor = new ArrayList<InterfaceGraficaServidor>();

        int idCliente = 0;
        int idServidor = 0;

        Scanner teclado = new Scanner(System.in);

        while (true){
            System.out.println("INSIRA:\n1 -> abrir novo cliente\n2 - abrir novo servidor\n-1 -> fechar");
            int teste = teclado.nextInt();
            if(teste == 1){
                System.out.println("iniciando nota interface de cliente");
                InterfaceGraficaCliente interfaceGrafica = new InterfaceGraficaCliente("Cliente " + idCliente,idCliente);
                interfaceGrafica.start();
                interfacesGraficasCliente.add(interfaceGrafica);

                idCliente = idCliente + 1;

            }
            if(teste == 2){
                System.out.println("iniciando nota interface de servidor");
                InterfaceGraficaServidor interfaceGrafica = new InterfaceGraficaServidor("Servidor " + idServidor,idServidor);
                interfaceGrafica.start();
                interfacesGraficasServidor.add(interfaceGrafica);
                idServidor = idServidor + 1;
            }
            if(teste == -1){
                for(InterfaceGrafica ui: interfacesGraficasCliente){
                    ui.dispose();
                }
                for(InterfaceGrafica ui: interfacesGraficasServidor){
                    ui.dispose();
                }
                interfacesGraficasCliente = null;
                interfacesGraficasServidor = null;
                break;
            }
        }

    }
}
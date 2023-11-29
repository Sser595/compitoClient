package com.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    String nome = "127.0.0.1";
    int porta = 1234;
    Socket mioSocket;
    BufferedReader tastiera;
    String primoNumero;
    String secondoNumero;
    String segno;
    String stringaRicevutadalserver;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    public Socket connetti(){
        System.out.println("client partito ");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            mioSocket = new Socket(nome, porta);
            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("host non riconosciuto");
            // TODO: handle exception
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("errore durante la conessione");
            System.exit(1);
        }
        return mioSocket;
    }

    public void comunica(){
        try {
            String comando =null;
            
            do{
                System.out.println("inserisci un numero");
                primoNumero = tastiera.readLine();
                System.out.println("Inserisci il secondo numero");
                secondoNumero = tastiera.readLine();
                System.out.println("inserisci il segno");
                segno = tastiera.readLine();
                outVersoServer.writeBytes(primoNumero+ '\n');
                outVersoServer.writeBytes(secondoNumero+ '\n');
                outVersoServer.writeBytes(segno+ '\n');

                stringaRicevutadalserver= inDalServer.readLine();
                System.out.println("l'operazione ha dato "+ stringaRicevutadalserver);
                System.out.println("vuoi effettuare un altro calcolo? y/n");
                comando = tastiera.readLine();
                outVersoServer.writeBytes(comando+'\n');
            }while(comando.equals("y"));
            
            System.out.println("termina elaborazione e chiude connessione");
            mioSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server");
            System.exit(porta);
        }
    }

 
}
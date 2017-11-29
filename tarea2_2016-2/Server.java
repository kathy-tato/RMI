/**
 * Created by Kathy-Feña on 21-10-16.
 */

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements Inter {

    public Hashtable<String, String> colas_client = new Hashtable<String, String>();
    public Hashtable<String,Queue<String>> colas_server = new Hashtable<String,Queue<String>>(); //nombre_cola: cola_como_valor
    public Hashtable<String,Queue<String>> colas_client_valor = new Hashtable<String,Queue<String>>(); //array_cliente: cola_como_valor

    Server() throws RemoteException{
        String cadena;
        try {

            FileReader f = new FileReader("colas.txt");
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                Queue<String> cola = new LinkedList<String>();
                colas_server.put(cadena, cola);
            }
            b.close();
        }catch (IOException e){
            System.out.println("Error: " + e);
        }
    }

    public void push(String msg, int pid) throws  RemoteException {
        String pid_ip;
        try {
            pid_ip = String.valueOf(pid) + " " + RemoteServer.getClientHost();
            colas_server.get(colas_client.get(pid_ip)).add(msg);
            colas_client_valor.get(pid_ip).add(msg);

        }catch (ServerNotActiveException e){}

    }

    public String pull(int pid) throws RemoteException {
        String pid_ip;
        String mensaje  =null;
        try {
            pid_ip = String.valueOf(pid) + " " + RemoteServer.getClientHost();
            if (colas_client_valor.get(pid_ip).poll() != null)
                mensaje = colas_client_valor.get(pid_ip).peek();;
        }catch (ServerNotActiveException e){}
        if (mensaje != null)
            return mensaje;
        return "No quedan mensajes en la cola";
    }

    public String lista_colas() throws RemoteException{
        Enumeration<String> e = colas_server.keys();
        String colas = "";
        while(e.hasMoreElements()) {
            colas = colas + e.nextElement() + "\n";
        }
        return colas;
    }

    public void suscription(String q, int pid) throws RemoteException {
        String pid_ip;
        try {
            pid_ip = String.valueOf(pid) + " " +RemoteServer.getClientHost();
            colas_client.put(pid_ip, q);
            Queue<String> cola = new LinkedList<String>();
            cola = colas_server.get(q);

            colas_client_valor.put(pid_ip, cola);
        }catch (ServerNotActiveException e){}
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        System.out.println("[Servidor] Iniciando...");
        try {
            Inter srv = new Server();
            Naming.rebind("rmi://localhost:" + args[0] + "/SimpleMoM", srv);
            System.out.println("[Servidor] Listo!");
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Excepcion en Servidor:");
            e.printStackTrace();
            System.exit(1);
        }
    }

}

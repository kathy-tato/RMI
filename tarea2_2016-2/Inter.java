/**
 * Created by Kathy-Feña on 21-10-16.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Inter extends Remote {
    void push(String msg, int pid) throws RemoteException;
    String pull(int pid) throws RemoteException;
    void suscription(String q, int pid) throws RemoteException;
    String lista_colas() throws RemoteException;
}
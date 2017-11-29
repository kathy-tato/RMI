package engine;

import compute.Compute;
import compute.Task;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine extends UnicastRemoteObject implements Compute {

    public ComputeEngine() throws RemoteException {
        super(); // same as super(0);
    }

    @Override
    public <T> T executeTask(Task<T> task) throws RemoteException {
        System.out.println("remote call!");
        return task.execute();
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Compute engine = new ComputeEngine();
            Naming.rebind("Compute", engine);
            System.out.println("Engine bound.");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

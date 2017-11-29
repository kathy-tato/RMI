package client;

import computations.Pi;
import compute.Compute;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ComputePi {
    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Compute comp = (Compute) Naming.lookup("Compute");
            Pi task = new Pi(42);
            BigDecimal pi = comp.executeTask(task);
            System.out.println("Pi: " + pi);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}

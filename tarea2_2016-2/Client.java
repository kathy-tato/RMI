/**
 * Created by Kathy-Feña on 21-10-16.
 */

import java.lang.management.ManagementFactory;
import java.rmi.*;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws RemoteException {
        int opcion, pid;
        String mensaje, elegida;
        Scanner sc = new Scanner(System.in);

        System.out.println("[Cliente] Iniciando...");

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Inter srv = (Inter) Naming.lookup("//" + args[0] + ":" + args[1] + "/SimpleMoM");
            pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            System.out.println("[Cliente] Elija cola a suscribir:");
            System.out.println(srv.lista_colas());

            elegida = sc.nextLine();
            srv.suscription(elegida, pid);

            do{

                System.out.println("[Cliente] Seleccionar opcion");
                System.out.println("[Cliente] [1] Enviar mensaje");
                System.out.println("[Cliente] [2] Recibir mensaje");
                System.out.println("[Cliente] [3] Suscribir a otra cola");
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion){
                    case 1:
                        System.out.print("\n[Cliente] Escribir mensaje a enviar: \n>");
                        mensaje = sc.nextLine();
                        srv.push(mensaje, pid);
                        break;
                    case 2:
                        System.out.println("[Cliente] Mensaje recibido:");
                        System.out.println(srv.pull(pid));
                        break;
                    case 3:
                        System.out.println("[Cliente] Elija cola a suscribir:");
                        System.out.println(srv.lista_colas());
                        elegida = sc.nextLine();
                        srv.suscription(elegida, pid);
                        break;
                    default:
                        System.out.println("Opcion Incorrecta!");
                        break;
                }

            }while(opcion != 4);

        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en Cliente:");
            e.printStackTrace();
        }
    }
}

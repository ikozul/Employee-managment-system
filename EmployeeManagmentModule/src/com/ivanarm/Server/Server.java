/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Server;


import com.ivanarm.Model.ChatInterface;
import com.ivanarm.Model.XmlJndiHelpers.Jndi;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;

/**
 *
 * @author Ivan Kožul
 */
public class Server extends UnicastRemoteObject implements ChatInterface, Serializable, Runnable {

    public static List<String> messages = new ArrayList<>();

    public Server() throws RemoteException {
        super();
    }

    @Override
    public synchronized void sendMessage(String message) throws RemoteException {
        if (message == null || "".equals(message)) {

        } else {
            messages.add(message);
        }

    }

    @Override
    public synchronized List<String> getAllMessages() throws RemoteException {
        messages.forEach((m) -> {
            System.out.println(m);
        });
        return messages;
    }
    
        @Override
    public void setAllMessages(List<String> lista) throws RemoteException {
        messages.clear();
        messages.addAll(lista);
    }

    public final static UserRepo users = new UserRepo();

    public static int PORT_RMI = 0;
    public static int PORT_SOCKET = 0;

    public static void main(String args[]) throws IOException, RemoteException, AlreadyBoundException {

        Jndi jndi = new Jndi();
        
        System.out.println("jndi");
        System.out.println(PORT_RMI);
        System.out.println(PORT_SOCKET);
        
        messages.add("pero: Radim sutra.");
        messages.add("admin: Kada dolazis sutra");
        
        System.err.println("Adding Users");

        users.getUsers();
        System.err.println("Chat server ready");

        Server obj = new Server();

        ChatInterface value = (ChatInterface) obj;

        Registry registry = LocateRegistry.createRegistry(10001);
        registry.bind("Messages", value);

        System.err.println("Server ready");

        obj.run();
    }

    private static void startServer() {

        for (;;) {
            try (ServerSocket ss = new ServerSocket(PORT_SOCKET);
                    Socket sck = ss.accept();
                    ObjectOutputStream str
                    = new ObjectOutputStream(sck.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(sck.getInputStream())) {

                str.writeObject(UserRepo.users);
                User user = (User) in.readObject();

                if (UserRepo.users.contains(user)) {
                    users.UpdateUser(user);
                } else {
                    users.AddUser(user);
                }

                System.out.println(user.toString());

            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void run() {
        startServer();
    }


}

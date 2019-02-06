/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm;

import com.ivanarm.Model.AdminLockControlThread;
import com.ivanarm.Model.ChatInterface;
import com.ivanarm.Model.SynchronizedCounter;
import com.ivanarm.Server.Server;
import com.ivanarm.Server.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.rmi.registry.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

/**
 *
 * @author Ivan Kožul
 */
public class EmployeeManagmentModule extends Application {
    public static AdminLockControlThread adminLock;
    public static Registry registry;
    public static ChatInterface value;
    public static User CurrentUser = new User();
    public static ArrayList<User> UserRepo = new ArrayList<>();
    //public static AtomicBoolean flag = new AtomicBoolean();
    //private static final String DESKTOP = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    //public static final String SERIALIZATION_PATH = DESKTOP + "\\Users.ser";
    //private static final File DOCUMENTATION_PATH = new File(DESKTOP + "\\Dokumentacija.html");
    

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("EmployeeManagmentLoginFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException, NotBoundException {
        //Filling dates for demo purposes
        //String host = (args.length < 1) ? "localhost" : args[0];  
        registry = LocateRegistry.getRegistry("localhost", 10001);
        value = (ChatInterface) registry.lookup("Messages");
        System.out.println("Test");
        startClient(null);
        launch(args);

    }
    public static void startClient(User user) {

        try (Socket sck = new Socket("localhost", 10000);
                ObjectInputStream ois
                = new ObjectInputStream(sck.getInputStream());
                ObjectOutputStream str
                = new ObjectOutputStream(sck.getOutputStream()
                )) {
            UserRepo.clear();
            UserRepo.addAll((ArrayList<User>) ois.readObject());

            if (user != null) {

                str.writeObject(user);
            }

            System.out.println(UserRepo.get(0).getPassword());

            //   UserRepo.addAll(users);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeManagmentModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeManagmentModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm;

import static com.ivanarm.EmployeeManagmentModule.registry;
import static com.ivanarm.EmployeeManagmentModule.startClient;
import com.ivanarm.Model.AdminLockControlThread;
import com.ivanarm.Model.ChatRecieveThread;
import com.ivanarm.Model.ChatSendThread;

import com.ivanarm.Model.ClockThread;
import com.ivanarm.Model.UserLockControlThread;
import com.ivanarm.Model.Utils;
import static com.ivanarm.Model.Utils.setLabelColor;
import com.ivanarm.Server.User;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ivan Kožul
 */
public class EmployeeSideFXMLController implements Initializable, Runnable {

    private final User CurrentUser = EmployeeManagmentModule.CurrentUser;
    @FXML
    public Button signIn;
    @FXML
    public Button signOut;
    @FXML
    private Label UsernameLabel;
    @FXML
    private Label CurrentTime;
    @FXML
    private Label timeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private ListView<String> signInListView;

    @FXML
    private TextField chatTextField;
    @FXML
    private ListView<String> chatListView;

    @FXML
    private ListView<String> signOutListView;

    protected ListProperty<String> listProperty = new SimpleListProperty<>();

    private String user;

    public UserLockControlThread userLock;

    ChatSendThread sendThreadUser;
    ChatRecieveThread recieveThreadUser;

    @FXML
    public void sendMessage(ActionEvent event) throws RemoteException, NotBoundException {

        EmployeeManagmentModule.value.sendMessage(user + ": " + chatTextField.getText());
        
        chatTextField.clear();
        
        chatListView.getItems().clear();
        
        EmployeeManagmentModule.value.getAllMessages().forEach(x -> chatListView.getItems().add(x));
        
        
    }

    @FXML
    public void pritisakGumbaPrijave(ActionEvent event) {
        if (!CurrentUser.isDidSignin()) {
            CurrentUser.getWorkingTime().setSignIn(LocalDateTime.now());
            //System.out.println(new Date().toString());            
            Utils.fillListViews(signInListView, signOutListView, CurrentUser);
            CurrentUser.setDidSignin(true);
            setLabelColor(statusLabel, CurrentUser);
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška pri prijavi!");
            alert.setHeaderText("Korisnik je već prijavljen na radno mjesto!");
            alert.showAndWait();
        }
    }

    @FXML
    public void pritisakGumbaOdjave(ActionEvent event) {
        if (CurrentUser.isDidSignin()) {

            CurrentUser.getWorkingTime().setSignOut(LocalDateTime.now());
            //System.out.println(new Date().toString());
            CurrentUser.setDidSignin(false);
            Utils.fillListViews(signInListView, signOutListView, CurrentUser);
            setLabelColor(statusLabel, CurrentUser);

            //System.out.println(EmployeeManagmentLoginFXMLController.CurrentUser.getSignIn());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška pri prijavi!");
            alert.setHeaderText("Korisnik se nije prijavio na radno mjesto!");
            alert.showAndWait();
        }
    }

    @FXML
    public void pritisakGumbaSignOut(ActionEvent event) {
        Parent root;
        try {
            startClient(CurrentUser);
            root = FXMLLoader.load(getClass().getResource("EmployeeManagmentLoginFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            Logger.getLogger(EmployeeSideFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        sendThreadUser = new ChatSendThread();
        recieveThreadUser = new ChatRecieveThread(chatListView);
        recieveThreadUser.start();

        user = CurrentUser.getUsername();
        Timer timer = new Timer();
        ClockThread ClockThread = new ClockThread(timeLabel);
        timer.scheduleAtFixedRate(ClockThread, 1000, 1000);
        Utils.fillListViews(signInListView, signOutListView, CurrentUser);
        UsernameLabel.setText(CurrentUser.getUsername());
        setLabelColor(statusLabel, CurrentUser);
        if (chatListView.getItems() != null) {
            chatListView.getItems().clear();
        }
        try {
            EmployeeManagmentModule.value.getAllMessages().forEach(x -> chatListView.getItems().add(x));
        } catch (RemoteException ex) {
            Logger.getLogger(AdminViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        userLock = new UserLockControlThread(signIn, signOut);
        userLock.start();
    }
    
    @Override
    public void run() {
        while(true){
            sendThreadUser.start();
        }
    }

}

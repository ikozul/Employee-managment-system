/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm;

import com.ivanarm.Model.AdminLockControlThread;
import com.ivanarm.Model.ChatRecieveThread;
import com.ivanarm.Model.ChatSendThread;
import com.ivanarm.Model.ClockThread;
import com.ivanarm.Server.User;
import com.ivanarm.Model.Utils;
import com.ivanarm.Model.XmlJndiHelpers.XmlHandler;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ivan Kožul
 */
public class AdminViewFXMLController implements Initializable, Runnable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button exportChatLog;
    @FXML
    private Button importChatLog;
    @FXML
    private TextField chatTextField;
    @FXML
    private ListView<String> chatListView;
    @FXML
    private Label statusLabel;
    @FXML
    private Label timeLabel;
    @FXML
    public ComboBox<User> userNameComboBox;
    @FXML
    private ListView<String> signInListView;
    @FXML
    private ListView<String> signOutListView;
    @FXML
    private ListView timeListBox;
    @FXML
    private Button signOutButton;

    private String user;
    ChatSendThread sendThread;
    ChatRecieveThread recieveThread;

    @FXML
    public void odaberiKorisnikaSelectionChanged(ActionEvent event) {

        AdminLockControlThread adminLock = new AdminLockControlThread();
        adminLock.start();

        Utils.fillListViews(signInListView, signOutListView, timeListBox, userNameComboBox.getSelectionModel().getSelectedItem());
        Utils.setLabelColor(statusLabel, userNameComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void pritisakGumbaSignOut(ActionEvent event) {
        Parent root;
        try {
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

    @FXML
    public void exportXmlButtonClick(ActionEvent event) {
        List<String> exportList = chatListView.getItems();
        XmlHandler.ExportXml(exportList);
    }

    @FXML
    public void importXmlButtonClick(ActionEvent event) throws RemoteException {
        List<String> lista = XmlHandler.ImportXml();

        EmployeeManagmentModule.value.setAllMessages(lista);
        chatTextField.clear();
        chatListView.getItems().clear();
        EmployeeManagmentModule.value.getAllMessages().forEach(x -> chatListView.getItems().add(x));
    }

    @FXML
    public void pritisakGumbaRegister(ActionEvent event) {
        Utils.updateComboBox(userNameComboBox);
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("RegisterUserFXML.fxml"));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            Logger.getLogger(EmployeeSideFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void sendMessage(ActionEvent event) throws RemoteException, NotBoundException {

        EmployeeManagmentModule.value.sendMessage(user + ": " + chatTextField.getText());
        chatTextField.clear();
        chatListView.getItems().clear();
        EmployeeManagmentModule.value.getAllMessages().forEach(x -> chatListView.getItems().add(x));

    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        sendThread = new ChatSendThread();
        recieveThread = new ChatRecieveThread(chatListView);
        recieveThread.start();
        user = "admin";

        userNameComboBox.getItems().removeAll();
        Timer timer = new Timer();
        ClockThread satNit = new ClockThread(timeLabel);
        timer.scheduleAtFixedRate(satNit, 1000, 1000);
        Utils.updateComboBox(userNameComboBox);

        chatListView.getItems().clear();
        try {
            EmployeeManagmentModule.value.getAllMessages().forEach(x -> chatListView.getItems().add(x));
        } catch (RemoteException ex) {
            Logger.getLogger(AdminViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (true) {

            sendThread.start();

        }
    }

}

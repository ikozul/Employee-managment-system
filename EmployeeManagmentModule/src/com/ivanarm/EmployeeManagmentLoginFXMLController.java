/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.ivanarm.Server.User;

/**
 * FXML Controller class
 *
 * @author Ivan Kožul
 */
public class EmployeeManagmentLoginFXMLController implements Initializable {

    private static final String ADMIN_VIEW = "AdminViewFXML.fxml";
    private static final String EMPLOYEE_VIEW = "EmployeeSideFXML.fxml";

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;
    
    @FXML
    private void onClickLogin(ActionEvent event) {        
        
        usernameTextField.getStyleClass().remove("error");
        passwordPasswordField.getStyleClass().remove("error");

        StringBuilder builder = new StringBuilder();
        boolean hasErrors = false;

        if (usernameTextField.getText().isEmpty()) {
            builder.append("Niste unijeli korisničko ime!\n");
            hasErrors = true;
            usernameTextField.getStyleClass().add("error");
        }

        if (passwordPasswordField.getText().isEmpty()) {
            builder.append("Niste unijeli lozinku!\n");
            hasErrors = true;
            passwordPasswordField.getStyleClass().add("error");
        }

        if (EmployeeManagmentModule.UserRepo.isEmpty()) {
            hasErrors = true;
            builder.append("Nema nijednog registriranog korisnika, molimo kreirajte barem jednog korisnika!");
        }

        boolean loginSucess = false;

        if (usernameTextField.getText().isEmpty() == false && passwordPasswordField.getText().isEmpty() == false) {
            for (User user : EmployeeManagmentModule.UserRepo) {
                if (!user.getUsername().equals(usernameTextField.getText())
                        || !user.getPassword().equals(passwordPasswordField.getText())) {

                    loginSucess = false;
                } else {
                    loginSucess = true;
                    EmployeeManagmentModule.CurrentUser = user;

                    break;
                }
            }
        }

        if (loginSucess == false) {
            builder.append("Unijeli ste neispravnog korisnika i/ili lozinku!");
            hasErrors = true;
        }

        if (hasErrors) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Pogreška kod unošenja podataka!");
            alert.setHeaderText("Neispravni podaci!");
            alert.setContentText(builder.toString());

            alert.showAndWait();
        } else {

            Parent root;
            try {
                if (EmployeeManagmentModule.CurrentUser.IsAdmin()) {
                    root = FXMLLoader.load(getClass().getResource(ADMIN_VIEW));

                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    root = FXMLLoader.load(getClass().getResource(EMPLOYEE_VIEW));
                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }

                //((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException ex) {
                Logger.getLogger(EmployeeManagmentLoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //EmployeeManagmentModule.UserRepo.add(new User("ante", "ante", false));
        //EmployeeManagmentModule.UserRepo.add(new User("miro", "miro", false));
    }

}

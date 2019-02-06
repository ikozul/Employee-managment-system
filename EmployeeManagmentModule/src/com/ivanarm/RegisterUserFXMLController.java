/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm;


import static com.ivanarm.EmployeeManagmentModule.startClient;
import com.ivanarm.Server.Serialization;
import com.ivanarm.Server.User;
import com.ivanarm.Model.Utils;
import com.ivanarm.Server.WorkingTime;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ivan Kožul
 */
public class RegisterUserFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private PasswordField repeatedPasswordPasswordField;

    @FXML
    private CheckBox isAdminCheckBox;

    @FXML
    private void ClickRegisterButton(ActionEvent event) throws RemoteException {

        WorkingTime tempWk = new WorkingTime(new ArrayList<>(), new ArrayList<>());
        if (passwordPasswordField.getText().equals(repeatedPasswordPasswordField.getText()) == false) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Pogreška kod unošenja podataka!");
            alert.setHeaderText("Neispravni podaci!");
            alert.setContentText("Lozinka i ponovljena lozinka nisu iste!");

            alert.showAndWait();
        } else {
            User newUser = new User(usernameTextField.getText(),
                    passwordPasswordField.getText(), isAdminCheckBox.isSelected(), tempWk);

            for (User user : EmployeeManagmentModule.UserRepo) {
                if (user.getUsername().equals(newUser.getUsername())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Pogreška!");
                    alert.setHeaderText("Neuspješno registriran korisnik!");
                    alert.setContentText("Unijeli ste korisničko ime koje već postoji!");

                    alert.showAndWait();
                    return;
                }
            }

            startClient(newUser);
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Akcija uspješno izvršena!");
            alert.setHeaderText("Uspješno registriran korisnik!");
            alert.setContentText("Uneseni korisnik je spremljen u sustav!"
                    + "\nMožete se prijavi s njime!");

            alert.showAndWait();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("AdminViewFXML.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                ((Node) (event.getSource())).getScene().getWindow().hide();

            } catch (IOException ex) {
                Logger.getLogger(EmployeeSideFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}

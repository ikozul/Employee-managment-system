/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;

import com.ivanarm.Server.User;
import static com.ivanarm.EmployeeManagmentModule.UserRepo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

/**
 *
 * @author Ivan Kožul
 */
public class Utils {

    public static final void setLabelColor(Label lbl, User u) {

        if (u.isDidSignin()) {
            lbl.setText("WORKING");
            lbl.setTextFill(Color.GREEN);
        } else {
            lbl.setText("NOT WORKING");
            lbl.setTextFill(Color.RED);
        }
    }
    public static final void setListViewColor(Label lbl, User u) {

        if (u.isDidSignin()) {
            lbl.setText("WORKING");
            lbl.setTextFill(Color.GREEN);
        } else {
            lbl.setText("NOT WORKING");
            lbl.setTextFill(Color.RED);
        }
    }

    public static final ObservableList<LocalDate> getObservableListForDate(ArrayList<Date> d) {
        ObservableList<LocalDate> oListDate = FXCollections.observableArrayList();
        return oListDate;
    }

    public static final User getUserForValue(String value) {
        User dummy = new User();
        for (User u : UserRepo) {
            if (u.getUsername().equals(value)) {
                dummy = u;
            }

        }
        return dummy;
    }

    public static final ObservableList<User> getObservableListForUserName(List<User> u) {
        ObservableList<User> oListUser;
        oListUser = FXCollections.observableArrayList(u);
        return oListUser;
    }

    public static void updateComboBox(ComboBox<User> cb) {
        cb.getSelectionModel().clearSelection();
        cb.getItems().clear();
        Utils.getObservableListForUserName(UserRepo).forEach((user) -> {
            cb.getItems().add(user);
        });
    }
   
    public static void fillListViews(ListView signInListView, ListView signOutListView,ListView workingTime , User current) {

        
        signInListView.getItems().clear();
        signOutListView.getItems().clear();
        workingTime.getItems().clear();

        current.getWorkingTime().getSignIn().forEach((d) -> {
            signInListView.getItems().add(d.toString());
        });
        
        current.getWorkingTime().getSignOut().forEach((d) -> {
            signOutListView.getItems().add(d.toString());
        });
        
        for (int i = 0; i < current.getWorkingTime().getSignOut().size(); i++) {
                workingTime.getItems().add(DateHelper.timeCalculator(current.getWorkingTime().getSignIn().get(i),current.getWorkingTime().getSignOut().get(i)) 
                        + " Dnevnica: "+ DateHelper.paycheckCalculator(current.getWorkingTime().getSignIn().get(i),current.getWorkingTime().getSignOut().get(i))+ " kuna");           
        }
    }
    public static void fillListViews(ListView signInListView, ListView signOutListView, User current) {
        signInListView.getItems().clear();
        signOutListView.getItems().clear();

        current.getWorkingTime().getSignIn().forEach((d) -> {
            signInListView.getItems().add(d);
        });

        current.getWorkingTime().getSignOut().forEach((d) -> {
            signOutListView.getItems().add(d);
        });

    }

}

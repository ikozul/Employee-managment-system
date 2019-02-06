/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Server;

import com.ivanarm.Model.XmlJndiHelpers.Jndi;
import static com.ivanarm.Server.Serialization.readFileForUser;
import static com.ivanarm.Server.Serialization.writeFilesForUser;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Ivan Kožul
 */
public final class UserRepo implements Serializable {

    
    private static final String DESKTOP = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    private static final String SERIALIZATION_PATH = DESKTOP + "\\Users.ser";
    public static ArrayList<User> users = new ArrayList<User>();

    public void getUsers() {

        if (Files.exists(Paths.get(SERIALIZATION_PATH)) == false) {
            addTestData();

        } else {
            ArrayList<User> dummy = readFileForUser(SERIALIZATION_PATH);
            dummy.stream().filter((user) -> (!users.contains(user))).forEachOrdered((user) -> {
                users.add(user);
            });
        }

    }

    public void AddUser(User u) {
        users.add(u);
        writeFilesForUser(SERIALIZATION_PATH);
    }

    public void UpdateUser(User user) {
        users.stream().filter((u) -> (u.equals(user))).forEachOrdered((u) -> {
            u.setWorkingTime(user.getWorkingTime());
            u.setDidSignin(user.isDidSignin());
            writeFilesForUser(SERIALIZATION_PATH);
            
        });
    }
    private static void addTestData() {

        List<LocalDateTime> startDates = new ArrayList<>();
        List<LocalDateTime> endDates = new ArrayList<>();
        fillStartDates(startDates);
        fillEndDates(endDates);
        users.add(new User("admin", "admin", true));
        users.add(new User("pero", "pero", false, new WorkingTime(startDates, endDates)));
        writeFilesForUser(SERIALIZATION_PATH);

    }

    private static void fillStartDates(List<LocalDateTime> startDates) {
        startDates.add(LocalDateTime.of(2018, Month.MARCH, 12, 8, 0, 0));
        startDates.add(LocalDateTime.of(2018, Month.MARCH, 13, 8, 0, 0));
        startDates.add(LocalDateTime.of(2018, Month.MARCH, 14, 8, 0, 0));
        startDates.add(LocalDateTime.of(2018, Month.MARCH, 15, 8, 0, 0));
    }

    private static void fillEndDates(List<LocalDateTime> endDates) {
        endDates.add(LocalDateTime.of(2018, Month.MARCH, 12, 16, 0, 0));
        endDates.add(LocalDateTime.of(2018, Month.MARCH, 13, 16, 0, 0));
        endDates.add(LocalDateTime.of(2018, Month.MARCH, 14, 16, 0, 0));
        endDates.add(LocalDateTime.of(2018, Month.MARCH, 15, 16, 0, 0));

    }
}

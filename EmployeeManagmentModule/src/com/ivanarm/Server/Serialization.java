/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Server;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ivan Kožul
 */
public class Serialization {

    public static void writeFilesForUser(String FILE_PATH) {
        try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(FILE_PATH))) {
            stream.writeObject(UserRepo.users);
        } catch (IOException ex) {
            Logger.getLogger(Serialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<User> readFileForUser(String FILE_PATH) {
        ArrayList<User> lista = new ArrayList<>();
        try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {

            Object object = inStream.readObject();

            if (object instanceof ArrayList) {
                lista = (ArrayList<User>) object;
            }
        } catch (IOException | ClassNotFoundException | NullPointerException ex) {
            Logger.getLogger(Serialization.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
}

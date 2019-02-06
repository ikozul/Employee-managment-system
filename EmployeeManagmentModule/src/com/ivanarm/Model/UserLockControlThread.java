/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;

import static com.ivanarm.EmployeeManagmentModule.UserRepo;
import com.ivanarm.Server.User;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.control.Button;

/**
 *
 * @author Ivan Kožul
 */
public class UserLockControlThread extends Thread {

    public UserLockControlThread(Button btnSignin, Button btnSignOut) {
        this.btnSignin = btnSignin;
        this.btnSignOut = btnSignOut;
    }
    private final Button btnSignin;
    public final Button btnSignOut;

    @Override
    public void run() {
        while (true) {
            System.out.println("Checking");
            synchronized (AdminLockControlThread.lock) {
                try {
                    btnSignOut.setDisable(false);
                    btnSignin.setDisable(false);
                    AdminLockControlThread.lock.wait();
                    
                    
                    btnSignOut.setDisable(true);
                    btnSignin.setDisable(true);
                    AdminLockControlThread.lock.wait();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

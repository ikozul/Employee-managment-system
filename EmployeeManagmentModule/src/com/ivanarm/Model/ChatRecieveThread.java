/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;

import com.ivanarm.EmployeeManagmentModule;
import java.rmi.RemoteException;
import javafx.scene.control.ListView;

/**
 *
 * @author Ivan Kožul
 */
public class ChatRecieveThread extends Thread implements Runnable{

    private ListView<String> chatListView;

    public ChatRecieveThread(ListView chatListView) {
        this.chatListView = chatListView;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ChatSendThread.gotMessage) {
                try {                    
                    ChatSendThread.gotMessage.wait();
                    chatListView.getItems().clear();
                    EmployeeManagmentModule.value.getAllMessages().forEach(x -> chatListView.getItems().add(x));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

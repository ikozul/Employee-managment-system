/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan Kožul
 */
public class ChatSendThread extends Thread{
    public static final Object gotMessage = new Object();

    @Override
    public void run() {
        synchronized (gotMessage) {
            //System.out.println("notifiying user");
            gotMessage.notify();

        }
    }
}

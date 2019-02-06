/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;


import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Ivan Kožul
 */
public class AdminLockControlThread extends Thread{    
    public static final Object lock = new Object();
    @Override
    public void run() {
        synchronized (lock) {
            System.out.println("notifiying user");
            lock.notify();            
        }
    }

}

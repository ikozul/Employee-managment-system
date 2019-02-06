/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;

import java.rmi.*;
import java.util.*;

/**
 *
 * @author hyperv
 */
public interface ChatInterface extends Remote {
    // declaring methods to call remotely

    public void sendMessage(String message) throws RemoteException;

    public List<String> getAllMessages() throws RemoteException;

    public void setAllMessages(List<String> lista) throws RemoteException;;

}

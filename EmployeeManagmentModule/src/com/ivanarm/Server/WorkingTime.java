/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Server;

import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivan Kožul
 */
public class WorkingTime implements Serializable {

    private List<LocalDateTime> signIn = new ArrayList<>();
    private List<LocalDateTime> signOut = new ArrayList<>();

    public WorkingTime(List<LocalDateTime> signIn, List<LocalDateTime> signOut) {
        this.signIn = signIn;
        this.signOut = signOut;
    }

    public List<LocalDateTime> getSignIn() {
        return signIn;
    }

    public void setSignIn(LocalDateTime e) {
        this.signIn.add(e);
    }

    public List<LocalDateTime> getSignOut() {
        return signOut;
    }

    public void setSignOut(LocalDateTime e) {
        this.signOut.add(e);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Ivan Kožul
 */
public class User implements Serializable{

    private String username;
    private String password;
    private boolean isAdmin;  

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean isLock) {
        this.isLock = isLock;
    }
    private boolean isLock;
    private WorkingTime workingTime;    
    public WorkingTime getWorkingTime() {
        return workingTime;
    }
    
    public void setWorkingTime(WorkingTime workingTime) {
        this.workingTime = workingTime;
    }
    private boolean didSignin;

    public User(String username, String password, boolean isAdmin) {
        this(username, password, isAdmin, new WorkingTime(new ArrayList<>(), new ArrayList<>()));
    }

    public User(String username, String password, boolean isAdmin, WorkingTime workingTime) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.workingTime = workingTime;
        didSignin = false;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean IsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isDidSignin() {
        return didSignin;
    }

    public void setDidSignin(boolean didSignin) {
        this.didSignin = didSignin;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.username);
        hash = 79 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}

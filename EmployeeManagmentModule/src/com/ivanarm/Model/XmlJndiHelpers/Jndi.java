/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model.XmlJndiHelpers;

import com.ivanarm.Server.Server;
import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Ivan Kožul
 */
public class Jndi {

    public Hashtable NeededForDeployment;

    public Jndi() {
        this.NeededForDeployment = get();
    }

    private Hashtable get() {
        Hashtable env = new Hashtable();

        // Hashtable punimo sa podacima koji govore o kojoj implementaciji
        // usluge imenovanja se radi
        // i koja nam je po�etna to�ka u hijerarhiji gdje se pozicioniramo
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.fscontext.RefFSContextFactory");

        env.put(Context.PROVIDER_URL, "file:" + FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "\\NeededForDeployment");
        try {
            // kreiramo objekt tipa Context
            Context ctxt = new InitialContext(env);
            // dohvati listu prona�enih zapisa
            NamingEnumeration flist = ctxt.listBindings("");

            int count = 0;
            // Shows what is needed for deployment
            while (flist.hasMore()) {
                Binding p = (Binding) flist.next();
                System.out.println(p.getName());
                if (count == 0) {
                    Server.PORT_SOCKET = Integer.valueOf(p.getName());
                }
                if (count == 1) {
                    Server.PORT_RMI = Integer.valueOf(p.getName());
                }
                count++;

            }
        } catch (NamingException ne) {
            System.out.println(ne);
        }
        return env;
    }
}

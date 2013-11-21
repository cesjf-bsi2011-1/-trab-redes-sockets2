/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.auadottoni.playlist.test;

import com.auadottoni.playlist.cliente.ClientFrame;
import com.auadottoni.playlist.server.ServerFrame;

/**
 *
 * @author auadtassio
 */
public class Main {
    
    public static void main(String[] args) {
        new ServerFrame().setVisible(true);
        new ClientFrame().setVisible(true);
        new ClientFrame().setVisible(true);
        new ClientFrame().setVisible(true);
    }
}

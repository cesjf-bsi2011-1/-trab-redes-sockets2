/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.auadottoni.playlist.server;

import com.auadottoni.playlist.model.Music;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author auadtassio
 */
public class ServerFrame extends javax.swing.JFrame implements InsertMusicInterface{

    private Thread thread;
    private ArrayList<Music> musics = new ArrayList<>();
    private ServerSocket serverSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket socket;
    private static int musicTrack = 0;
    private static final String NEXT_MESSAGE = "next_music";
    private static final String CLOSE_STREAMING_MESSAGE = "close_streaming";
    
    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        initComponents();
        setExtendedState(ServerFrame.MAXIMIZED_BOTH);
    }
    
    public void runServer() {
        buttonRunServer.setEnabled(false);
        buttonInsertMusic.setEnabled(false);
        buttonStopServer.setEnabled(true);
        
        thread = new Thread(
            new Runnable() {

                @Override
                public void run() {
                    try {
                        serverSocket = new ServerSocket(5555, 100);

                        while(true) {
                            //Waiting for a connection
                            socket = serverSocket.accept();

                            //Setting OutputStream and InputStream
                            settingStreamObjects();

                            //Sending musics
                            streaming();

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        );
        thread.start();
    }
    
    public void stopServer() throws IOException {
        thread.interrupt();
        serverSocket.close();
        socket.close();
        buttonRunServer.setEnabled(true);
        buttonInsertMusic.setEnabled(true);
        buttonStopServer.setEnabled(false);
    }
    
    public void settingStreamObjects() throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
       /*FLUSH - Enviando um cabeçalho de fluxo para o ObjectInputStream correspondente
        *no client. Isso faz com que o ObjectInputStream do client se prepare para
        *receber dados corretamente e o ObjectOutputStream do servidor seja esvaziado.
       */
       outputStream.flush();
       inputStream = new ObjectInputStream(socket.getInputStream());
    }
   
    private void streaming() throws IOException, ClassNotFoundException {
        String messageReceived;
        do {
            outputStream.writeObject(getNextMusic());
            outputStream.flush();
            messageReceived = (String) inputStream.readObject();
            if(messageReceived.equals(NEXT_MESSAGE)) {
                outputStream.writeObject(getNextMusic());
                outputStream.flush();
            }
        } while(!messageReceived.equals(CLOSE_STREAMING_MESSAGE));
    }
    
    public Music getNextMusic() {
        //Preparing to send the next music
        musicTrack++;
        if(musicTrack >= musics.size()) {
            musicTrack = 0;
        }
        
        return musics.get(musicTrack);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableMusics = new javax.swing.JTable();
        buttonRunServer = new javax.swing.JButton();
        buttonInsertMusic = new javax.swing.JButton();
        buttonStopServer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Playlist Server");

        tableMusics.setFont(new java.awt.Font("Ubuntu Light", 0, 14)); // NOI18N
        tableMusics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Music Name", "Music Author", "Music File Name", "Music File Path"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMusics.setEnabled(false);
        jScrollPane1.setViewportView(tableMusics);

        buttonRunServer.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        buttonRunServer.setText("Run Server");
        buttonRunServer.setEnabled(false);
        buttonRunServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRunServerActionPerformed(evt);
            }
        });

        buttonInsertMusic.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        buttonInsertMusic.setText("Insert Music");
        buttonInsertMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInsertMusicActionPerformed(evt);
            }
        });

        buttonStopServer.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        buttonStopServer.setText("Stop Server");
        buttonStopServer.setEnabled(false);
        buttonStopServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonInsertMusic)
                        .addGap(18, 18, 18)
                        .addComponent(buttonRunServer)
                        .addGap(18, 18, 18)
                        .addComponent(buttonStopServer)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonRunServer)
                    .addComponent(buttonInsertMusic)
                    .addComponent(buttonStopServer))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonInsertMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertMusicActionPerformed
        buttonInsertMusic.setEnabled(false);
        buttonRunServer.setEnabled(false);
        JFileChooser fileChooser = new JFileChooser();
        int chooseResultValue = fileChooser.showOpenDialog(this);
        
        if(chooseResultValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Music music = new Music();
            music.setFile(file);
            new MusicInfoFrame(this, music).setVisible(true);
        } else {
            buttonInsertMusic.setEnabled(true);
            buttonRunServer.setEnabled(true);  
        }
    }//GEN-LAST:event_buttonInsertMusicActionPerformed

    private void buttonRunServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRunServerActionPerformed
        buttonInsertMusic.setEnabled(false);
        buttonRunServer.setEnabled(false);
        runServer();
    }//GEN-LAST:event_buttonRunServerActionPerformed

    private void buttonStopServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopServerActionPerformed
        try {
            stopServer();
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonStopServerActionPerformed

    @Override
    public void insertMusic(Music music) {
        if(music != null) {
            musics.add(music);
            updateMusicTable();
        }
        
        buttonInsertMusic.setEnabled(true);
        buttonRunServer.setEnabled(true);
    }
    
    public void updateMusicTable() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tableMusics.getModel();
        defaultTableModel.getDataVector().removeAllElements();
        
        for(Music music : musics) {
            defaultTableModel.addRow(
                new Object[]{
                    music.getName(),
                    music.getAuthor(),
                    music.getFile().getName(),
                    music.getFile().getPath()
                }
            );
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonInsertMusic;
    private javax.swing.JButton buttonRunServer;
    private javax.swing.JButton buttonStopServer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableMusics;
    // End of variables declaration//GEN-END:variables

}

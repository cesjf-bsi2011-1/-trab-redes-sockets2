/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.auadottoni.playlist.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author auadtassio
 */
public class Music implements Serializable{
    private String name;
    private String author;
    private File file;
    private byte[] filebytes;
    
    public Music() {
        
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) throws IOException {
        this.file = file;
        filebytes  = new byte [(int)file.length()];
        FileInputStream fis = new FileInputStream(file);  
        BufferedInputStream bis = new BufferedInputStream(fis);  
        bis.read(filebytes, 0, filebytes.length);  
    }

    public byte[] getFilebytes() {
        return filebytes;
    }

    public void setFilebytes(byte[] filebytes) {
        this.filebytes = filebytes;
    }
}

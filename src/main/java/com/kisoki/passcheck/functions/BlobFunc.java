/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kisoki.passcheck.functions;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nwneal
 */
public class BlobFunc {
    private static GcsService storage = GcsServiceFactory.createGcsService(new RetryParams.Builder()
      .initialRetryDelayMillis(10)
      .retryMaxAttempts(10)
      .totalRetryPeriodMillis(15000)
      .build()); 
    
    public static String[] splitBlob (BlobKey blobKey, String fn) {
        List<String> passwords = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BlobstoreInputStream(blobKey)));
            String line = null;

            while((line = reader.readLine()) != null) {
                if (line.length() <= 100) {
                    passwords.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            
        }
        
        // split password blob into 10,000 line password files.
        
        String[] gcsFiles = new String[getNumFiles(passwords.size())];
        int[] numlist = getNumList(passwords.size());
        int counter = 0; // used to count lines
        int lineNum = 0;
        
        // create password list fragments        
        while (counter < getNumFiles(passwords.size())) {
            GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
            GcsFilename fileName = new GcsFilename("passcheck-kisoki-com.appspot.com", (counter+fn));
            GcsOutputChannel outputChannel;
            OutputStream output = null;
            try {
                outputChannel = storage.createOrReplace(fileName, instance);
                output = Channels.newOutputStream(outputChannel);

                while (lineNum < numlist[counter]) {
                    byte[] buffer = (passwords.get(lineNum)+"\n").getBytes();
                    output.write(buffer);
                    
                    lineNum++;
                }
                
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(BlobFunc.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            gcsFiles[counter] = counter+fn;
            counter++;
        }
        
        return gcsFiles;
    }
    
    private static int getNumFiles(int listSize) {
        return (int) Math.ceil(listSize / 10000);
    }
    
    private static int[] getNumList(int listSize) {
        int numOfLists = getNumFiles(listSize);
        int[] lists = new int[numOfLists];
        int addnum = 0;
        
        for (int i = 0; i < numOfLists; i++) {
            if ((listSize-addnum) < 10000) {
                addnum = addnum+(listSize-addnum);
                lists[i] = addnum;
            } else {
                addnum+=10000;
                lists[i] = addnum;
            }
        }
        
        return lists;
    }
}

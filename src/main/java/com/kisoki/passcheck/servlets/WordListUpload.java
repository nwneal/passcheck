/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kisoki.passcheck.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.kisoki.passcheck.functions.checkPassword;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author nwneal
 */
public class WordListUpload extends HttpServlet {
    private static final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
        List<BlobKey> blobKeys = new ArrayList<BlobKey>(blobs.values());

        // check filetype for compatibility
        BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
        BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKeys.get(0));
        String fileType = blobInfo.getContentType();
        String fileName = blobInfo.getFilename();
        long fileSize = blobInfo.getSize();
        String fileExt = FilenameUtils.getExtension(fileName);
        if ((fileExt.equals("txt") || fileExt.equals("lst")) && fileType.equals("text/plain") && fileSize <= 307200) {
           // add file to db upload queue...
           com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getDefaultQueue();
           queue.add(TaskOptions.Builder.withUrl("/UploadWorker").param("blobkey", blobKeys.get(0).getKeyString()));
           response.sendRedirect("/");
        } else {
            blobstoreService.delete(blobKeys.get(0));
            response.sendRedirect("/upload.jsp?error=1");
        }
            
            

//        if (blobKeys == null || blobKeys.isEmpty()) {
//            res.sendRedirect("/");
//        } else {
//            res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
//        }
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

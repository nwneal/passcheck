<%-- 
    Document   : upload
    Created on : Jun 26, 2017, 8:13:52 AM
    Author     : nwneal
--%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <title>Upload Wordlist</title>
    </head>
    <body>
        <h1>Upload Wordlist</h1>
        <div id="error-box"></div>
        <form action="<%= blobstoreService.createUploadUrl("/WordListUpload") %>" method="post" enctype="multipart/form-data">
            <input type="file" name="wordlist">
            <input type="submit" name="Upload">
        </form>
        <script>
            function findGetParameter(parameterName) {
                var result = null,
                tmp = [];
                var items = location.search.substr(1).split("&");
                for (var index = 0; index < items.length; index++) {
                    tmp = items[index].split("=");
                    if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
                }
                return result;
            }
            
            if (findGetParameter("error") == 1) {
                $('#error-box').html("<font color=\"red\">Error uploading wordlist... Make sure that:<br><ul><li>The file exstension is .txt or .lst</li><li>The file type is 'text/plain'</li><li>The file size is less than 5MB</li></ul></font>");
            }
            
        </script>    
    </body>
</html>

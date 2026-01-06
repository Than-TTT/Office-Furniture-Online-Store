package cnpm.ergo.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet(name = "FileServlet", urlPatterns = {"/uploads/*"})
public class FileServlet extends HttpServlet {
    
    private String uploadDir;
    
    @Override
    public void init() throws ServletException {
        // Use webapp's real path for portable upload directory
        uploadDir = getServletContext().getRealPath("/uploads");
        if (uploadDir == null) {
            // Fallback: use a directory relative to webapp
            uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
        }
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println("FileServlet upload directory: " + uploadDir);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        String filename = pathInfo.substring(1);
        File file = new File(uploadDir, filename);
        
        // Also check in campaigns subdirectory (for campaign images)
        if (!file.exists()) {
            file = new File(getServletContext().getRealPath("/campaigns"), filename);
        }
        
        // Also check directly in webapp root for campaigns folder
        if (!file.exists() && filename.startsWith("campaigns/")) {
            file = new File(getServletContext().getRealPath("/" + filename));
        }
        
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        String mimeType = getServletContext().getMimeType(filename);
        if (mimeType == null) {
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                mimeType = "image/jpeg";
            } else if (filename.endsWith(".png")) {
                mimeType = "image/png";
            } else if (filename.endsWith(".gif")) {
                mimeType = "image/gif";
            } else if (filename.endsWith(".webp")) {
                mimeType = "image/webp";
            } else {
                mimeType = "application/octet-stream";
            }
        }
        
        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());
        
        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }
    }
}
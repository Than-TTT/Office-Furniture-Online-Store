package cnpm.ergo.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet(name = "FileServlet", urlPatterns = {"/uploads/*"})
public class FileServlet extends HttpServlet {
    
    private static final String UPLOAD_DIR = "D:/CNPM/Project/Office-Furniture-Online-Store/src/uploads";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        System.out.println("=== FileServlet called ===");
        System.out.println("URI: " + request.getRequestURI());
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        String filename = pathInfo.substring(1);
        File file = new File(UPLOAD_DIR, filename);
        
        System.out.println("Looking for: " + file.getAbsolutePath());
        System.out.println("Exists: " + file.exists());
        
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
            System.out.println("File sent successfully!");
        }
    }
}
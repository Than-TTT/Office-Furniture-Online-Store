package util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {

    // Define the upload directory path
    private static final String UPLOAD_DIR = "campaigns";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "webp"};

    /**
     * Upload a file from Part and return the relative path
     * @param part the file part from the request
     * @param uploadBasePath the base path where files should be uploaded (usually ServletContext.getRealPath("/"))
     * @return the relative path to the uploaded file, or null if upload failed
     */
    public static String uploadFile(Part part, String uploadBasePath) {
        try {
            if (part == null || part.getSize() == 0) {
                return null;
            }

            // Validate file size
            if (part.getSize() > MAX_FILE_SIZE) {
                throw new IOException("File size exceeds maximum limit of 5MB");
            }

            // Get original filename
            String fileName = getFileName(part);
            if (fileName == null || fileName.isEmpty()) {
                return null;
            }

            // Validate file extension
            String fileExtension = getFileExtension(fileName).toLowerCase();
            if (!isAllowedExtension(fileExtension)) {
                throw new IOException("File type not allowed. Allowed types: " + String.join(", ", ALLOWED_EXTENSIONS));
            }

            // Create upload directory if it doesn't exist
            String uploadPath = uploadBasePath + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generate unique filename to avoid conflicts
            String uniqueFileName = UUID.randomUUID() + "_" + System.currentTimeMillis() + "." + fileExtension;
            String filePath = uploadPath + File.separator + uniqueFileName;

            // Save the file
            part.write(filePath);

            // Return relative path for database storage
            return UPLOAD_DIR + "/" + uniqueFileName;

        } catch (IOException e) {
            System.err.println("Error uploading file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a file from the upload directory
     * @param relativePath the relative path of the file (as stored in database)
     * @param uploadBasePath the base path
     * @return true if deletion was successful, false otherwise
     */
    public static boolean deleteFile(String relativePath, String uploadBasePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return false;
        }

        try {
            String filePath = uploadBasePath + File.separator + relativePath.replace("/", File.separator);
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extract filename from Part header
     */
    private static String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return null;
    }

    /**
     * Get file extension from filename
     */
    private static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Check if file extension is allowed
     */
    private static boolean isAllowedExtension(String extension) {
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Construct absolute URL path for image display
     */
    public static String getImageUrl(String relativePath, String contextPath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return "";
        }
        return contextPath + "/" + relativePath;
    }
}

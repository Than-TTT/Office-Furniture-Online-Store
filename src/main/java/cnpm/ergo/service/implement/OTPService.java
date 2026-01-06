package cnpm.ergo.service.implement;

import cnpm.ergo.service.interfaces.IOTPService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.Random;

public class OTPService implements IOTPService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USERNAME = "jeyeho6685@gmail.com";
    private static final String SMTP_PASSWORD = "psvpffyvwgdoxrwa";

    // Method to generate a 6-digit OTP
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        System.out.println("Generated OTP: " + otp);
        return String.valueOf(otp);
    }

    public void sendOTPEmail(String toEmail, String otp) {
        System.out.println("Sending OTP to: " + toEmail);
        
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", SMTP_HOST);
            properties.put("mail.smtp.port", SMTP_PORT);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã xác nhận đặt lại mật khẩu - ERGO");
            message.setText("Mã xác nhận (OTP) của bạn là: " + otp + "\n\nMã này có hiệu lực trong 90 giây.\n\nNếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.");

            Transport.send(message);
            System.out.println("OTP email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP email.", e);
        }
    }

}

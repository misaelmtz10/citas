package utez.edu.mx.citas.service;

public interface EmailService {
    boolean sendEmail(String emailTo, String emailSubject, String emailContent);

}

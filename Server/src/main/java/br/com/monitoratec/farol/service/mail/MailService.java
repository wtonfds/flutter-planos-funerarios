package br.com.monitoratec.farol.service.mail;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailService {
    private final JavaMailSender sender;

    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void send(String from, String to, String subject, String body) {
        send(from, List.of(to), subject, body);
    }

    public void send(String from, List<String> to, String subject, String body) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(body);

        sender.send(message);
    }

    public void send(String from, String to, String subject, String body, String attachmentPath) {
        MimeMessage mimeMessage = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            FileSystemResource fileSystemResource = new FileSystemResource(attachmentPath);
            helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        sender.send(mimeMessage);
    }
}

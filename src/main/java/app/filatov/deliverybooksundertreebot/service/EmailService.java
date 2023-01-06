package app.filatov.deliverybooksundertreebot.service;

import app.filatov.deliverybooksundertreebot.model.Book;
import app.filatov.deliverybooksundertreebot.model.EmailSettings;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailSettingsService emailSettingsService;

    private JavaMailSender getMailSender(EmailSettings emailSettings) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp-mail.outlook.com");
        mailSender.setPort(587);

        mailSender.setUsername(emailSettings.getSenderEmail());
        mailSender.setPassword(emailSettings.getSenderPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.properties.mail.tls", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    public void send(Book book, File file) throws MessagingException {
        EmailSettings emailSettings = emailSettingsService.findByUserId(book.getUser().getId());
        JavaMailSender sender = getMailSender(emailSettings);

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(emailSettings.getSenderEmail());
        helper.setTo(emailSettings.getKindleEmail());
        helper.setSubject(book.getFileName());
        helper.addAttachment(book.getFileName(), file);
        helper.setText("", true);

        sender.send(mimeMessage);
    }
}

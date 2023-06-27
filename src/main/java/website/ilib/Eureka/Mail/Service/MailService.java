package website.ilib.Eureka.Mail.Service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import website.ilib.Eureka.Team.Model.TeamModel;

@Service
public class MailService {
    private JavaMailSender javaMailSender;

    /**
     * @param javaMailSender
     */
    public MailService(JavaMailSender javaMailSender, ResourceLoader resourceLoader) {
        this.javaMailSender = javaMailSender;
        this.resourceLoader = resourceLoader;
    }

    private final ResourceLoader resourceLoader;

    public void sendVerificationMail(TeamModel team, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(team.getEmail());
            helper.setSubject("Eureka - Password for authentication");

            Resource templateResource = resourceLoader
                    .getResource("classpath:mail_templates/password.html");
            byte[] templateBytes = StreamUtils.copyToByteArray(templateResource.getInputStream());
            String htmlContent = new String(templateBytes, StandardCharsets.UTF_8);
            htmlContent = htmlContent.replace("<%=team%>", team.getTeamName())
                    .replace("<%=password%>", password);

            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
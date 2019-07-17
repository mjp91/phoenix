package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.Email;
import com.mpearsall.hr.dto.EmailTemplate;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

@Component
public class EmailService {
  private static final String FROM_ADDRESS = "noreply@hr.com";

  private final JavaMailSender javaMailSender;
  private final VelocityEngine velocityEngine;

  public EmailService(JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
    this.javaMailSender = javaMailSender;
    this.velocityEngine = velocityEngine;
  }

  @Async
  public void sendText(Email email) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(FROM_ADDRESS);
    message.setTo(email.getToAddresses().toArray(new String[0]));
    message.setCc(email.getCcAddresses().toArray(new String[0]));

    message.setSubject(email.getSubject());
    message.setText(email.getBody());

    javaMailSender.send(message);
  }

  @Async
  public void sendHtml(Email email, EmailTemplate emailTemplate) throws MessagingException {
    final Template template = velocityEngine.getTemplate(emailTemplate.getTemplateName());

    final StringWriter stringWriter = new StringWriter();
    template.merge(new VelocityContext(emailTemplate.getArguments()), stringWriter);

    email.setBody(stringWriter.toString());
    sendHtml(email);
  }

  @Async
  public void sendHtml(Email email) throws MessagingException {
    final MimeMessage message = javaMailSender.createMimeMessage();
    message.setFrom(FROM_ADDRESS);
    message.setRecipients(Message.RecipientType.TO, String.join(",", email.getToAddresses()));
    message.setRecipients(Message.RecipientType.CC, String.join(",", email.getCcAddresses()));

    message.setSubject(email.getSubject());
    message.setContent(email.getBody(), MediaType.TEXT_HTML_VALUE);

    javaMailSender.send(message);
  }
}

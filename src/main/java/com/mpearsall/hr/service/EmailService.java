package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.Email;
import com.mpearsall.hr.dto.EmailTemplate;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

  private static final String FROM_ADDRESS = "noreply@example.co.uk";

  private final JavaMailSender javaMailSender;
  private final VelocityEngine velocityEngine;

  public EmailService(@Autowired(required = false) JavaMailSender javaMailSender, VelocityEngine velocityEngine) {
    this.javaMailSender = javaMailSender;
    this.velocityEngine = velocityEngine;
  }

  @Async
  public void sendText(Email email) {
    if (javaMailSender == null) {
      return;
    }

    final SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(FROM_ADDRESS);
    message.setTo(email.getToAddresses().toArray(new String[0]));
    message.setCc(email.getCcAddresses().toArray(new String[0]));

    message.setSubject(email.getSubject());
    message.setText(email.getBody());

    javaMailSender.send(message);
  }

  @Async
  public void sendHtml(Email email, EmailTemplate emailTemplate) {
    final Template template = velocityEngine.getTemplate(emailTemplate.getTemplateName());

    final StringWriter stringWriter = new StringWriter();
    template.merge(new VelocityContext(emailTemplate.getArguments()), stringWriter);

    email.setBody(stringWriter.toString());
    try {
      sendHtml(email);
    } catch (MessagingException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Async
  public void sendHtml(Email email) throws MessagingException {
    if (javaMailSender == null) {
      return;
    }

    final MimeMessage message = javaMailSender.createMimeMessage();
    message.setFrom(FROM_ADDRESS);
    message.setRecipients(Message.RecipientType.TO, String.join(",", email.getToAddresses()));
    message.setRecipients(Message.RecipientType.CC, String.join(",", email.getCcAddresses()));

    message.setSubject(email.getSubject());
    message.setContent(email.getBody(), MediaType.TEXT_HTML_VALUE);

    javaMailSender.send(message);
  }
}

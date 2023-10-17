/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.service.impl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import com.smartict.mail.constant.enums.EnumAttachmentType;
import com.smartict.mail.constant.exceptions.ServiceException;
import com.smartict.mail.constant.messages.EnumExceptionMessages;
import com.smartict.mail.dto.MailDto;
import com.smartict.mail.dto.MailLogDto;
import com.smartict.mail.dto.MailSettingDto;
import com.smartict.mail.service.MailLogService;
import com.smartict.mail.service.MailService;
import com.smartict.mail.service.MailSettingService;
import com.smartict.mail.service.impl.mapper.MailLogMailDtoMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Gelen mailleri kayıt eden ve daha sonrasınıda kayıtlı mailleri bir Scheduler {@link MailServiceImpl#processMails()} içerisinde işleyen servistir. Mailler
 * gönderilme denemesi için <b><i>mailAttempt</i></b> içerinde hak sayının tanımlanması gerekir .Config dosyası içerisinde ayarlama değişkeni mevcuttur.
 */
@Service
public class MailServiceImpl implements MailService {
    /**
     * Loglayıcı
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    private static final String UTF_8 = "UTF-8";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private JavaMailSender javaMailSender;
    private final MailLogService mailLogService;
    private final MailSettingService mailSettingService;

    private final Integer mailAttempt;
    private final Integer timeout;
    private String mailFromProperty;

    public MailServiceImpl(
        JavaMailSender javaMailSender,
        MailLogService mailLogService,
        MailSettingService mailSettingService,
        @Value("${mail.mail-attempt}") Integer mailAttempt,
        @Value("${mail.timeout}") Integer timeout,
        @Value("${spring.mail.from}") String mailFromProperty
    ) {
        this.mailSettingService = mailSettingService;
        LOGGER.info("this.mailFromProperty ", System.getenv("MAIL_FROM"));
        this.mailFromProperty = Objects.nonNull(System.getenv("MAIL_FROM")) ? System.getenv("MAIL_FROM") : mailFromProperty;
        this.timeout = Objects.nonNull(timeout) ? timeout : Integer.parseInt(System.getenv("MAIL_TIMEOUT"));
        this.javaMailSender = getMailSender(javaMailSender);
        this.mailLogService = mailLogService;
        this.mailAttempt = Objects.nonNull(mailAttempt) ? mailAttempt : Integer.parseInt(System.getenv("MAIL_ATTEMPT"));
    }

    @Override
    public void testMailService() throws MessagingException {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) this.javaMailSender;
        LOGGER.info("Bağlantı testi yapılıyor -> " + mailSender.getUsername() + " " + mailSender.getPassword());
        mailSender.testConnection();// throws MessagingException
        LOGGER.info("Bağlantı Testi başarılı");
    }

    @Override
    public boolean sendMail(MailDto mailDto, MultipartFile multipartFile) {
        LOGGER.info("Mail isteği geldi -> To:" + mailDto.getTo() + "\nSubject:" + mailDto.getSubject() + "\nBody:" + mailDto.getBody());

        MailLogDto mailLogDto = MailLogMailDtoMapper.INSTANCE.mailDtoToMailLogDto(mailDto);
        mailLogDto.setAttemptLeft(this.mailAttempt);
        mailLogDto.setAbbreviation("default");

        if (Objects.nonNull(multipartFile)) {
            mailLogDto.setAttachmentFileName(multipartFile.getOriginalFilename());
            try {
                mailLogDto.setAttachment(multipartFile.getBytes());
            } catch (IOException e) {
                throw new ServiceException(
                    EnumExceptionMessages.MAIL_ATTACHMENT_NOT_SUPPORT.getLanguageKey(),
                    EnumExceptionMessages.MAIL_ATTACHMENT_NOT_SUPPORT.getLanguageValue()
                );
            }
        }

        return Objects.nonNull(mailLogService.create(mailLogDto));
    }

    /**
     * Default kayıtlı mail ayarları dışında farklı bir mail ayarları kullanarak mail gönderme işlemi için kullanılır.
     */

    @Override
    public boolean sendMailViaSettings(MailDto mailDto, MailSettingDto mailSettingDto, MultipartFile multipartFile) {
        LOGGER.info("Mail isteği geldi -> To:" + mailDto.getTo() + "\nSubject:" + mailDto.getSubject() + "\nBody:" + mailDto.getBody());

        MailSettingDto existSettingDto = mailSettingService.readByAbbreviation(mailSettingDto.getAbbreviation());

        if (Objects.isNull(existSettingDto)) {
            mailSettingService.create(mailSettingDto);
        } else {
            mailSettingDto.setId(existSettingDto.getId());
            mailSettingService.update(mailSettingDto);
        }

        MailLogDto mailLogDto = MailLogMailDtoMapper.INSTANCE.mailDtoToMailLogDto(mailDto);
        mailLogDto.setAttemptLeft(this.mailAttempt);
        mailLogDto.setAbbreviation(mailSettingDto.getAbbreviation());

        if (Objects.nonNull(multipartFile)) {
            mailLogDto.setAttachmentFileName(multipartFile.getOriginalFilename());
            try {
                mailLogDto.setAttachment(multipartFile.getBytes());
            } catch (IOException e) {
                throw new ServiceException(
                    EnumExceptionMessages.MAIL_ATTACHMENT_NOT_SUPPORT.getLanguageKey(),
                    EnumExceptionMessages.MAIL_ATTACHMENT_NOT_SUPPORT.getLanguageValue()
                );
            }
        }

        return Objects.nonNull(mailLogService.create(mailLogDto));
    }

    private JavaMailSender getMailSenderViaExternalSettings(MailSettingDto mailSettingDto) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        if (!Objects.isNull(mailSettingDto.getHost())) {
            mailSender.setHost(mailSettingDto.getHost());
        }

        if (!Objects.isNull(mailSettingDto.getUsername())) {
            mailSender.setUsername(mailSettingDto.getUsername());
        }

        if (!Objects.isNull(mailSettingDto.getPassword())) {
            mailSender.setPassword(mailSettingDto.getPassword());
        }

        if (!Objects.isNull(mailSettingDto.getPort())) {
            mailSender.setPort(mailSettingDto.getPort());
        }

        if (!Objects.isNull(mailSettingDto.getProtocol())) {
            mailSender.setProtocol(mailSettingDto.getProtocol());
        }

        mailSender.setJavaMailProperties(configureJavaMailProp(mailSender, mailSettingDto));

        return mailSender;
    }

    private JavaMailSender getMailSender(JavaMailSender javaMailSender) {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;

        if (!Objects.isNull(System.getenv("MAIL_" + "host".toUpperCase()))) {
            mailSender.setHost(System.getenv("MAIL_" + "host".toUpperCase()));
        }

        if (!Objects.isNull(System.getenv("MAIL_" + "username".toUpperCase()))) {
            mailSender.setUsername(System.getenv("MAIL_" + "username".toUpperCase()));
        }

        if (!Objects.isNull(System.getenv("MAIL_" + "password".toUpperCase()))) {
            mailSender.setPassword(System.getenv("MAIL_" + "password".toUpperCase()));
        }

        if (!Objects.isNull(System.getenv("MAIL_" + "port".toUpperCase()))) {
            mailSender.setPort(Integer.parseInt(System.getenv("MAIL_" + "port".toUpperCase())));
        }

        if (!Objects.isNull(System.getenv("MAIL_" + "protocol".toUpperCase()))) {
            mailSender.setProtocol(System.getenv("MAIL_" + "protocol".toUpperCase()));
        }

        mailSender.setJavaMailProperties(configureJavaMailProp(mailSender, null));

        return mailSender;
    }

    private Properties configureJavaMailProp(JavaMailSenderImpl mailSender, MailSettingDto mailSettingDto) {
        Properties mailProperties = mailSender.getJavaMailProperties();

        if (Objects.isNull(mailSettingDto)) {
            readMailConfigValue(mailProperties, "mail.smtp.auth", "AUTH");
            readMailConfigValue(mailProperties, "mail.smtp.starttls.enable", "START_TLS_ENABLE");
            readMailConfigValue(mailProperties, "mail.smtp.ssl.trust", "SSL_TRUST");
            mailProperties.put("mail.smtp.connectiontimeout", timeout);
        } else {
            mailProperties.setProperty("mail." + mailSettingDto.getProtocol().toLowerCase() + ".auth", mailSettingDto.getAuth().toString());
            //@formatter:off
            mailProperties.setProperty("mail." + mailSettingDto.getProtocol().toLowerCase() + ".starttls.enable", mailSettingDto.getStartTlsEnable().toString());
            mailProperties.setProperty("mail." + mailSettingDto.getProtocol().toLowerCase() + ".ssl.trust", mailSettingDto.getSslTrust());
            mailProperties.put("mail." + mailSettingDto.getProtocol().toLowerCase() + ".connectiontimeout", timeout);
        }

        try {
            LOGGER.info("Spring mail properties " + new ObjectMapper().writeValueAsString(mailProperties));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return mailProperties;
    }

    private void readMailConfigValue(Properties mailProperties, String keyProp, String keyConfig) {
        String value = System.getenv("MAIL_" + keyConfig.toUpperCase());
        LOGGER.info("Enviroment Value " + value);
        if (!Objects.isNull(value)) {
            mailProperties.setProperty(keyProp, value);
        }
    }

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Bu metot Verilen cron expression(zaman ifadesine)'a göre çalışır .
     * <p>
     * cron ifadesi kullanım hakkında <a href="URL#https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm">bağlantı</a>
     * </p>
     * <p>
     * Her çalışmaya başladığı vakit ; sistemde kayıtlı mailleri her birisi için göndermeye çalışır . işlem sonnuda gönderilen ve deneme hakkı biten mailler
     * silinir.
     * </p>
     */
    @Scheduled(cron = "${spring.mail.scheduler}")
    private void processMails() {
        LOGGER.info("Mail zamanlayıcı başladı...");
        List<MailLogDto> mailLogDtos = mailLogService.readAll();
        LOGGER.info("İşleme konulan mail sayısı: " + mailLogDtos.size());

        int logCounter = 1;
        int doneMailCounter = 0;

        for (MailLogDto mailLogDto : mailLogDtos) {
            JavaMailSender javaMailSender;

            MailSettingDto settingDto = mailSettingService.readByAbbreviation(mailLogDto.getAbbreviation());

            if (Objects.nonNull(settingDto)) {
                javaMailSender = getMailSenderViaExternalSettings(settingDto);
            } else {
                javaMailSender = this.javaMailSender;
            }

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            LOGGER.info("Mail gönderililiyor..." + "[" + (logCounter) + "/" + mailLogDtos.size() + "]");

            try {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, UTF_8);

                if (Objects.nonNull(settingDto)) {
                    LOGGER.info("from: " + settingDto.getFromAddress());
                    message.setFrom(settingDto.getFromAddress());
                }else{
                    LOGGER.info("from: " + this.mailFromProperty);
                    if (Objects.nonNull(this.mailFromProperty)) {
                        message.setFrom(this.mailFromProperty);
                    }
                }

                message.setTo(mailLogDto.getTo().split(","));

                LOGGER.info("isHtml " + mailLogDto.getIsHtml());
                message.setText(mailLogDto.getBody(), mailLogDto.getIsHtml());
                message.setSubject(mailLogDto.getSubject());

                if (mailLogDto.getCc() != null && mailLogDto.getCc().length() > 0) {
                    message.setCc(mailLogDto.getCc().split(","));
                }

                if (mailLogDto.getBcc() != null && mailLogDto.getBcc().length() > 0) {
                    message.setBcc(mailLogDto.getBcc().split(","));
                }

                if (Objects.nonNull(mailLogDto.getAttachment()) && mailLogDto.getAttachment().length > 0) {
                    String fileName = mailLogDto.getAttachmentFileName();
                    EnumAttachmentType attachmentType = EnumAttachmentType.valueOfFileNameExtension(fileName);

                    if (Objects.nonNull(attachmentType)) {
                        switch (attachmentType) {
                            case PDF_ATTACHMENT: {
                                ByteArrayDataSource dSource = new ByteArrayDataSource(mailLogDto.getAttachment(), "application/pdf");
                                message.addAttachment(fileName, dSource);
                                break;
                            }
                            case IMAGE_ATTACHMENT: {
                                ByteArrayDataSource dSource = new ByteArrayDataSource(mailLogDto.getAttachment(), "image/*");
                                message.addAttachment(fileName, dSource);
                                break;
                            }
                            case EXCEL_ATTACHMENT: {
                                ByteArrayDataSource dSource = new ByteArrayDataSource(mailLogDto.getAttachment(), "application/vnd.ms-excel");
                                message.addAttachment(fileName, dSource);
                                break;
                            }
                            case TXT_ATTACHMENT: {
                                ByteArrayDataSource dSource = new ByteArrayDataSource(mailLogDto.getAttachment(), "text/plain;charset=UTF-8");
                                message.addAttachment(fileName, dSource);
                                break;
                            }
                            default:
                                throw new ServiceException(
                                    EnumExceptionMessages.MAIL_ATTACHMENT_NOT_SUPPORT.getLanguageKey(),
                                    EnumExceptionMessages.MAIL_ATTACHMENT_NOT_SUPPORT.getLanguageValue()
                                );
                        }
                    }

                }

                LOGGER.info(
                    "\n Gönderen hesap: " +
                            ((JavaMailSenderImpl) javaMailSender).getUsername() +
                            "\n" +
                            ((JavaMailSenderImpl) javaMailSender).getPassword() +
                            "\nAlıcı Hesap: " +
                            mailLogDto.getTo()
                );

                javaMailSender.send(mimeMessage);
                LOGGER.info("Mail başarıyla gönderildi");

                mailLogService.delete(mailLogDto.getId());
                doneMailCounter += 1;

            } catch (Exception e) {
                LOGGER.info("\n Mail gönderme işlemi başarısız oldu !");
                mailLogDto.setAttemptLeft(mailLogDto.getAttemptLeft() - 1);
                if (mailLogDto.getAttemptLeft() <= 0) {
                    mailLogService.delete(mailLogDto.getId());
                    doneMailCounter += 1;
                } else {
                    mailLogService.update(mailLogDto);
                }

                throw new ServiceException(
                    EnumExceptionMessages.MAIL_NOT_SENT.getLanguageKey(),
                    EnumExceptionMessages.MAIL_NOT_SENT.getLanguageValue() + "\nDetails : " + e.toString() + "\n" + e.getLocalizedMessage()
                );
            } finally {
                logCounter += 1;
            }
        }
        LOGGER.info("Mail zamanlayıcı sona erdi!");
        LOGGER.info("Sona eren(Silinen) mail sayısı: " + doneMailCounter);
    }
}

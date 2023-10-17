/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.service;

import javax.mail.MessagingException;

import com.smartict.mail.dto.MailDto;
import com.smartict.mail.dto.MailSettingDto;

import org.springframework.web.multipart.MultipartFile;

public interface MailService {
    boolean sendMail(MailDto mailDto, MultipartFile multipartFile);

    boolean sendMailViaSettings(MailDto mailDto, MailSettingDto mailSettingDto, MultipartFile multipartFile);

    void testMailService() throws MessagingException;
}

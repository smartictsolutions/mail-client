/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.service;

import javax.mail.MessagingException;

import com.smartict.mail.dto.MailDto;

import org.springframework.web.multipart.MultipartFile;

public interface MailService {
    boolean sendMail(MailDto mailDto, MultipartFile multipartFile);

    void testMailService() throws MessagingException;
}

/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Gönderilecek olan Mail sınıfıdır
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDto implements Serializable {
    private String to;

    private String cc;

    private String bcc;

    private String subject;

    private String body;

    private Boolean isHtml = false;
}

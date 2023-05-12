/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailLogDto implements Serializable {
    private UUID id;

    private String to;

    private String cc;

    private String bcc;

    private String subject;

    private String body;

    private Boolean isHtml = false;

    private Integer attemptLeft;

    private byte[] attachment;

    private String attachmentFileName;
}

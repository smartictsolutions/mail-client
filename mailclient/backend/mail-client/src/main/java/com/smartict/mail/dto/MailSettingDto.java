/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.mail.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailSettingDto {
    private UUID id;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String fromAddress;
    private String protocol;
    private Boolean auth;
    private Boolean startTlsEnable;
    private String sslTrust;
    private String abbreviation;
}

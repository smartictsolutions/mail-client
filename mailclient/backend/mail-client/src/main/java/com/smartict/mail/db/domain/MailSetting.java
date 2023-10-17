/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.mail.db.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mail_setting")
public class MailSetting implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
        @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")
    })
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private Integer port;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "protocol")
    private String protocol;

    @Column(name = "auth")
    private Boolean auth;

    @Column(name = "start_tls_enable")
    private Boolean startTlsEnable;

    @Column(name = "ssl_trust")
    private String sslTrust;

    @Column(name = "abbreviation")
    private String abbreviation;
}

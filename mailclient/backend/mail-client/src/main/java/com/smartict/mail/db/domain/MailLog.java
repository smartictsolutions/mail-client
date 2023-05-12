/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.db.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mail_log")
public class MailLog implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
        @Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")
    })
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "to")
    private String to;

    @Column(name = "cc")
    private String cc;

    @Column(name = "bcc")
    private String bcc;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "attempt_left")
    private Integer attemptLeft;

    @Lob
    @Column(name = "attachment_byte_array", columnDefinition = "BLOB")
    private byte[] attachment;

    @Column(name = "attempt_file_name")
    private String attachmentFileName;

    @Column(name = "is_html")
    private Boolean isHtml;

}

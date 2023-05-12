/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.db.dao;

import java.util.UUID;

import javax.transaction.Transactional;

import com.smartict.mail.db.domain.MailLog;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface MailLogRepository extends BaseRepository<MailLog, UUID> {

}

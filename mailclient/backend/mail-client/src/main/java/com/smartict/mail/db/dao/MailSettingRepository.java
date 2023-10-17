/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.mail.db.dao;

import java.util.UUID;

import javax.transaction.Transactional;

import com.smartict.mail.db.domain.MailSetting;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface MailSettingRepository extends BaseRepository<MailSetting, UUID> {
    MailSetting findByAbbreviation(String abbreviation);
}

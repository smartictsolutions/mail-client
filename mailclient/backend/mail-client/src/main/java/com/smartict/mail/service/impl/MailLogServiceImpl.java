/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.service.impl;

import com.smartict.mail.db.dao.MailLogRepository;
import com.smartict.mail.db.domain.MailLog;
import com.smartict.mail.dto.MailLogDto;
import com.smartict.mail.service.MailLogService;
import com.smartict.mail.service.impl.mapper.MailLogMapper;

import org.springframework.stereotype.Service;

@Service
public class MailLogServiceImpl extends BaseServiceImpl<MailLogDto, MailLog> implements MailLogService {
    private final MailLogRepository mailLogRepository;

    public MailLogServiceImpl(MailLogRepository mailLogRepository) {
        super(mailLogRepository, MailLogMapper.INSTANCE);
        this.mailLogRepository = mailLogRepository;
    }

}

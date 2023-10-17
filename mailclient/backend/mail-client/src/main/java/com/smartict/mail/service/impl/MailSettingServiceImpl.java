/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.mail.service.impl;

import com.smartict.mail.db.dao.MailSettingRepository;
import com.smartict.mail.db.domain.MailSetting;
import com.smartict.mail.dto.MailSettingDto;
import com.smartict.mail.service.MailSettingService;
import com.smartict.mail.service.impl.mapper.MailSettingMapper;

import org.springframework.stereotype.Service;

@Service
public class MailSettingServiceImpl extends BaseServiceImpl<MailSettingDto, MailSetting> implements MailSettingService {
    private final MailSettingRepository mailSettingRepository;

    public MailSettingServiceImpl(MailSettingRepository mailSettingRepository) {
        super(mailSettingRepository, MailSettingMapper.INSTANCE);
        this.mailSettingRepository = mailSettingRepository;
    }

    @Override
    public MailSettingDto readByAbbreviation(String abbreviation) {
        return MailSettingMapper.INSTANCE.entityToDto(mailSettingRepository.findByAbbreviation(abbreviation));
    }
}

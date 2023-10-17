/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.mail.service.impl.mapper;

import com.smartict.mail.db.domain.MailSetting;
import com.smartict.mail.dto.MailSettingDto;
import com.smartict.mail.service.impl.mapper.base.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface MailSettingMapper extends BaseMapper<MailSetting, MailSettingDto> {
    MailSettingMapper INSTANCE = Mappers.getMapper(MailSettingMapper.class);
}

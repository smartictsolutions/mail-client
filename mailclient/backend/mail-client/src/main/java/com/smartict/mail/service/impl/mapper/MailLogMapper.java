/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.service.impl.mapper;

import com.smartict.mail.db.domain.MailLog;
import com.smartict.mail.dto.MailLogDto;
import com.smartict.mail.service.impl.mapper.base.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface MailLogMapper extends BaseMapper<MailLog, MailLogDto> {
    MailLogMapper INSTANCE = Mappers.getMapper(MailLogMapper.class);
}

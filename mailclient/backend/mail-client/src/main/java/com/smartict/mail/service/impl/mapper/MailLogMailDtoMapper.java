/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.service.impl.mapper;

import java.util.List;

import com.smartict.mail.dto.MailDto;
import com.smartict.mail.dto.MailLogDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface MailLogMailDtoMapper {
    MailLogMailDtoMapper INSTANCE = Mappers.getMapper(MailLogMailDtoMapper.class);

    MailLogDto mailDtoToMailLogDto(MailDto mailDto);

    MailDto MailLogDtoToMailDto(MailLogDto mailLogDto);

    List<MailLogDto> mailDtoListToMailLogDtoList(List<MailDto> mailDtos);

    List<MailDto> MailLogDtoListToMailDtoList(List<MailLogDto> mailLogDtos);
}

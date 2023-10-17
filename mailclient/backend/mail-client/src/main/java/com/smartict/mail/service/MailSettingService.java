/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.mail.service;

import com.smartict.mail.dto.MailSettingDto;

public interface MailSettingService extends BaseService<MailSettingDto> {
    MailSettingDto readByAbbreviation(String abbreviation);
}

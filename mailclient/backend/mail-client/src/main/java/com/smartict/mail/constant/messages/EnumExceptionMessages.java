/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.mail.constant.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum EnumExceptionMessages {
    MAIL_CONNECTION_ERROR("ExceptionMessages.MAIL_CONNECTION_ERROR", "Mail bağlantısı zaman aşıma uğradı ! Lütfen hesabın bağlantı doğrulunu kontrol ediniz ."),
    MAIL_NOT_SENT("ExceptionMessages.MAIL_NOT_SENT", "Mail gönderilemedi!"),
    MAIL_ATTACHMENT_NOT_SUPPORT("ExceptionMessages.MAIL_ATTACHMENT_NOT_SUPPORT", "Mail eki desteklenmeyen bir dosya türünde"),
    MAIL_ATTACHMENT_PROCESS_ERROR("ExceptionMessages.MAIL_ATTACHMENT_PROCESS_ERROR", "Mail eki sisteme işlenirken hata meydana geldi"),

    UNEXPECTED_ERROR_OCCURRED("ExceptionMessages.UNEXPECTED_ERROR_OCCURRED", "Beklenmeyen hata oluştu!");

    private final String languageValue;
    private final String languageKey;

    EnumExceptionMessages(String languageKey, String languageValue) {
        this.languageKey = languageKey;
        this.languageValue = languageValue;
    }

    @JsonCreator
    public static EnumExceptionMessages valueOfLanguageValue(String languageValue) {
        for (EnumExceptionMessages deger : values()) {
            if (deger.languageValue.equalsIgnoreCase(languageValue)) {
                return deger;
            }
        }
        return null;
    }

    @JsonCreator
    public static EnumExceptionMessages valueOfLanguageKey(String languageKey) {
        for (EnumExceptionMessages deger : values()) {
            if (deger.languageKey.equalsIgnoreCase(languageKey)) {
                return deger;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnumExceptionMessages {languageValue=" + languageValue + ", languageKey=" + languageKey + "}";
    }

}

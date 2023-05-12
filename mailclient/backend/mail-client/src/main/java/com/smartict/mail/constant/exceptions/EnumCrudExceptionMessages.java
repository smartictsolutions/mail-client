/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.constant.exceptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum EnumCrudExceptionMessages {
    OBJECT_NOT_BE_EMPTY("ExceptionMessages.OBJECT_NOT_BE_EMPTY", "Kaydedilecek nesne boş olamaz!"),
    ID_NOT_BE_EMPTY("ExceptionMessages.ID_NOT_BE_EMPTY", "Silinecek id boş olamaz!"),
    ENTITY_NOT_FOUND("ExceptionMessages.ENTITY_NOT_FOUND", "Entity bulunamadı! id: "),
    ID_NOT_BE_EMPTY_FOR_DELETE("ExceptionMessages.ID_NOT_BE_EMPTY_FOR_DELETE", "ID boş olamaz!"),
    ENTITY_NOT_FOUND_FOR_DELETE("ExceptionMessages.ENTITY_NOT_FOUND_FOR_DELETE", "Silinecek id'ye karşılık kayıt bulunamadı! id:"),
    AUTH_NOT_BE_EMPTY_DURING_CRUD("ExceptionMessages.AUTH_NOT_BE_EMPTY_DURING_CRUD", "CRUD işlemi esnasında authentication boş olmamalı!"),
    OBJECT_OR_ID_NOT_BE_EMPTY_DURING_UPDATE("ExceptionMessages.OBJECT_OR_ID_NOT_BE_EMPTY_DURING_UPDATE", "Güncellenecek nesne veya id boş olamaz!"),
    COMBO_LIST_NOT_CREATE("ExceptionMessages.COMBO_LIST_NOT_CREATE", "Combo listesi oluşturulamadı!"),
    VALUE_OR_TEXT_NOT_BE_EMPTY("ExceptionMessages.VALUE_OR_TEXT_NOT_BE_EMPTY", "valueProp veya textProp boş olamaz!"),
    NO_AUTHENTICATED_USER("ExceptionMessages.NO_AUTHENTICATED_USER", "Yetkili kullanıcı bulunamadı!"),
    NO_AUTHENTICATED_USER_WITH_ID("ExceptionMessages.NO_AUTHENTICATED_USER_WITH_ID", "Yetkili kullanıcı bulunamadı. Id ={}"),
    DATA_CASCADED_DELETE_ERROR("ExceptionMessages.DATA_CASCADED_DELETE_ERROR", "İlişkili veriler silinirken hata oluştu."),
    DATA_CASCADED_DELETE_FK_NOT_DEFINED_ERROR(
            "ExceptionMessages.DATA_CASCADED_DELETE_FK_NOT_DEFINED_ERROR", "İlişkili tablodaki JoinColumn\\name() alanı olmadığı için işleme devam edilemedi."
    ),
    DATA_SAVE_CONSTRAINT_ERROR("ExceptionMessages.DATA_SAVE_CONSTRAINT_ERROR", "Veri kısıtına uyulmadığı için kaydedilemedi"),
    SERVICE_INTERNAL_ERROR("ExceptionMessages.SERVICE_INTERNAL_ERROR", "Servis özel hatası!"),
    FIELD_NOT_FOUND("ExceptionMessages.FIELD_NOT_FOUND", "Tablo üzerinde alan bulunamadı!");

    private final String languageValue;
    private final String languageKey;

    EnumCrudExceptionMessages(String languageKey, String languageValue) {
        this.languageKey = languageKey;
        this.languageValue = languageValue;
    }

    @JsonCreator
    public static EnumCrudExceptionMessages valueOfLanguageValue(String languageValue) {
        for (EnumCrudExceptionMessages deger : values()) {
            if (deger.languageValue.equalsIgnoreCase(languageValue)) {
                return deger;
            }
        }
        return null;
    }

    @JsonCreator
    public static EnumCrudExceptionMessages valueOfLanguageKey(String languageKey) {
        for (EnumCrudExceptionMessages deger : values()) {
            if (deger.languageKey.equalsIgnoreCase(languageKey)) {
                return deger;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnumCrudExceptionMessages {languageValue=" + languageValue + ", languageKey=" + languageKey + "}";
    }

}

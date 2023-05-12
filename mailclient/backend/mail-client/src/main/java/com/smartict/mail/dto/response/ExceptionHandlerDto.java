/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.mail.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Hata mesajları için gerekli verilerin tutulduğu sınıftır.
 */
@Getter
@Setter
public class ExceptionHandlerDto {
    private String message;
    private Boolean warning = false;
    private String localizedMessage;
}

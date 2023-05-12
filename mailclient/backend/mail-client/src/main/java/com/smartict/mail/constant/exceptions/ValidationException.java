/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.constant.exceptions;

public class ValidationException extends ServiceException {
    public ValidationException(String messageLanguageKey, String message) {
        super(messageLanguageKey, message);
    }
}
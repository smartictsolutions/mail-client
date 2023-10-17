/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.controller;

import com.smartict.mail.constant.exceptions.ServiceException;
import com.smartict.mail.constant.messages.EnumCrudMessages;
import com.smartict.mail.dto.MailDto;
import com.smartict.mail.dto.MailSettingDto;
import com.smartict.mail.dto.response.ResponseTypeEnum;
import com.smartict.mail.dto.response.RestResponse;
import com.smartict.mail.service.MailService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping(value = "/sendMail", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<RestResponse<Boolean>> sendMail(@RequestPart("mail") MailDto mail, @Nullable @RequestPart("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    mailService.sendMail(mail, file),
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );
        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    /**
     * Default mail ayarlarından farklı ayarlar ile mail göndermek için kullanılır.
     */
    @PostMapping(value = "/sendMailViaSettings", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<RestResponse<Boolean>> sendMailViaSettings(
        @RequestPart("mail") MailDto mail,
        @RequestPart("settings") MailSettingDto settings,
        @Nullable @RequestPart("file") MultipartFile file
    ) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    mailService.sendMailViaSettings(mail, settings, file),
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );
        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @GetMapping(value = "/test")
    public ResponseEntity<RestResponse<String>> test() {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    "Service Is Running",
                    EnumCrudMessages.CREATE_TITLE.getLanguageKey(),
                    EnumCrudMessages.CREATE_TITLE.getLanguageValue(),
                    EnumCrudMessages.CREATE_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.CREATE_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );
        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    "Error",
                    EnumCrudMessages.CREATE_TITLE.getLanguageKey(),
                    EnumCrudMessages.CREATE_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

}

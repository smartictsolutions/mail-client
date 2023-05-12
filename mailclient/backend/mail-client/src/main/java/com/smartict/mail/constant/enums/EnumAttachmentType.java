/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.constant.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum EnumAttachmentType {
    PDF_ATTACHMENT(
            1, new String[] {
                ".pdf"
            }
    ),
    IMAGE_ATTACHMENT(
            2, new String[] {
                ".png",
                ".jpg"
            }
    ),
    EXCEL_ATTACHMENT(
            3, new String[] {
                ".xls",
                ".xlsx"
            }
    ),
    TXT_ATTACHMENT(
            4, new String[] {
                ".txt"
            }
    );

    Integer index;
    String[] extensionList;

    EnumAttachmentType(Integer index, String[] extensionList) {
        this.index = index;
        this.extensionList = extensionList;
    }

    public static EnumAttachmentType valueOfIndex(int index) {
        for (EnumAttachmentType deger : values()) {
            if (deger.index.intValue() == index) {
                return deger;
            }
        }
        return null;
    }

    public static EnumAttachmentType valueOfFileNameExtension(String fileName) {
        String extension = fileName.substring(fileName.indexOf('.'));
        for (EnumAttachmentType deger : values()) {
            if (Arrays.stream(deger.extensionList).collect(Collectors.toList()).contains(extension)) {
                return deger;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnumAttachmentType{" + '\'' + "index: " + index + ", details='" + extensionList + '\'' + ", =" + '}';
    }

}

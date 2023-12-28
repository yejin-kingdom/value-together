package com.vt.valuetogether.global.validator;

import static com.vt.valuetogether.global.meta.ResultCode.INVALID_PROFILE_IMAGE_FILE;
import static com.vt.valuetogether.global.meta.ResultCode.NULL_FILE_TYPE;

import com.vt.valuetogether.global.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

public class S3Validator {
    private static final String IMAGE_JPG = "image/jpeg";
    private static final String IMAGE_PNG = "image/png";

    public static void isProfileImageFile(MultipartFile multipartFile) {
        String fileType = multipartFile.getContentType();

        if (isNullFileType(fileType)) {
            throw new GlobalException(NULL_FILE_TYPE);
        }
        if (!isImageFile(fileType)) {
            throw new GlobalException(INVALID_PROFILE_IMAGE_FILE);
        }
    }

    private static boolean isNullFileType(String fileType) {
        return fileType == null;
    }

    private static boolean isImageFile(String fileType) {
        return fileType.equals(IMAGE_JPG) || fileType.equals(IMAGE_PNG);
    }
}

package com.timezerg.api.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xnx on 2018/5/23.
 */
public class FileUtil {


    public static String getExtension(String file) {
        try {
            String result = file.substring(file.lastIndexOf("."), file.length());
            return result;
        } catch (Exception e) {
            return null;
        }
    }


    public static File convert(MultipartFile file) throws IOException {

        String fileType = getExtension(file.getOriginalFilename());

        File convFile = new File("temp" + File.pathSeparator +"temp");
        convFile.createNewFile();


        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

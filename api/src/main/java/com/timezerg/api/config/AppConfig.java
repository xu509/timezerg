package com.timezerg.api.config;

/**
 * Created by xnx on 2018/5/23.
 */
public interface AppConfig {

    String Image_Domain = "http://xu-1253743633.file.myqcloud.com";

    interface DEFAULT {
        String CIVILIZATION_COVER = Image_Domain + IMG_FOLDER.CIVILIZATION + "default.jpg";
    }

    interface IMG_FOLDER{
        String CIVILIZATION = "/civilization/";
    }

    interface KEY_VALUE{
        Integer Level_Very_Important = 1;
        Integer Level_Important = 2;
        Integer Level_Normal = 3;
        Integer Level_Detail = 4;
    }

}

package com.timezerg.api.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;

/**
 * Created by xnx on 2018/5/23.
 */
public class QCloudUtil {

    private static QCloudUtil instance;
    private static COSCredentials cosCredentials;
    private static ClientConfig clientConfig;
    private static COSClient cosClient;
    private static String bucketName = "xu-1253743633";

    private static String SecretId = "AKID9IRbkUeMYp3BEFQsRvIXbt45ygIjjTfg";
    private static String SecretKey = "9D996sTtIiy9FBwWv8A1hMxTs6GLG92R";

    public static QCloudUtil getInstance(){

        if (instance == null)
            return new QCloudUtil();
        else
            return instance;
    }

    private QCloudUtil(){
        cosCredentials = new BasicCOSCredentials(SecretId,SecretKey);
        clientConfig = new ClientConfig(new Region("ap-shanghai"));
        cosClient = new COSClient(cosCredentials,clientConfig);
    }

    public Object uploadFile(File file, String path){
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,path,file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return putObjectResult;
    }

}

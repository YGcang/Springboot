package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) throws IOException {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;

        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = file.getInputStream();

        // 获取文件名称
        String filename = file.getOriginalFilename();

        // 在文件名称里添加随机值
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        filename = uuid + filename;

        // 把文件按日期分类, 上传到oss会自动分类
        // 2019/11/12/01/jpg
        // 获取当前日期
        String timeString = new DateTime().toString("yyyy/MM/dd");

        // 拼接
        filename = timeString + "/" + filename;

        ossClient.putObject(bucketName, filename, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        // https://gary-gc-edu.oss-eu-west-1.aliyuncs.com/C9167EDC-54A2-4820-984B-CA612E44D9F9.jpeg
        return "https://" + bucketName + "." + endpoint + "/" + filename;
    }
}

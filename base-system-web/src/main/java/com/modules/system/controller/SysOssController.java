//package com.modules.quartz.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.common.upload.AliyunCloudStorageService;
//import com.common.upload.CloudStorageConfig;
//import com.common.utils.exception.BusinessException;
//import com.common.web.controller.BaseController;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * Created by Administrator on 2017/7/12.
// */
//@RestController
//@RequestMapping("sys/oss")
//public class SysOssController extends BaseController {
//
//    private final static String KEY = "";
//
//    /**
//     * 云存储配置信息
//     */
//    @RequestMapping("/base")
//    @PreAuthorize("hasAuthority('sys:oss:all')")
//    public CloudStorageConfig base() {
//        CloudStorageConfig base = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
//
//        return base;
//    }
//
//    /**
//     * 保存云存储配置信息
//     */
//    @RequestMapping("/saveConfig")
//    @PreAuthorize("hasAuthority('sys:oss:all')")
//    public ResponseEntity saveConfig(@RequestBody CloudStorageConfig base) {
//
//        sysConfigService.updateValueByKey(KEY, JSON.toJSONString(base));
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    /**
//     * 上传文件
//     */
//    @RequestMapping("/upload")
//    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
//        if (file.isEmpty()) {
//            throw new BusinessException("上传文件不能为空");
//        }
//        String url = null;
//        String value = sysConfigService.getValue("",
//                null);
//        if (StringUtils.isNotBlank(value)) {
//            CloudStorageConfig base = JSON.parseObject(value, CloudStorageConfig.class);
//            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            url = new AliyunCloudStorageService(base).upload(file.getBytes(),fileExtension);
//        }
//        return url;
//    }
//
//
//    public static void main(String args[]) {
//        CloudStorageConfig base = JSON.parseObject("{\"aliyunAccessKeyId\":\"eN0UzDHYgkCM7LHG\",\"aliyunAccessKeySecret\":\"GNvB9M2yfRXCF16dSsk4m8HO79g3h8\",\"aliyunBucketName\":\"whjk-lic-test\",\"aliyunDomain\":\"http://whjk-lic-test.img-cn-hangzhou.aliyuncs.com/\",\"aliyunEndPoint\":\"http://oss-cn-hangzhou.aliyuncs.com/\",\"aliyunPrefix\":\"upload\"}", CloudStorageConfig.class);
//        System.out.println(base.getAliyunAccessKeyId());
//    }
//}

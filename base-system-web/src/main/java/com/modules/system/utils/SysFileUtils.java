package com.modules.system.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.common.utils.http.Result;
import com.modules.system.entity.resp.AttachmentInfoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件工具类
 */
public class SysFileUtils {

    /**
     * 日志
     */
    public static Logger logger = LoggerFactory.getLogger(SysFileUtils.class);

    /**
     * 文件上传
     * @param fileRootPath 根目录
     * @param file 上传文件对象
     * @param bizType 业务类型
     */
    public static Result<AttachmentInfoResp> upload(String fileRootPath, String bizType, MultipartFile file) {
        //业务类型
        if (StrUtil.isBlank(bizType)) {
            return Result.error("业务类型为null-上传文件失败");
        }
        String nowDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        //获取上传文件名称
        String fileName = file.getOriginalFilename();
        //获取上传文件名称
        String fileNameNew = bizType + "-" + RandomUtil.randomNumbers(10) + "_" + System.currentTimeMillis()
                + "." + StrUtil.subAfter(fileName, ".", true);
        //设置上传文件夹，采用 /根/业务类型/年月日/文件(将上传文件名称重命名为业务类型-时间戳格式文件名)
        String uploadFileRootPath = File.separator + bizType + File.separator + nowDate + File.separator;
        if (!FileUtil.exist((fileRootPath + uploadFileRootPath + fileNameNew))) {
            FileUtil.touch(fileRootPath + uploadFileRootPath + fileNameNew);
        }

        try {
            file.transferTo(new File((fileRootPath + uploadFileRootPath + fileNameNew)));
        } catch (IOException e) {
            logger.error("上传文件[" + fileName + "]-写入文件失败：" + e.getMessage(), e);
            return Result.error("上传文件[" + fileName + "]-写入文件失败");
        }

        String filePath = File.separator + "files" + uploadFileRootPath + fileNameNew;
        return Result.success(new AttachmentInfoResp(bizType, fileName, filePath));
    }
}

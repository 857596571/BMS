package com.modules.system.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.common.utils.http.ResponseMessage;
import com.common.utils.http.Result;
import com.common.web.controller.BaseController;
import com.common.web.util.YmlConfig;
import com.modules.system.entity.SysDict;
import com.modules.system.entity.resp.AttachmentInfoResp;
import com.modules.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文件上传下载管理
 */
@RestController
@RequestMapping(value = "/sys/file")
public class SysFileController extends BaseController {

    /**
     * 属性文件读取
     */
    @Autowired
    private YmlConfig ymlConfig;

    /**
     * 单个文件上传到本地服务器
     *
     * @return
     */
    @PostMapping(value = "/upload")
    public ResponseMessage<AttachmentInfoResp> fileUpload(HttpServletRequest request) {
        //获取上传request对象
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        //获取上传文件对象
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        //读取配置的根目录
        String fileRootPath = (String) ymlConfig.getMap("upload", String.class).get("file-root-path");
        //业务类型
        String bizType = params.getParameter("bizType");
        if (StrUtil.isBlank(bizType)) {
            return Result.error("业务类型为null-上传文件失败");
        }
        String nowDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        //获取上传文件名称
        String fileName = file.getOriginalFilename();
        //获取上传文件名称
        String fileNameNew = bizType + "-" + System.currentTimeMillis()
                + "." + StrUtil.subAfter(fileName, ".", true);
        //设置上传文件夹，采用 /根/业务类型/年月日/文件(将上传文件名称重命名为业务类型-时间戳格式文件名)
        String uploadFileRootPath = File.separator + bizType + File.separator + nowDate + File.separator;
        if (!FileUtil.exist((fileRootPath + uploadFileRootPath))) {
            FileUtil.mkdir((fileRootPath + uploadFileRootPath));
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

    /**
     * 文件下载
     * @param response
     * @param fileName
     * @param filePath
     */
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, String fileName, String filePath) {
        //读取配置的根目录
        String fileRootPath = (String) ymlConfig.getMap("upload", String.class).get("file-root-path");
        File file = FileUtil.file((fileRootPath + filePath.replace("/files/", "")));
        if (file.exists()) {
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
                response.setContentType("application/force-download;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+URLEncoder.encode(fileName,"UTF-8"));
                FileUtil.writeToStream(file, response.getOutputStream());
            } catch (Exception e) {
                logger.error("下载文件[" + fileName + "]-下载失败：" + e.getMessage(), e);
            }
        }
    }

}

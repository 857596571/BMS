package com.modules.system.controller;

import cn.hutool.core.io.FileUtil;
import com.common.utils.http.ResponseMessage;
import com.common.web.controller.BaseController;
import com.common.web.util.YmlConfig;
import com.modules.system.entity.resp.AttachmentInfoResp;
import com.modules.system.utils.SysFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;

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
        return SysFileUtils.upload(fileRootPath, bizType, file);
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

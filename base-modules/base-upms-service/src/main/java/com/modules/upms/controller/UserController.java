package com.modules.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.constant.CommonConstant;
import com.common.util.R;
import com.common.vo.UserVO;
import com.common.web.BaseController;
import com.modules.upms.model.dto.UserDTO;
import com.modules.upms.model.dto.UserInfo;
import com.modules.upms.model.entity.SysUser;
import com.modules.upms.model.entity.SysUserRole;
import com.modules.upms.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lengleng
 * @date 2017/10/28
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private SysUserService sysUserService;


    /**
     * 获取当前用户信息（角色、权限）
     * 并且异步初始化用户部门信息
     *
     * @param userVo 当前用户信息
     * @return 用户名
     */
    @GetMapping("/info")
    public R<UserInfo> user(UserVO userVo) {
        UserInfo userInfo = sysUserService.findUserInfo(userVo);
        return new R<>(userInfo);
    }

    /**
     * 通过ID查询当前用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public UserVO user(@PathVariable Integer id) {
        return sysUserService.selectUserVoById(id);
    }

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/{id}")
    public R<Boolean> userDel(@PathVariable Integer id) {
        SysUser sysUser = sysUserService.getById(id);
        return new R<>(sysUserService.deleteUserById(sysUser));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping
    public R<Boolean> user(@RequestBody UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        sysUserService.save(sysUser);

        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return new R<>(Boolean.TRUE);
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping
    public R<Boolean> userUpdate(@RequestBody UserDTO userDto) {
        SysUser user = sysUserService.getById(userDto.getUserId());
        return new R<>(sysUserService.updateUser(userDto, user.getUsername()));
    }

    /**
     * 通过用户名查询用户及其角色信息
     *
     * @param username 用户名
     * @return UseVo 对象
     */
    @GetMapping("/findUserByUsername/{username}")
    public UserVO findUserByUsername(@PathVariable String username) {
        return sysUserService.findUserByUsername(username);
    }

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号
     * @return UseVo 对象
     */
    @GetMapping("/findUserByMobile/{mobile}")
    public UserVO findUserByMobile(@PathVariable String mobile) {
        return sysUserService.findUserByMobile(mobile);
    }

    /**
     * 通过OpenId查询
     *
     * @param openId openid
     * @return 对象
     */
    @GetMapping("/findUserByOpenId/{openId}")
    public UserVO findUserByOpenId(@PathVariable String openId) {
        return sysUserService.findUserByOpenId(openId);
    }

    /**
     * 分页查询用户
     *
     * @param userVO 用户信息
     * @return 用户集合
     */
    @RequestMapping("/userPage")
    public Page userPage(Page page, UserVO userVO) {
        return sysUserService.selectWithRolePage(page, userVO);
    }

    /**
     * 上传用户头像
     * (多机部署有问题，建议使用独立的文件服务器)
     *
     * @param file 资源
     * @return filename map
     */
    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
//        String fileExt = FileUtil.extName(file.getOriginalFilename());
        Map<String, String> resultMap = new HashMap<>(1);
//        try {
//            StorePath storePath = fastFileStorageClient.uploadFile(file.getBytes(), fileExt);
//            resultMap.put("filename", fdfsPropertiesConfig.getFileHost() + storePath.getFullPath());
//        } catch (IOException e) {
//            logger.error("文件上传异常", e);
//            throw new RuntimeException(e);
//        }
        return resultMap;
    }

    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @param userVo  登录用户信息
     * @return success/false
     */
    @PutMapping("/editInfo")
    public R<Boolean> editInfo(@RequestBody UserDTO userDto, UserVO userVo) {
        return sysUserService.updateUserInfo(userDto, userVo.getUsername());
    }
}

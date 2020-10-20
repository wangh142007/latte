package com.wh.controller.center;

import com.wh.controller.BaseController;
import com.wh.pojo.Users;
import com.wh.pojo.bo.center.CenterUserBO;
import com.wh.pojo.vo.UsersVO;
import com.wh.resource.FileUpload;
import com.wh.service.center.CenterUserService;
import com.wh.utils.CookieUtils;
import com.wh.utils.DateUtil;
import com.wh.utils.IMOOCJSONResult;
import com.wh.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wh
 * @create: 2020/3/8 14:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("userInfo")
@Api(value = "用户信息的接口", tags = {"用户信息的接口的接口"})
public class CenterUserController extends BaseController {

    private CenterUserService centerUserService;
    private FileUpload fileUpload;


    @ApiOperation(value = "用户修改头像", notes = "用户修改头像", httpMethod = "POST")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        //定义头像保存的地址
//        String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();
        //在路径上为每一个用户增加一个userId，用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;
        //文件开始上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            //获取上传的文件名
            try {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    //文件重命名
                    String[] fileNameArr = fileName.split("\\.");
                    //获取文件的后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    if (!suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("jpg") &&
                            !suffix.equalsIgnoreCase("jpeg")) {
                        return IMOOCJSONResult.errorMsg("图片格式不正确！");
                    }
                    //文件名称重组 覆盖式上传，增量式：额外拼接当前时间
                    String newFileName = "face-" + userId + "." + suffix;

                    //上传头像的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                    //用于提供给web服务访问的地址
                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    //文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        //获取服务器地址
        String imageServerUrl = fileUpload.getImageServerUrl();
        //由于浏览器可能存在缓存情况，所以在这里需要加上时间戳，以便及时更新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        Users user = centerUserService.updateUserFace(userId, finalUserFaceUrl);
//        user = setNullProperty(user);
        UsersVO usersVO = conventUsersVO(user);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        //判断BindingResult是否保存错误的验证信息，如果有，直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return IMOOCJSONResult.errorMap(errorMap);
        }
        Users user = centerUserService.updateUserInfo(centerUserBO, userId);
//        user = setNullProperty(user);

        UsersVO usersVO = conventUsersVO(user);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);

        return IMOOCJSONResult.ok();
    }

    private Users setNullProperty(Users user) {
        user.setUpdatedTime(null);
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setBirthday(null);
        return user;
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField, errorMsg);
        }
        return map;
    }
}

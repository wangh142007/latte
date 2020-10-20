package com.wh.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import sun.dc.pr.PRError;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-14 19:42
 */
@Data
@ToString
@ApiModel(value = "用户对象OB", description = "从客户端，由用户传入的数据封装在此entity中")
public class UserBO {

    @ApiModelProperty(value = "用户名", name = "username", example = "wh", required = true)
    private String username;
    @ApiModelProperty(value = "密码", name = "username", example = "123456", required = true)
    private String password;
    @ApiModelProperty(value = "确认密码", name = "username", example = "123456", required = false)
    private String confirmPassword;


}

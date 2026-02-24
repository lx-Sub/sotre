package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class CreateAdminDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度4-20位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private String phone;

    @NotEmpty(message = "请至少分配一个权限")
    private List<String> permissions;
}
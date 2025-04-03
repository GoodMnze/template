package com.lingfeng.stellar.restfull.dto;


import org.springframework.validation.annotation.Validated;

/**
 * @author xiaolingfeng
 * @create 2024/5/27
 */
//@Setter
//@Getter
@Validated
public class UserDTO {

//    @NotNull(message = "用户名不能为空")
    private String name;

//    @Range(min = 16,max = Integer.MAX_VALUE, message = "用户年龄不能小于16")
    private Integer age;

//    @NotBlank(message = "地址信息不能为空")
//    @Length(max = 30, message = "地址长度必须为 10-30 个字符")
    private String address;

}

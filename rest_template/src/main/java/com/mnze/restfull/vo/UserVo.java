package com.mnze.restfull.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xiaolingfeng
 * @date 2023/4/12 9:56 AM
 */

@Setter
@Getter
@Validated
@AllArgsConstructor
public class UserVo {

    @NotNull(message = "用户名不能为空")
    private String name;

    @Range(min = 16,max = Integer.MAX_VALUE, message = "用户年龄不能小于16")
    private Integer age;

    @NotBlank(message = "地址信息不能为空")
    @Length(max = 30, message = "地址长度必须为 10-30 个字符")
    private String address;

}

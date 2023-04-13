package com.mnze.restfull.command;

import com.mnze.restfull.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;


/**
 * @author xiaolingfeng
 * @date 2023/4/12 12:52 PM
 */

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserCommandController {

        @PostMapping("/create")
        public UserVo create(@RequestBody @Valid UserVo user){
            log.info(user.toString());
            return user;
        }
}

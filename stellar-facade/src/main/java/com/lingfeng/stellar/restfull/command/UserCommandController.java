package com.lingfeng.stellar.restfull.command;

import com.lingfeng.stellar.restfull.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xiaolingfeng
 * @since 2023/4/12 12:52 PM
 */
@RestController
@RequestMapping("/api/user")
public class UserCommandController {

        @PostMapping("/create")
        public UserVo create(@RequestBody UserVo user){
//            log.info(user.toString());
            return user;
        }
}

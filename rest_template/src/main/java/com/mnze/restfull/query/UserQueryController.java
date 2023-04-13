package com.mnze.restfull.query;

import com.mnze.restfull.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolingfeng
 * @date 2023/4/12 12:49 PM
 */

@RestController
@RequestMapping("/api/user")
public class UserQueryController {

    public User queryUserById(){
        return null;
    }
}

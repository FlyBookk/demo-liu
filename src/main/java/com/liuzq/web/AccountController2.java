package com.liuzq.web;

import com.liuzq.entity.Account;
import com.liuzq.service.AccountService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liu
 * @Description
 * @Date 2019/11/26 17:31
 * @Version  1.0
 */
@RestController
@RequestMapping("/account")
public class AccountController2 {
    @Autowired
    AccountService2 accountService;

    @RequestMapping(value = "transfer", method = RequestMethod.GET)
    public void transfer(){
        accountService.transfer();
    }

}

package com.liuzq.web;

import com.liuzq.service.AccountService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fangzhipeng on 2017/4/20.
 */
@RestController
@RequestMapping("/account")
public class AccountController2 {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    AccountService2 accountService;

    @RequestMapping(value = "transfer", method = RequestMethod.GET)
    public void transfer(){
        accountService.transfer();
    }
}

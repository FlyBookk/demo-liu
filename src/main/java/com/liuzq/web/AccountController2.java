package com.liuzq.web;

import com.liuzq.service.AccountService2;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/account")
@Api(tags = "test",description = "test1111")
public class AccountController2 {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    AccountService2 accountService;

    @RequestMapping(value = "transfer", method = RequestMethod.GET)
    public String transfer(){
        return "OK!";
    }
}

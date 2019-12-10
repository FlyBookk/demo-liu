package com.liuzq.service;

import com.liuzq.dao.AccountMapper2;
import com.liuzq.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class AccountService2 {

    @Autowired
    AccountMapper2 accountMapper2;

    /**
     * @param
     * @Author liu
     * @Description
     * @Return void
     * @Exception
     * @Date 2019/11/26 17:30
     * @Version 1.0
     */
    @Transactional
    public void transfer() throws RuntimeException {
        accountMapper2.update(90, 1);//用户1减10块 用户2加10块
        int i = 1 / 0;
        accountMapper2.update(110, 2);
    }
}

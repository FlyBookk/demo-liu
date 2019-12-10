package com.liuzq.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: springboot-liuzq
 * @Package: com.liuzq.web
 * @ClassName: ThreadTestController
 * @Author: liuzq
 * @Description: 并发安全测试
 * @Date: 2019/12/5 11:23
 * @Version: 1.0
 */
@RestController
public class ThreadTestController {
    Logger logger= LoggerFactory.getLogger(ThreadTestController.class);
    Map<String,Integer> map=new HashMap();
    List list= new ArrayList();
    ConcurrentHashMap<String,Integer> con=new ConcurrentHashMap();
    @RequestMapping("/testHashMap")
    public Object getHashMapValue(){
        Integer a = map.get("a");
        if (a != null) {
            a++;
            map.put("a",a );
        } else {
            map.put("a",0);
        }
        Integer a1 = map.get("a");
        logger.info("hashMap-->{}",a1);
        return a1;
    }

    @RequestMapping("/testConcurrentHashMap")
    public Object get(){
        Integer a = con.get("a");
        if (a != null) {
            a++;
            con.put("a",a );
        } else {
            con.put("a",0);
        }
        Integer a1 = con.get("a");
        list.add(a1);
        logger.info("concurrentHashMap-->{}",a1);
        logger.info("list-->{}--->{}",list,list.size());
        return a1;
    }

}

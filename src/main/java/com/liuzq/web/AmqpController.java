package com.liuzq.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: springboot-liuzq
 * @Package: com.liuzq.web
 * @ClassName: AmqpController
 * @Author: liuzq
 * @Description: amqp链路测试,高版本的sleuth可以做到自己支持往header头添加链路头,具体添加细节看MessagePostProcessor的实现类及SpringRabbitTracing类处理
 * @Date: 2019/11/26 17:56
 * @Version: 1.0
 */
@RestController
public class AmqpController {
    Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * @Author liu
     * @param
     * @Description 发送消息
     * @Return java.lang.String
     * @Exception
     * @Date 2019/11/26 18:14
     * @Version  1.0
     */
    @RequestMapping("testq")
    public String testq(){
        logger.info("开始发送mq消息....");
        rabbitTemplate.convertAndSend("LemonChargeRuleNew1","testq");
        return "OK!";
    }

    /**
     * @Author liu
     * @param
     * @Description 接收消息
     * @Return void
     * @Exception
     * @Date 2019/11/26 18:15
     * @Version  1.0
     */
    @RabbitListener(queues = {"LemonChargeRuleNew1"})
    @RabbitHandler
    public void process(Message message, Channel channel) {
        logger.info("接受消息体....{}", JSON.toJSONString(message));
        String dataStr = new String(message.getBody());
        logger.info("接受消息内容....{}",dataStr);
    }
}

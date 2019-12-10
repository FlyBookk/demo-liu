package com.liuzq.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liuzq.bean.RabbitConfigLater;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: springboot-liuzq
 * @Package: com.liuzq.web
 * @ClassName: AmqpController
 * @Author: liuzq
 * @Description: amqp链路测试, 高版本的sleuth可以做到自己支持往header头添加链路头, 具体添加细节看MessagePostProcessor的实现类及SpringRabbitTracing类处理
 * @Date: 2019/11/26 17:56
 * @Version: 1.0
 */
@RestController
public class AmqpController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @param
     * @Author liu
     * @Description 发送消息
     * @Return java.lang.String
     * @Exception
     * @Date 2019/11/26 18:14
     * @Version 1.0
     */
    @RequestMapping("testq")
    public String testq() {
        logger.info("开始发送mq消息....");
        sendDelayMsg("asfasgasgasgasgasgasgasgas");
//        rabbitTemplate.convertAndSend("LemonChargeRuleNew1","testq");
        sendMsg();
        return "OK!";
    }

    /**
     * @param
     * @Author liu
     * @Description 接收消息
     * @Return void
     * @Exception
     * @Date 2019/11/26 18:15
     * @Version 1.0
     */
    @RabbitListener(queues = {RabbitConfigLater.DEFAULT_QUEUE_NAME_CLEARWAIT})
    @RabbitHandler
    public void process(Message message, Channel channel) {
        logger.info("接受消息体....{}", JSON.toJSONString(message));
        String dataStr = new String(message.getBody());
        try {
            channel.basicAck(message.getMessageProperties().getReceivedDelay(), false);
        } catch (IOException e) {
            logger.error("消息确认异常");
        }
        logger.info("接受消息内容....{}", dataStr);
    }

    public void sendMsg() {
        Map<String, String> mqParams = new HashMap<>();
        mqParams.put("serverName", "serverName");
        mqParams.put("scoreId", "scoreId");
        mqParams.put("ruleId", "ruleId");
        mqParams.put("orderId", "orderId");
        mqParams.put("appKey", "appKey");
        mqParams.put("originalAppKey", "originalAppKey");
        mqParams.put("apiId", "apiId");
        mqParams.put("name", "name");
        mqParams.put("phone", "phone");
        mqParams.put("idCard", "124124");
        // 设置队列名  发送
        rabbitTemplate.convertAndSend("obtainData.interface.dataClean.applyinfotest", JSONObject.toJSONString(mqParams));
    }


    public void sendDelayMsg(Object msg) {
        MessagePostProcessor processor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("15000");
                return message;
            }
        };
        if (msg instanceof String) {
            rabbitTemplate.convertAndSend(RabbitConfigLater.DEFAULT_EXCHANGE, RabbitConfigLater.DEFAULT_DEAD_LETTER_QUEUE_NAME_CLEARWAIT, (Object) msg, processor);
        } else {
            rabbitTemplate.convertAndSend(RabbitConfigLater.DEFAULT_EXCHANGE, RabbitConfigLater.DEFAULT_DEAD_LETTER_QUEUE_NAME_CLEARWAIT, (Object) JSON.toJSONString(msg), processor);
        }
    }

    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<>();
        for (int i = 1; i < 30; i++) {
            map.put(i+"",1);
        }
        System.out.println(map.size()+"---"+map.toString());
    }

}

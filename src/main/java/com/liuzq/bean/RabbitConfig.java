package com.liuzq.bean;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

/**
 * @author zhao
 * @date 2018/6/13.
 */

public class RabbitConfig {


    public static final String DEFAULT_EXCHANGE = "fk.topic.test";


    /**
     * 定义exchange
     */
    @Bean
    TopicExchange defaultExchange() {
        return new TopicExchange(RabbitConfig.DEFAULT_EXCHANGE);
    }


}

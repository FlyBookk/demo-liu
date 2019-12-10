package com.liuzq.bean;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhao
 * @date 2018/6/14.
 */

@Configuration
public class RabbitConfigLater extends RabbitConfig {

    public static final String DEFAULT_QUEUE_NAME_CLEARWAIT = "rabbit.topic.test.wait.go";
    public static final String DEFAULT_DEAD_LETTER_QUEUE_NAME_CLEARWAIT = "rabbit.topic.dead.letter.queue.test.wait";

    /**
     * 定义消息队列
     */
    @Bean
    public Queue clearWaitQueue() {
        return new Queue(DEFAULT_QUEUE_NAME_CLEARWAIT);
    }

    /**
     * 消息队列绑定exchange
     */
    @Bean
    Binding clearWaitQueueBindingExchange(Queue clearWaitQueue, TopicExchange defaultExchange) {
        return BindingBuilder.bind(clearWaitQueue).to(defaultExchange).with("#.test.wait.#");
    }


    /**
     * 延迟队列定义
     */
    @Bean
    public Queue clearWaitDeadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEFAULT_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "virtual.test_wait.a11_fraud");
        return new Queue(DEFAULT_DEAD_LETTER_QUEUE_NAME_CLEARWAIT, true, false, false, arguments);
    }

    /**
     * 延迟队列绑定exchange
     */
    @Bean
    public Binding clearWaitDeadLetterQueueBindingExchange(Queue clearWaitDeadLetterQueue, TopicExchange defaultExchange) {
        return BindingBuilder.bind(clearWaitDeadLetterQueue).to(defaultExchange).with("#.dead.letter.queue.test.wait.#");
    }

}

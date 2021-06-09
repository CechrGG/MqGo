package com.acmr;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class SyncProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("notice");
        producer.setNamesrvAddr("192.168.28.83:9876");
        try {
            producer.start();
            new Thread(() ->{
                while (true) {
                    Message message = new Message("Topic_Notice", "Tag_Warn",
                            ("Notice " + System.currentTimeMillis() + ", Hello Consumer").getBytes(StandardCharsets.UTF_8));
                    try {
                        SendResult sendResult = producer.send(message);
                        System.out.println(sendResult.toString());
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}

package com.lambarki.notification;

import com.lambarki.amqp.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.lambarki.notification",
                "com.lambarki.amqp"
        }
)
public class NotificationApplicaton {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplicaton.class, args);
    }

  /*  @Bean
    CommandLineRunner commandLineRunner(
            RabbitMQMessageProducer producer,
            NotificationConfig notificationConfig
    ) {
        return args -> {
            producer.publish(
                    new Person("Yassine", 37),
                    notificationConfig.getInternalExchange(),
                    notificationConfig.getInternalNotificationRoutingKey());
        };
    }

     record Person (String name, int age) {}*/
}

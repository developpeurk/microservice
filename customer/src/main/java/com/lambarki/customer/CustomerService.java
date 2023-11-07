package com.lambarki.customer;

import com.lambarki.amqp.RabbitMQMessageProducer;
import com.lambarki.clients.fraud.FraudCheckResponse;
import com.lambarki.clients.fraud.FraudClient;
import com.lambarki.clients.notification.NotificationClient;
import com.lambarki.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .build();

        //todo: check if email valid
        //todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster

        FraudCheckResponse fraudCheckResponse =  fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to YassineCode...",
                        customer.getFirstname())
        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key");
    }
}

package com.example.messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

//@Configuration
//@EnableWebSocketMessageBroker
public class ChatSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/private").withSockJS();
    }*/

    /**
     * This method should never be used by anyone except Spring Boot itself.
     * It sets up the following URL extensions:
     * "/app": Used to send info that will be changed by the server
     * "/topic" Used for subscribing to topics that you want messages from
     * "/queue" Used for sending messages to specific people
     */
    /*@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
    }*/
}

package com.craig.greggames.config.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
//@EnableWebSocket
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer  {

	// TODO Auto-generated method stub
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
//		.setTaskScheduler(heartBeatScheduler()).setHeartbeatValue(new long[] {5000,5000});
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ggsocket");
		registry.addEndpoint("/ggsocket").setAllowedOrigins("*").withSockJS();
	}
	
//	@Override
//	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//		
//		registration.setMessageSizeLimit(200*1024*1024);
//		registration.setSendTimeLimit(20*10000);
//		registration.setSendBufferSizeLimit(200*1024*1024);
//	}

	
	@Bean
    public TaskScheduler heartBeatScheduler() {
        return new ThreadPoolTaskScheduler();
    }



}

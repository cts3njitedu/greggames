//package com.craig.greggames.rabbit.game.cards.spades;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
//@EnableScheduling
//public class RabbitConfig {
//
//	static final String topicExchangeName = "customers.topic";
//	static final String queueName = "uid-craig";
//	
//	static final String ROUTING_KEY="greggy";
//	static final String EXCHANGE_NAME = "json-topic";
//	
//	static final String QUEUE_GENERIC_NAME="generic-queue";
//	static final String QUEUE_SPECIFIC_NAME = "specific-queue";
//	
//	@Bean
//	public Queue queue() {
//		
//		Map<String, Object> arguments = new HashMap<>();
//		arguments.put("x-dead-letter-exchange", "deadletters.fanout");
//		arguments.put("x-expires", 86400000);
//		arguments.put("x-message-ttl", 8640000);
//		return new Queue(queueName,false,false,true,arguments);
//	}
//	
//	@Bean
//	public Queue appQueueGeneric() {
//	    return new Queue(QUEUE_GENERIC_NAME);
//	}
//	 
//	@Bean
//	public Queue appQueueSpecific() {
//	    return new Queue(QUEUE_SPECIFIC_NAME);
//	}
//	
//	@Bean
//	public TopicExchange exchange() {
//		Map<String, Object> arguments = new HashMap<>();
//		arguments.put("alternate-exchange", "customers_alt.fanout");
//		return new TopicExchange(topicExchangeName,true,false,arguments);
//	}
//	
//	@Bean
//	public TopicExchange appExchange() {
//	    return new TopicExchange(EXCHANGE_NAME);
//	}
//	
//	@Bean
//	public Binding binding(Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with("sports.#");
//	}
//	
//	@Bean
//	public Binding declareBindingGeneric() {
//	    return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
//	}
//	 
//	@Bean
//	public Binding declareBindingSpecific() {
//	    return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY);
//	}
//	
//	
//	@Bean
//	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);
//        container.setConcurrentConsumers(5);
//        container.setMaxConcurrentConsumers(10);
////        container.setconse
//        
//        
//        
//        
//        
//        
//        
//        
////        
////        connectionFactory.setConcurrentConsumers(10);
////        connectionFactory.setMaxConcurrentConsumers(20);
////        factory.setConsecutiveActiveTrigger(1);
////        factory.setConsecutiveIdleTrigger(1);
////        factory.setPrefetchCount(100);
//        return container;
//    }
//	
//	@Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
//	
//	@Bean
//	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//	    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//	    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//	    return rabbitTemplate;
//	}
//	 
//	@Bean
//	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//	    return new Jackson2JsonMessageConverter();
//	}
//}

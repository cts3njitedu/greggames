//package com.craig.greggames.rabbit.game.cards.spades;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Receiver {
//
//	@Autowired
//	ConnectionFactory connectionFactory;
//	private static final Logger log = LoggerFactory.getLogger(Receiver.class);
//	
//	
//	@RabbitListener(queues = RabbitConfig.QUEUE_GENERIC_NAME)
//    public void receiveMessage(final Message message) {
//
//        log.info("Received message as generic: {}", message.toString());
//    }
// 
//    @RabbitListener(queues = RabbitConfig.QUEUE_SPECIFIC_NAME)
//    public void receiveMessage(final CustomMessage customMessage) {
//        log.info("Received message as specific class: {}", customMessage.toString());
//    }
//}

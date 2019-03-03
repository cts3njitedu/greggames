//package com.craig.greggames.rabbit.game.cards.spades;
//
//import java.util.Random;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Sender  {
//	private final RabbitTemplate rabbitTemplate;
//    private final Receiver receiver;
//
//    public Sender(Receiver receiver, RabbitTemplate rabbitTemplate) {
//        this.receiver = receiver;
//        this.rabbitTemplate = rabbitTemplate;
//    }
//    @Scheduled(fixedDelay = 3000L)
//    public void sendMessage() {
//        final CustomMessage message = new CustomMessage("Hello there!", new Random().nextInt(50), false);
//        System.out.println("Sending message...");
//        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, message);
//    }
//   
//}

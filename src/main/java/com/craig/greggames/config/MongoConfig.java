//package com.craig.greggames.config;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//
//@Configuration
//@EnableMongoRepositories("com.craig.greggames")
//public class MongoConfig extends AbstractMongoConfiguration {
//
//	@Value("${spring.data.mongodb.host}")
//	private String host;
//
//	@Value("${spring.data.mongodb.port}")
//	private int port;
//
//	@Value("${spring.data.mongodb.database}")
//	private String mongoDB;
//
//	@Value("${spring.data.mongodb.username}")
//	private String userName;
//
//	@Value("${spring.data.mongodb.password}")
//	private String password;
//
//	@Value("${spring.data.mongodb.socketTimeOutMS}")
//	private int socketTimeout;
//
//	@Value("${spring.data.mongodb.connectionTimeoutMS}")
//	private int connectionTimeout;
//
//	@Override
//	protected String getDatabaseName() {
//		// TODO Auto-generated method stub
//		return mongoDB;
//	}
//
//	@Override
//	@Bean
//	public Mongo mongo() throws Exception {
//		// TODO Auto-generated method stub
//		List<ServerAddress> servers = new ArrayList<ServerAddress>();
//		servers.add(new ServerAddress(host, port));
//
//		List<MongoCredential> creds = new ArrayList<MongoCredential>();
//		creds.add(MongoCredential.createCredential(userName, mongoDB, password.toCharArray()));
//
//		MongoClientOptions builder = MongoClientOptions.builder().socketTimeout(socketTimeout)
//				.connectTimeout(connectionTimeout).build();
//
//		return new MongoClient(servers, creds, builder);
//		
//	}
//
//}

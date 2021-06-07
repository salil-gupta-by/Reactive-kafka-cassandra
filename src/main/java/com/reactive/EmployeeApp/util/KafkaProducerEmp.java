package com.reactive.EmployeeApp.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.EmployeeApp.model.EmployeeRequest;
import com.reactive.EmployeeApp.service.EmployeeService;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;


@Component
public class KafkaProducerEmp {
	
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	private static final String TOPIC_NAME= "app_updates";
	
	private static final Logger log = LoggerFactory.getLogger(KafkaProducerEmp.class);



	public Disposable sendMessages(EmployeeRequest employeeRequest){
		
		Map<String,Object> producerProps = new HashMap<>();
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		
		SenderOptions<Integer,String> producerOptions = SenderOptions.create(producerProps);
		
		KafkaSender<Integer, String> kafkaProducer = KafkaSender.create(producerOptions);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String payload = null;
		try {
			payload = objectMapper.writeValueAsString(employeeRequest);
			
			log.debug("JSON String value " + payload);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		
	
		SenderRecord<Integer, String, Integer> message = SenderRecord.create(new ProducerRecord<>(TOPIC_NAME, payload),1);
			
		//return kafkaProducer.send(Mono.just(message)).next();
		
		return  kafkaProducer.send(Mono.just(message))
				.subscribe();
	}
	

}

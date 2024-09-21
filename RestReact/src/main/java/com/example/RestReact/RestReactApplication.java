package com.example.RestReact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestReactApplication.class, args);
	}

	//kafka
	//https://kafka.apache.org/documentation/
//	public static void main(String[] args) {
//		Properties properties = new Properties();
//		properties.put("bootstrap.servers", "localhost:9092"); // Your Kafka server
//		properties.put("key.serializer", StringSerializer.class.getName());
//		properties.put("value.serializer", StringSerializer.class.getName());
//
//		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
//		ProducerRecord<String, String> record = new ProducerRecord<>("your-topic", "key", "value");
//		producer.send(record);
//		producer.close();
//	}

//	public static void main(String[] args) {        Properties properties = new Properties();        properties.put("bootstrap.servers", "localhost:9092"); // Your Kafka server        properties.put("group.id", "your-group");        properties.put("key.deserializer", StringDeserializer.class.getName());        properties.put("value.deserializer", StringDeserializer.class.getName());        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);        consumer.subscribe(Collections.singletonList("your-topic"));        while (true) {            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));            records.forEach(record -> {                System.out.printf("Consumed message: key = %s, value = %s%n", record.key(), record.value());            });        }    }

}

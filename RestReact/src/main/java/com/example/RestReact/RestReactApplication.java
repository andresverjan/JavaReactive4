package com.example.RestReact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class RestReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestReactApplication.class, args);
		Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 3, 5);
		Flux<Integer> transformedFlux = numbersFlux.map(number -> {
			if (number == 3) {
				throw new RuntimeException("Encountered an error processing element: " + number);
			}
			else if (number == 1) {
				throw new RuntimeException("Encountered an error processing element: " + number);
			}
			return number * 2;
		});

		transformedFlux
				.doOnError(error -> {
			System.err.println("An error occurred: " + error.getMessage());
		}).subscribe(
				System.out::println,
// Handle errors emitted by the Flux
				error -> System.err.println("Error: " + error.getMessage())
		);
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

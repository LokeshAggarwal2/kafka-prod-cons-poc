import java.time.Duration
import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties

import scala.collection.JavaConverters._
import org.slf4j.{Logger, LoggerFactory}
class Consumer {
  val logger: Logger = LoggerFactory.getLogger("Consumer")
  def consumeFromKafka(topic: String) = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList(topic))
    while (true) {
      val record = consumer.poll(Duration.ofSeconds(5)).asScala
      for (data <- record.iterator) {
        logger.info("Consuming......")
        println(data.value())
        val df = data
      }
    }
  }
}
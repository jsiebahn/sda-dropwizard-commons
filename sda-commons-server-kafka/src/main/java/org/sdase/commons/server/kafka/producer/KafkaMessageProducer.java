package org.sdase.commons.server.kafka.producer;

import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.sdase.commons.server.kafka.prometheus.ProducerTopicMessageCounter;

public class KafkaMessageProducer<K, V> implements MessageProducer<K, V> {

  private final String topic;

  private final Producer<K, V> producer;

  private final ProducerTopicMessageCounter msgCounter;

  private final String producerName;

  public KafkaMessageProducer(
      String topic,
      Producer<K, V> producer,
      ProducerTopicMessageCounter msgCounter,
      String producerName) {
    this.producer = producer;
    this.topic = topic;
    this.msgCounter = msgCounter;
    this.producerName = producerName;
  }

  @Override
  public Future<RecordMetadata> send(K key, V value, Headers headers, Callback callback) {
    ProducerRecord<K, V> producerRecord = new ProducerRecord<>(topic, null, key, value, headers);
    msgCounter.increase(producerName, producerRecord.topic());
    return producer.send(producerRecord, new MonitoringCallback(msgCounter, producerName, callback));
  }

  public void close() {
    producer.close();
  }

  @Override
  public void flush() {
    producer.flush();
  }

  static class MonitoringCallback implements Callback {

    private final ProducerTopicMessageCounter messageCounter;
    private final String producerName;
    private final Callback delegate;

    public MonitoringCallback(ProducerTopicMessageCounter messageCounter, String producerName, Callback delegate) {
      this.messageCounter = messageCounter;
      this.producerName = producerName;
      this.delegate = delegate;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
      try {
        if (exception != null) {
          // increaseFailure needs implementation
          messageCounter.increaseFailure(producerName, metadata.topic());
        }
      } finally {
        delegate.onCompletion(metadata, exception);
      }
    }
  }
}

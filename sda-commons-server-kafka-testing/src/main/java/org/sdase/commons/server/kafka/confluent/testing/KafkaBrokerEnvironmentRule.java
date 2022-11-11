package org.sdase.commons.server.kafka.confluent.testing;

import com.salesforce.kafka.test.KafkaBroker;
import com.salesforce.kafka.test.junit4.SharedKafkaTestResource;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Rule for setting the Environment Variable `BROKER_CONNECTION_STRING`
 *
 * @deprecated please replace with the original {@link KafkaBrokerRule} and use standard config
 *     overrides when creating your {@link io.dropwizard.testing.junit.DropwizardAppRule}, e.g.
 *     {@code config("kafka.brokers", KAFKA::getKafkaConnectString)}
 */
@Deprecated
public class KafkaBrokerEnvironmentRule implements TestRule, KafkaBrokerRule {

  private final SharedKafkaTestResource kafka;
  static final String CONNECTION_STRING_ENV = "BROKER_CONNECTION_STRING";

  public KafkaBrokerEnvironmentRule(SharedKafkaTestResource brokerRule) {
    this.kafka = brokerRule;
  }

  @Override
  public Statement apply(Statement base, Description description) {

    return RuleChain.outerRule(kafka)
        .around(
            (base1, description1) ->
                new Statement() {
                  @Override
                  public void evaluate() throws Throwable {
                    setConnectionString(
                        String.format(
                            "[ %s ]",
                            getBrokerConnectStrings().stream()
                                .map(b -> "\"" + b + "\"")
                                .collect(Collectors.joining(", "))));
                    try {
                      base1.evaluate();
                    } finally {
                      clearConnectionString();
                    }
                  }
                })
        .apply(base, description);
  }

  @Override
  public String getConnectString() {
    return kafka.getKafkaConnectString();
  }

  @Override
  public List<String> getBrokerConnectStrings() {
    return kafka.getKafkaBrokers().stream()
        .map(KafkaBroker::getConnectString)
        .collect(Collectors.toList());
  }

  private void setConnectionString(String value) {
    System.setProperty(CONNECTION_STRING_ENV, value);
  }

  private void clearConnectionString() {
    System.clearProperty(CONNECTION_STRING_ENV);
  }
}

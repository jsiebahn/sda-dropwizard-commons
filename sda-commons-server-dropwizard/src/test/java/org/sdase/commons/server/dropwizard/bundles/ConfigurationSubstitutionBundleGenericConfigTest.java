package org.sdase.commons.server.dropwizard.bundles;

import static io.dropwizard.testing.ConfigOverride.randomPorts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

import com.codahale.metrics.annotation.ResponseMeteredLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.util.DataSize;
import io.dropwizard.util.Duration;
import java.util.List;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sdase.commons.server.kafka.KafkaConfiguration;

class ConfigurationSubstitutionBundleGenericConfigTest {

  @MethodSource
  @ParameterizedTest
  void shouldSetCommonValues(
      String givenKey, String givenValue, List<String> expectedPath, Object expectedValue)
      throws Throwable {
    try {
      System.setProperty(givenKey, givenValue);
      startAppToTestConfiguration(
          c -> {
            AbstractObjectAssert<?, ?> configAssert = assertThat(c);
            for (var pathSegment : expectedPath) {
              configAssert = configAssert.extracting(pathSegment);
            }
            configAssert.isEqualTo(expectedValue);
          });

    } finally {
      System.clearProperty(givenKey);
    }
  }

  static Stream<Arguments> shouldSetCommonValues() {
    return Stream.of(
        of("CHANGEDNAME", "Hello World!", List.of("originalName"), "Hello World!"),
        of("SERVER_GZIP_ENABLED", "true", List.of("server", "gzip", "enabled"), true),
        of("SERVER_GZIP_ENABLED", "false", List.of("server", "gzip", "enabled"), false),
        of(
            "SERVER_GZIP_BUFFERSIZE",
            "2mib",
            List.of("server", "gzip", "bufferSize"),
            DataSize.mebibytes(2)),
        of(
            "SERVER_SHUTDOWNGRACEPERIOD",
            "50s",
            List.of("server", "shutdownGracePeriod"),
            Duration.seconds(50)),
        of(
            "SERVER_RESPONSEMETEREDLEVEL",
            "ALL",
            List.of("server", "responseMeteredLevel"),
            ResponseMeteredLevel.ALL),
        of(
            "SERVER_APPLICATIONCONTEXTPATH",
            "/new-api",
            List.of("server", "applicationContextPath"),
            "/new-api"));
  }

  void startAppToTestConfiguration(ThrowingConsumer<Configuration> assertions) throws Throwable {
    var testSupport = new DropwizardTestSupport<>(TestApp.class, null, randomPorts());
    try {
      testSupport.before();
      TestApp application = testSupport.getApplication();
      assertions.acceptThrows(application.getConfiguration());
    } finally {
      testSupport.after();
    }
  }

  public static class TestApp extends Application<TestConfiguration> {

    private Configuration configuration;

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
      super.initialize(bootstrap);
      bootstrap.addBundle(ConfigurationSubstitutionBundle.builder().build());
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) {
      this.configuration = configuration;
    }

    public Configuration getConfiguration() {
      return configuration;
    }
  }

  @SuppressWarnings("unused")
  public static class TestConfiguration extends Configuration {
    @JsonProperty("changedName")
    private String originalName;

    private KafkaConfiguration kafka;

    @NotNull private String forTestingCommandOnly = "foo";

    public String getOriginalName() {
      return originalName;
    }

    public TestConfiguration setOriginalName(String originalName) {
      this.originalName = originalName;
      return this;
    }

    public KafkaConfiguration getKafka() {
      return kafka;
    }

    public TestConfiguration setKafka(KafkaConfiguration kafka) {
      this.kafka = kafka;
      return this;
    }

    public String getForTestingCommandOnly() {
      return forTestingCommandOnly;
    }

    public TestConfiguration setForTestingCommandOnly(String forTestingCommandOnly) {
      this.forTestingCommandOnly = forTestingCommandOnly;
      return this;
    }
  }
}
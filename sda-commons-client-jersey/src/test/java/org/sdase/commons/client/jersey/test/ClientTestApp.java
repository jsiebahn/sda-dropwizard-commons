package org.sdase.commons.client.jersey.test;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.forms.MultiPartBundle;
import io.opentelemetry.api.GlobalOpenTelemetry;
import org.sdase.commons.client.jersey.JerseyClientBundle;
import org.sdase.commons.client.jersey.oidc.OidcClient;
import org.sdase.commons.server.dropwizard.bundles.ConfigurationSubstitutionBundle;
import org.sdase.commons.server.jackson.JacksonConfigurationBundle;
import org.sdase.commons.server.trace.TraceTokenBundle;

public class ClientTestApp extends Application<ClientTestConfig> {

  private final JerseyClientBundle<ClientTestConfig> jerseyClientBundle =
      JerseyClientBundle.builder()
          .withConsumerTokenProvider(ClientTestConfig::getConsumerToken)
          .withOpenTelemetry(GlobalOpenTelemetry.get())
          .build();

  private OidcClient oidcClient;

  public static void main(String[] args) throws Exception {
    new ClientTestApp().run(args);
  }

  @Override
  public void initialize(Bootstrap<ClientTestConfig> bootstrap) {
    bootstrap.addBundle(ConfigurationSubstitutionBundle.builder().build());
    bootstrap.addBundle(JacksonConfigurationBundle.builder().build());
    bootstrap.addBundle(TraceTokenBundle.builder().build());
    bootstrap.addBundle(jerseyClientBundle);
    bootstrap.addBundle(new MultiPartBundle());
  }

  @Override
  public void run(ClientTestConfig configuration, Environment environment) {
    environment.jersey().register(this);
    environment
        .jersey()
        .register(
            new ClientTestEndPoint(
                jerseyClientBundle.getClientFactory(), configuration.getMockBaseUrl()));

    oidcClient = new OidcClient(jerseyClientBundle.getClientFactory(), configuration.getOidc());
  }

  public JerseyClientBundle<ClientTestConfig> getJerseyClientBundle() {
    return jerseyClientBundle;
  }

  public OidcClient getOidcClient() {
    return oidcClient;
  }
}

package org.sdase.commons.server.cors.test;

import javax.ws.rs.HttpMethod;
import io.dropwizard.core.setup.Bootstrap;
import org.sdase.commons.server.cors.CorsBundle;

public class CorsRestrictedTestApp extends CorsTestApp {

  public static void main(String[] args) throws Exception {
    new CorsRestrictedTestApp().run(args);
  }

  @Override
  public void initialize(Bootstrap<CorsTestConfiguration> bootstrap) {
    bootstrap.addBundle(
        CorsBundle.builder()
            .withCorsConfigProvider(CorsTestConfiguration::getCors)
            .withAdditionalAllowedHeaders("some")
            .withAdditionalExposedHeaders("exposed")
            .withAllowedMethods(HttpMethod.GET, HttpMethod.POST)
            .build());
  }
}

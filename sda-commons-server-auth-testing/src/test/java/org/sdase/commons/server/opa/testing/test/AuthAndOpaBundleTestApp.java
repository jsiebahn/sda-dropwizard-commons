package org.sdase.commons.server.opa.testing.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.sdase.commons.server.auth.AuthBundle;
import org.sdase.commons.server.dropwizard.bundles.ConfigurationSubstitutionBundle;
import org.sdase.commons.server.opa.OpaBundle;
import org.sdase.commons.server.opa.OpaJwtPrincipal;

public class AuthAndOpaBundleTestApp extends Application<AuthAndOpaBundeTestAppConfiguration> {

  @Override
  public void initialize(Bootstrap<AuthAndOpaBundeTestAppConfiguration> bootstrap) {
    bootstrap.addBundle(ConfigurationSubstitutionBundle.builder().build());
    bootstrap.addBundle(
        AuthBundle.builder()
            .withAuthConfigProvider(AuthAndOpaBundeTestAppConfiguration::getAuth)
            .withExternalAuthorization()
            .build());
    bootstrap.addBundle(
        OpaBundle.builder()
            .withOpaConfigProvider(AuthAndOpaBundeTestAppConfiguration::getOpa)
            .build());
  }

  @Override
  public void run(AuthAndOpaBundeTestAppConfiguration configuration, Environment environment) {
    environment.jersey().register(Endpoint.class);
  }

  @Path("/")
  public static class Endpoint {

    @Context SecurityContext securityContext;

    @GET
    @Path("resources")
    public Response get() {
      OpaJwtPrincipal principal = (OpaJwtPrincipal) securityContext.getUserPrincipal();

      PrincipalInfo result =
          new PrincipalInfo()
              .setName(principal.getName())
              .setJwt(principal.getJwt())
              .setSub(
                  principal.getClaims() == null
                      ? null
                      : principal.getClaims().get("sub").asString())
              .setConstraints(principal.getConstraintsAsEntity(ConstraintModel.class))
              .setConstraintsJson(principal.getConstraints());

      return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
    }
  }
}

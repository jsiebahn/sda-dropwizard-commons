package org.sdase.commons.server.starter;

import org.junit.Before;
import org.junit.Test;
import org.sdase.commons.server.security.SecurityBundle;
import org.sdase.commons.server.starter.test.BundleAssertion;

public class SecurityBuilderTest {

   private BundleAssertion<SdaPlatformConfiguration> bundleAssertion;

   @Before
   public void setUp() {
      bundleAssertion = new BundleAssertion<>();
   }

   @Test
   public void defaultSecuritySettings() {
      SdaPlatformBundle<SdaPlatformConfiguration> bundle = SdaPlatformBundle.builder()
            .usingSdaPlatformConfiguration()
            .withoutConsumerTokenSupport()
            .withSwaggerInfoTitle("Starter")
            .addSwaggerResourcePackageClass(this.getClass())
            .build();

      bundleAssertion.assertBundleConfiguredByPlatformBundle(bundle, SecurityBundle.builder().build());
   }

   @Test
   public void customizableLimitsSecuritySettings() {
      SdaPlatformBundle<SdaPlatformConfiguration> bundle = SdaPlatformBundle.builder()
            .usingSdaPlatformConfiguration()
            .withoutConsumerTokenSupport()
            .withSwaggerInfoTitle("Starter")
            .addSwaggerResourcePackageClass(this.getClass())
            .disableBufferLimitValidationSecurityFeature()
            .build();

      bundleAssertion.assertBundleConfiguredByPlatformBundle(
            bundle, SecurityBundle.builder().disableBufferLimitValidation().build());
   }
}

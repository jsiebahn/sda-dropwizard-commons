package org.sdase.commons.client.jersey.wiremock.testing;

import static com.github.tomakehurst.wiremock.client.WireMock.findUnmatchedRequests;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * @deprecated This test is still here to show migration from {@link
 *     org.sdase.commons.client.jersey.wiremock.testing.WireMockExtension} in its commit history.
 */
@Deprecated(forRemoval = true)
class WireMockExtensionTest {

  @RegisterExtension
  @SuppressWarnings("JUnitMalformedDeclaration") // intended to be a class extension
  WireMockExtension wire = new WireMockExtension();

  static Set<String> wireBaseUrls = new HashSet<>();

  @BeforeEach // Wiremock is reset for each test
  void before() {
    wireBaseUrls.add(wire.baseUrl());
    stubFor(
        get("/api/cars") // NOSONAR
            .withHeader("Accept", notMatching("gzip"))
            .willReturn(ok().withHeader("Content-type", "application/json").withBody("[]")));
  }

  @AfterAll
  static void afterAll() {
    assertThat(wireBaseUrls).hasSize(2);
  }

  @Test
  void shouldGetMockedResponse() throws Exception {
    URLConnection connection = new URL(wire.baseUrl() + "/api/cars").openConnection();
    try (InputStream inputStream = connection.getInputStream()) {
      assertThat(IOUtils.toString(inputStream, StandardCharsets.UTF_8)).isEqualTo("[]");
      assertThat(findUnmatchedRequests()).isEmpty();
    }
  }

  @Test
  void shouldGet404() throws Exception {
    HttpURLConnection connection =
        (HttpURLConnection) new URL(wire.baseUrl() + "/foo").openConnection();
    assertThat(connection.getResponseCode()).isEqualTo(404);
    assertThat(findUnmatchedRequests()).isNotEmpty();
  }
}

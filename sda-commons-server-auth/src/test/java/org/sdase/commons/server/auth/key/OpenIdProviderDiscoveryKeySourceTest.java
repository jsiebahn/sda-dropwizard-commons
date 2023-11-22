package org.sdase.commons.server.auth.key;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class OpenIdProviderDiscoveryKeySourceTest {

  @Test
  void shouldRethrowSameKeyloadFailedException() {

    KeyLoadFailedException keyLoadFailedException = new KeyLoadFailedException();

    Client client = mock(Client.class);
    doThrow(keyLoadFailedException).when(client).target(anyString());

    OpenIdProviderDiscoveryKeySource openIdProviderDiscoveryKeySource =
        new OpenIdProviderDiscoveryKeySource("uri", client, null);

    assertThatExceptionOfType(KeyLoadFailedException.class)
        .isThrownBy(openIdProviderDiscoveryKeySource::loadKeysFromSource)
        .isSameAs(keyLoadFailedException);
  }

  @Test
  void shouldCloseWebApplicationExceptionResponse() {

    Response response = mock(Response.class);
    Client client = mock(Client.class);
    WebApplicationException webApplicationException = mock(WebApplicationException.class);
    doReturn(response).when(webApplicationException).getResponse();
    doThrow(webApplicationException).when(client).target(anyString());
    OpenIdProviderDiscoveryKeySource openIdProviderDiscoveryKeySource =
        new OpenIdProviderDiscoveryKeySource("uri", client, null);

    assertThatExceptionOfType(KeyLoadFailedException.class)
        .isThrownBy(openIdProviderDiscoveryKeySource::loadKeysFromSource);

    verify(response, times(1)).close();
  }

  @Test
  void shouldHandleExceptionOnCloseWebApplicationException() {

    Response response = mock(Response.class);
    Client client = mock(Client.class);
    WebApplicationException webApplicationException = mock(WebApplicationException.class);
    doReturn(response).when(webApplicationException).getResponse();
    doThrow(webApplicationException).when(client).target(anyString());
    doThrow(new ProcessingException("Test")).when(response).close();
    OpenIdProviderDiscoveryKeySource openIdProviderDiscoveryKeySource =
        new OpenIdProviderDiscoveryKeySource("uri", client, null);

    assertThatExceptionOfType(KeyLoadFailedException.class)
        .isThrownBy(openIdProviderDiscoveryKeySource::loadKeysFromSource)
        .withCause(webApplicationException);
  }

  @Test
  void shouldWrapWebApplicationExceptionInKeyloadFailedException() {

    Response response = mock(Response.class);
    Client client = mock(Client.class);
    WebApplicationException webApplicationException = mock(WebApplicationException.class);
    doReturn(response).when(webApplicationException).getResponse();
    doThrow(webApplicationException).when(client).target(anyString());
    OpenIdProviderDiscoveryKeySource openIdProviderDiscoveryKeySource =
        new OpenIdProviderDiscoveryKeySource("uri", client, null);

    assertThatExceptionOfType(KeyLoadFailedException.class)
        .isThrownBy(openIdProviderDiscoveryKeySource::loadKeysFromSource)
        .withCause(webApplicationException);
  }

  @Test
  void shouldWrapAnyExceptionInKeyloadFailedException() {

    Exception e = new IllegalArgumentException();

    Client client = mock(Client.class);
    doThrow(e).when(client).target(anyString());

    OpenIdProviderDiscoveryKeySource openIdProviderDiscoveryKeySource =
        new OpenIdProviderDiscoveryKeySource("uri", client, null);

    assertThatExceptionOfType(KeyLoadFailedException.class)
        .isThrownBy(openIdProviderDiscoveryKeySource::loadKeysFromSource)
        .withCause(e);
  }
}

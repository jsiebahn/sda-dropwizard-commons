package org.sdase.commons.server.dropwizard.bundles.scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sdase.commons.server.dropwizard.bundles.scanner.JacksonTypeScanner.DROPWIZARD_PLAIN_TYPES;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.sdase.commons.server.kafka.KafkaConfiguration;
import org.sdase.commons.starter.SdaPlatformConfiguration;

class JacksonTypeScannerTest {

  JacksonTypeScanner jacksonTypeScanner =
      new JacksonTypeScanner(
          new ObjectMapper(new YAMLFactory()).disable(SerializationFeature.FAIL_ON_EMPTY_BEANS),
          DROPWIZARD_PLAIN_TYPES);

  @Test
  void shouldFindPossibleEnvs() {
    var actual = jacksonTypeScanner.createConfigurationHints(CustomConfig.class);
    assertThat(actual)
        .contains("AUTH_KEYLOADERCLIENT_TIMEOUT (Duration)")
        .contains("AUTH_KEYLOADERCLIENT_CONNECTIONTIMEOUT (Duration)")
        .contains("AUTH_KEYLOADERCLIENT_CONNECTIONREQUESTTIMEOUT (Duration)")
        .contains("AUTH_KEYLOADERCLIENT_TIMETOLIVE (Duration)")
        .contains("AUTH_KEYLOADERCLIENT_COOKIESENABLED (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_NORMALIZEURIENABLED (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_MAXCONNECTIONS (int)")
        .contains("AUTH_KEYLOADERCLIENT_MAXCONNECTIONSPERROUTE (int)")
        .contains("AUTH_KEYLOADERCLIENT_KEEPALIVE (Duration)")
        .contains("AUTH_KEYLOADERCLIENT_RETRIES (int)")
        .contains("AUTH_KEYLOADERCLIENT_USERAGENT (String)")
        .contains("AUTH_KEYLOADERCLIENT_VALIDATEAFTERINACTIVITYPERIOD (Duration)")
        .contains("AUTH_KEYLOADERCLIENT_MINTHREADS (int)")
        .contains("AUTH_KEYLOADERCLIENT_MAXTHREADS (int)")
        .contains("AUTH_KEYLOADERCLIENT_WORKQUEUESIZE (int)")
        .contains("AUTH_KEYLOADERCLIENT_GZIPENABLED (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_GZIPENABLEDFORREQUESTS (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_CHUNKEDENCODINGENABLED (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_HOST (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_SCHEME (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_AUTH_USERNAME (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_AUTH_PASSWORD (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_AUTH_AUTHSCHEME (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_AUTH_REALM (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_AUTH_HOSTNAME (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_AUTH_DOMAIN (String)")
        .contains("AUTH_KEYLOADERCLIENT_PROXY_AUTH_CREDENTIALTYPE (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_PROTOCOL (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_PROVIDER (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_KEYSTOREPATH (File)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_KEYSTOREPASSWORD (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_KEYSTORETYPE (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_KEYSTOREPROVIDER (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_TRUSTSTOREPATH (File)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_TRUSTSTOREPASSWORD (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_TRUSTSTORETYPE (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_TRUSTSTOREPROVIDER (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_TRUSTSELFSIGNEDCERTIFICATES (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_VERIFYHOSTNAME (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_CERTALIAS (String)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_VALIDKEYSTOREPASSWORD (boolean)")
        .contains("AUTH_KEYLOADERCLIENT_TLS_VALIDTRUSTSTOREPASSWORD (boolean)")
        .contains("AUTH_LEEWAY (long)")
        .contains("AUTH_DISABLEAUTH (boolean)")
        .contains("OPA_OPACLIENT_TIMEOUT (Duration)")
        .contains("OPA_OPACLIENT_CONNECTIONTIMEOUT (Duration)")
        .contains("OPA_OPACLIENT_CONNECTIONREQUESTTIMEOUT (Duration)")
        .contains("OPA_OPACLIENT_TIMETOLIVE (Duration)")
        .contains("OPA_OPACLIENT_COOKIESENABLED (boolean)")
        .contains("OPA_OPACLIENT_NORMALIZEURIENABLED (boolean)")
        .contains("OPA_OPACLIENT_MAXCONNECTIONS (int)")
        .contains("OPA_OPACLIENT_MAXCONNECTIONSPERROUTE (int)")
        .contains("OPA_OPACLIENT_KEEPALIVE (Duration)")
        .contains("OPA_OPACLIENT_RETRIES (int)")
        .contains("OPA_OPACLIENT_USERAGENT (String)")
        .contains("OPA_OPACLIENT_VALIDATEAFTERINACTIVITYPERIOD (Duration)")
        .contains("OPA_OPACLIENT_MINTHREADS (int)")
        .contains("OPA_OPACLIENT_MAXTHREADS (int)")
        .contains("OPA_OPACLIENT_WORKQUEUESIZE (int)")
        .contains("OPA_OPACLIENT_GZIPENABLED (boolean)")
        .contains("OPA_OPACLIENT_GZIPENABLEDFORREQUESTS (boolean)")
        .contains("OPA_OPACLIENT_CHUNKEDENCODINGENABLED (boolean)")
        .contains("OPA_OPACLIENT_PROXY_HOST (String)")
        .contains("OPA_OPACLIENT_PROXY_SCHEME (String)")
        .contains("OPA_OPACLIENT_PROXY_AUTH_USERNAME (String)")
        .contains("OPA_OPACLIENT_PROXY_AUTH_PASSWORD (String)")
        .contains("OPA_OPACLIENT_PROXY_AUTH_AUTHSCHEME (String)")
        .contains("OPA_OPACLIENT_PROXY_AUTH_REALM (String)")
        .contains("OPA_OPACLIENT_PROXY_AUTH_HOSTNAME (String)")
        .contains("OPA_OPACLIENT_PROXY_AUTH_DOMAIN (String)")
        .contains("OPA_OPACLIENT_PROXY_AUTH_CREDENTIALTYPE (String)")
        .contains("OPA_OPACLIENT_TLS_PROTOCOL (String)")
        .contains("OPA_OPACLIENT_TLS_PROVIDER (String)")
        .contains("OPA_OPACLIENT_TLS_KEYSTOREPATH (File)")
        .contains("OPA_OPACLIENT_TLS_KEYSTOREPASSWORD (String)")
        .contains("OPA_OPACLIENT_TLS_KEYSTORETYPE (String)")
        .contains("OPA_OPACLIENT_TLS_KEYSTOREPROVIDER (String)")
        .contains("OPA_OPACLIENT_TLS_TRUSTSTOREPATH (File)")
        .contains("OPA_OPACLIENT_TLS_TRUSTSTOREPASSWORD (String)")
        .contains("OPA_OPACLIENT_TLS_TRUSTSTORETYPE (String)")
        .contains("OPA_OPACLIENT_TLS_TRUSTSTOREPROVIDER (String)")
        .contains("OPA_OPACLIENT_TLS_TRUSTSELFSIGNEDCERTIFICATES (boolean)")
        .contains("OPA_OPACLIENT_TLS_VERIFYHOSTNAME (boolean)")
        .contains("OPA_OPACLIENT_TLS_CERTALIAS (String)")
        .contains("OPA_OPACLIENT_TLS_VALIDKEYSTOREPASSWORD (boolean)")
        .contains("OPA_OPACLIENT_TLS_VALIDTRUSTSTOREPASSWORD (boolean)")
        .contains("OPA_DISABLEOPA (boolean)")
        .contains("OPA_BASEURL (String)")
        .contains("OPA_POLICYPACKAGE (String)")
        .contains("KAFKA_DISABLED (boolean)")
        .contains("KAFKA_CONFIG_<KEY> (Map)")
        .contains("KAFKA_PRODUCERS_<KEY>_CLIENTID (String)")
        .contains("KAFKA_PRODUCERS_<KEY>_CONFIG_<KEY> (Map)")
        .contains("KAFKA_CONSUMERS_<KEY>_CLIENTID (String)")
        .contains("KAFKA_CONSUMERS_<KEY>_CONFIG_<KEY> (Map)")
        .contains("KAFKA_CONSUMERS_<KEY>_GROUP (String)")
        .contains("KAFKA_LISTENERCONFIG_<KEY>_INSTANCES (int)")
        .contains("KAFKA_LISTENERCONFIG_<KEY>_MAXPOLLINTERVAL (long)")
        .contains("KAFKA_LISTENERCONFIG_<KEY>_POLLINTERVAL (long)")
        .contains("KAFKA_LISTENERCONFIG_<KEY>_POLLINTERVALFACTORONERROR (long)")
        .contains("KAFKA_LISTENERCONFIG_<KEY>_TOPICMISSINGRETRYMS (long)")
        .contains("KAFKA_TOPICS_<KEY>_NAME (String)")
        .contains("KAFKA_SECURITY_USER (String)")
        .contains("KAFKA_SECURITY_PASSWORD (String)")
        .contains("KAFKA_SECURITY_PROTOCOL (enum ProtocolType)")
        .contains("KAFKA_SECURITY_SASLMECHANISM (String)")
        .contains("KAFKA_ADMINCONFIG_ADMINCLIENTREQUESTTIMEOUTMS (int)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_USER (String)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_PASSWORD (String)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_PROTOCOL (enum ProtocolType)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_SASLMECHANISM (String)")
        .contains("KAFKA_ADMINCONFIG_ADMINCLIENTREQUESTTIMEOUTMS (int)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_PASSWORD (String)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_PROTOCOL (enum ProtocolType)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_SASLMECHANISM (String)")
        .contains("KAFKA_ADMINCONFIG_ADMINSECURITY_USER (String)")
        .contains("KAFKA_ADMINCONFIG_CONFIG_<KEY> (Map)")
        .contains("KAFKA_HEALTHCHECK_TIMEOUTINSECONDS (int)")
        .contains("METRICS_FREQUENCY (Duration)")
        .contains("METRICS_REPORTONSTOP (boolean)")
        .contains("LOGGING_LEVEL (String)")
        .contains("SERVER_MAXTHREADS (int)")
        .contains("SERVER_MINTHREADS (int)")
        .contains("SERVER_MAXQUEUEDREQUESTS (int)")
        .contains("SERVER_IDLETHREADTIMEOUT (Duration)")
        .contains("SERVER_USER (String)")
        .contains("SERVER_GROUP (String)")
        .contains("SERVER_UMASK (String)")
        .contains("SERVER_SHUTDOWNGRACEPERIOD (Duration)")
        .contains("SERVER_ENABLETHREADNAMEFILTER (boolean)")
        .contains("SERVER_DUMPAFTERSTART (boolean)")
        .contains("SERVER_DUMPBEFORESTOP (boolean)")
        .contains("SERVER_ADMINMAXTHREADS (int)")
        .contains("SERVER_ADMINMINTHREADS (int)")
        .contains("SERVER_APPLICATIONCONTEXTPATH (String)")
        .contains("SERVER_ADMINCONTEXTPATH (String)")
        .contains("SERVER_SERVERPUSH_ENABLED (boolean)")
        .contains("SERVER_SERVERPUSH_ASSOCIATEPERIOD (Duration)")
        .contains("SERVER_SERVERPUSH_MAXASSOCIATIONS (int)")
        .contains("SERVER_RESPONSEMETEREDLEVEL (enum ResponseMeteredLevel)")
        .contains("SERVER_METRICPREFIX (String)")
        .contains("SERVER_ROOTPATH (String)")
        .contains("SERVER_GZIP_ENABLED (boolean)")
        .contains("SERVER_GZIP_MINIMUMENTITYSIZE (DataSize)")
        .contains("SERVER_GZIP_BUFFERSIZE (DataSize)")
        .contains("SERVER_GZIP_DEFLATECOMPRESSIONLEVEL (int)")
        .contains("SERVER_GZIP_GZIPCOMPATIBLEINFLATION (boolean)")
        .contains("SERVER_GZIP_SYNCFLUSH (boolean)")
        .contains("ADMIN_HEALTHCHECKS_SERVLETENABLED (boolean)")
        .contains("ADMIN_HEALTHCHECKS_MINTHREADS (int)")
        .contains("ADMIN_HEALTHCHECKS_MAXTHREADS (int)")
        .contains("ADMIN_HEALTHCHECKS_WORKQUEUESIZE (int)")
        .contains("ADMIN_TASKS_PRINTSTACKTRACEONERROR (boolean)")
        .contains("HEALTH_ENABLED (boolean)")
        .contains("HEALTH_INITIALOVERALLSTATE (boolean)")
        .contains("HEALTH_DELAYEDSHUTDOWNHANDLERENABLED (boolean)")
        .contains("HEALTH_SHUTDOWNWAITPERIOD (Duration)")
        .contains("HEALTH_RESPONDER_CACHECONTROLENABLED (boolean)")
        .contains("HEALTH_RESPONDER_CACHECONTROLVALUE (String)");
  }

  @SuppressWarnings("unused")
  public static class CustomConfig extends SdaPlatformConfiguration {

    private KafkaConfiguration kafka = new KafkaConfiguration();

    public KafkaConfiguration getKafka() {
      return kafka;
    }

    public CustomConfig setKafka(KafkaConfiguration kafka) {
      this.kafka = kafka;
      return this;
    }
  }
}

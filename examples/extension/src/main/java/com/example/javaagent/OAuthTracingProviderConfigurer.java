package com.example.javaagent;

import com.google.auto.service.AutoService;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizer;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;

@AutoService(AutoConfigurationCustomizerProvider.class)
public class OAuthTracingProviderConfigurer implements AutoConfigurationCustomizerProvider {

  private static final String TOKEN_ENDPOINT = "otel.javaagent.extensions.oidc.endpoint";
  private static final String CLIENT_ID = "otel.javaagent.extensions.oidc.clientid";
  private static final String CLIENT_SECRET = "otel.javaagent.oidc.clientsecret";
  private static final String JAVAAGENT_ENABLED_CONFIG = "otel.javaagent.enabled";


  @Override
  public void customize(AutoConfigurationCustomizer autoConfiguration) {
    autoConfiguration.addTracerProviderCustomizer(
        OAuthTracingProviderConfigurer::configure);
  }

  @CanIgnoreReturnValue
  private static SdkTracerProviderBuilder configure(
      SdkTracerProviderBuilder sdkTracerProviderBuilder, ConfigProperties config) {
    System.out.println("HERE!");

    if (!config.getBoolean(JAVAAGENT_ENABLED_CONFIG, true)) {
      return sdkTracerProviderBuilder;
    }

    System.out.printf("%s is the getString value for TOKEN_ENDPOINT", config.getString(TOKEN_ENDPOINT));
    System.out.printf("%s is the getString value for CLIENT_ID", config.getString(CLIENT_ID));

    if (config.getString(TOKEN_ENDPOINT) != null) {
      System.out.printf("%s is the endpoint", config.getString(TOKEN_ENDPOINT));
    }

    //maybeEnableLoggingExporter(sdkTracerProviderBuilder, config);

    return sdkTracerProviderBuilder;
  }
  @Override
  public int order() {
    return AutoConfigurationCustomizerProvider.super.order();
  }
}

package ua.gov.diia.client.example.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.gov.diia.client.CryptoServiceUAPKI;
import ua.gov.diia.client.crypto.api.CryptoService;
import ua.gov.diia.client.crypto.service.CryptoServiceIIT;
import ua.gov.diia.client.sdk.Diia;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class DiiaConfig {
    @Bean
    public Diia diia(
            @Value("${acquirerToken}") String acquirerToken,
            @Value("${diiaHost}") String diiaHost,
            @Value("${proxy.host:}") String proxyHost,
            @Value("${proxy.port:}") Integer proxyPort,
            @Value("${proxy.ssl.verifying:true}") boolean proxySslVerifying,
            CryptoService cryptoService
    ) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if(proxyHost != null && !proxyHost.isEmpty() && proxyPort != null) {
            httpClientBuilder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));

            if(!proxySslVerifying) {
                httpClientBuilder.hostnameVerifier((s, sslSession) -> true);
            }
        }

        HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
        loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        loggerInterceptor.redactHeader("authorization");

        OkHttpClient okHttpClient = httpClientBuilder
                .addInterceptor(loggerInterceptor)
                .build();

        return new Diia(acquirerToken, diiaHost, okHttpClient, cryptoService);
    }

    @Bean
    @ConditionalOnProperty(name = "crypto.service.implementation", havingValue = "UAPKI")
    public CryptoServiceUAPKI uapkiCryptoService(@Value("${configPath}") String configPath) {
        return new CryptoServiceUAPKI(configPath);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "crypto.service.implementation",
            havingValue = "IIT", matchIfMissing = true)
    public CryptoServiceIIT cryptoServiceIIT(
            @Value("${privateKeyStorage}") String privateKeyStorage,
            @Value("${privateKeyPath}") String privateKeyPath,
            @Value("${privateKeyPassword}") String privateKeyPassword,
            @Value("${certificatesStoragePath}") String certificatesStoragePath,
            @Value("${privateKeyBinaryBase64}") String privateKeyBase64
    ) {
        return new CryptoServiceIIT(
                privateKeyStorage, privateKeyPath, privateKeyPassword, certificatesStoragePath,
                privateKeyBase64);
    }
}

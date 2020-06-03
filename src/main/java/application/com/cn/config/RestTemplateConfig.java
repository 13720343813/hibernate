package application.com.cn.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * create restTemplate bean
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        // 设置读取超时时间
        clientHttpRequestFactory.setReadTimeout(60 * 1000);
        // 设置连接超时时间
        clientHttpRequestFactory.setConnectTimeout(60 * 1000);
        try {
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                    NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
            clientHttpRequestFactory.setHttpClient(httpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }
}

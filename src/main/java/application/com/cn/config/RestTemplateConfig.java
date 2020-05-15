package application.com.cn.config;

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
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }
}

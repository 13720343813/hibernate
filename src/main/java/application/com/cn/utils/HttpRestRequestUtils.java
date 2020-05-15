package application.com.cn.utils;

import org.apache.commons.collections.MapUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpRestRequestUtils {

    private RestTemplate restTemplate = (RestTemplate) ApplicationContextUtils.getBean("restTemplate");

    private HttpHeaders headers = new HttpHeaders();

    public HttpRestRequestUtils() {
    }

    public HttpRestRequestUtils(HttpHeaders headers) {
        this.headers = headers;
    }

    public String doGet(String url) {
        return request(url, HttpMethod.GET, null);
    }

    public String doPost(String url, Map<String, Object> params) {
        return request(url, HttpMethod.POST, params);
    }

    private String request(String url, HttpMethod method, Map<String, Object> params) {
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity entity = null;
            if (MapUtils.isEmpty(params)) {
                entity = new HttpEntity(headers);
            } else {
                entity = new HttpEntity(JsonUtils.toJson(params), headers);
            }
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, entity, String.class);
            if (null == responseEntity) {
                return null;
            }
            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

}

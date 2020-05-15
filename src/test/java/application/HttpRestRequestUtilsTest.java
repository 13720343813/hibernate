package application;

import application.com.cn.utils.HttpRestRequestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpRestRequestUtilsTest {

    @Test
    public void testRest(){
        HttpRestRequestUtils httpRestRequestUtils = new HttpRestRequestUtils();
        String url = "http://192.168.0.39:8090/rakutenOrderTrackingInfor/findByPage";
        Map<String, Object> query = new HashMap<>();
        query.put("transactionId", "317288946");
        Map<String, Object> request = new HashMap<>();
        request.put("query", query);
        String response = httpRestRequestUtils.doPost(url, request);
        System.out.println(response);
    }
}

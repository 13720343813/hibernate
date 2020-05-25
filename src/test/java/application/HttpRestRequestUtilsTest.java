package application;

import application.com.cn.orm.User;
import application.com.cn.utils.HttpRestRequestUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpRestRequestUtilsTest {

    static List<User> users = new ArrayList<>();

    static {
        users.add(new User("张三", 11, "zhangsan@qq.com"));
        users.add(new User("李四", 10, "lisi@qq.com"));
        users.add(new User("王五", 12, "wangwu@qq.com"));
    }

    @Test
    public void testRest() {
        HttpRestRequestUtils httpRestRequestUtils = new HttpRestRequestUtils();
        String url = "http://192.168.220.40:9010/sale/queryTikiProducts?api_key=MzUzYjMwMmM0NDU3NGY1NjUwNDU2ODdlNTM0ZTdkNmE6Mjg2OTI0Njk3ZTYxNWE2NzJhNjQ";
        Map<String, Object> query = new HashMap<>();
        query.put("page_size", 1000);
        Integer pageIndex = 0;
        while (true) {
            String response = httpRestRequestUtils.doPost(url, query);
            pageIndex++;
        }
    }

    @Test
    public void testJava8() {
        //过滤
        Long count = users.stream().filter(user -> (user.getAge() > 10)).count();
        System.out.println(count);

        System.out.println("====================================================");

        //排序
        List<User> temp = users.stream().sorted((a, b) -> a.getAge().compareTo(b.getAge())).collect(Collectors.toList());
        for (User user : temp) {
            System.out.println(user);
        }

        System.out.println("====================================================");

        //最大值和最小值
        User min = users.stream().min((a, b) -> a.getAge().compareTo(b.getAge())).get();
        System.out.println("min " + min);

        User max = users.stream().max((a, b) -> a.getAge().compareTo(b.getAge())).get();
        System.out.println("max " + max);

        System.out.println("====================================================");

        List<Integer> ages = users.stream().map(User::getAge).collect(Collectors.toList());
        ages.forEach(System.out::println);

        System.out.println("====================================================");

        //平均值
        Double avgAge = users.stream().collect(Collectors.averagingInt(User::getAge));
        System.out.println(avgAge);

        System.out.println("====================================================");

        //过滤为空的数据并拼接字符串
        String names = users.stream().filter(user -> StringUtils.isNotEmpty(user.getName())).map(User::getName).collect(Collectors.joining(",")).trim();
        System.out.println(names);

        System.out.println("====================================================");

        //将集合转成map
        Map<String, Integer> map = users.stream().collect(Collectors.toMap(User::getName, User::getAge));
        map.forEach((k, v) -> System.out.println("k : " + k + " v : " + v));

        System.out.println("====================================================");

        //分组
        Map<String, Long> groupMap = users.stream().collect(Collectors.groupingBy(User::getName, Collectors.counting()));
        groupMap.forEach((k, v) -> System.out.println("k : " + k + " v : " + v));

        System.out.println("====================================================");

        //查找重复字段
        List<String> nameList = users.stream().collect(Collectors.groupingBy(User::getName, Collectors.counting())).entrySet().stream()
                .filter(entry -> entry.getValue() > 1).map(entry -> entry.getKey()).collect(Collectors.toList());
        System.out.println(nameList.size());

    }
}

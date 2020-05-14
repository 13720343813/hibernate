package application;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    private static final String TRACK_NUMBER_EXIST_MARK = "The tracking number has already been given";
    @org.junit.Test
    public void test(){
        String xml = "<detail><![CDATA[Transporter doesn't exist.]]></detail>";

        xml = xml.substring(xml.indexOf("<detail>"), xml.indexOf("</detail>"));
        if (xml.indexOf(TRACK_NUMBER_EXIST_MARK) >= 0) {
            System.out.println(xml);
        } else {
            System.out.println(xml);
        }

    }
}

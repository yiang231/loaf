import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3", "", "4");
        List<HashMap<String, String>> mapList = Arrays.asList(new HashMap<String, String>() {{
            put("张三", "zhangsan");
            put("李四", "lisi");
        }});
        for (String item : list) {
            System.out.println("item = " + item);
        }
        for (HashMap<String, String> item : mapList) {
            System.out.println("item = " + item);
        }
    }
}

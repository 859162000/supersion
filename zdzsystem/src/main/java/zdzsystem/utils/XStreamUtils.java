package zdzsystem.utils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * @author veggieg
 * @since 2016-09-27 01:47
 */
public class XStreamUtils {
    public static String marshal(Object obj) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(obj.getClass());
        xstream.autodetectAnnotations(true);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xstream.toXML(obj);
        return CharMatcher.WHITESPACE.trimAndCollapseFrom(xml, ' ');
    }

    public static Object unmarshal(String path, Class<?> clazz) throws FileNotFoundException {
        Object obj = null;

        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(clazz);// 手工注册对象
        xstream.autodetectAnnotations(true);// 自动检查标注了 XStream 注解的 java 对象
        return  xstream.fromXML(new InputStreamReader(new FileInputStream(path), Charsets.UTF_8));
    }
}

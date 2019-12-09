package Factory.support;

import Factory.BeanDefinition;
import Factory.BeanFactory;
import com.sun.jdi.event.ClassUnloadEvent;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;


/**
 * @author YYDCYY
 * @create 2019-12-09
 */
public class DefaultBeanFactory implements BeanFactory {

   public static final String ID_ATTRIBUTE = "id";
   public static final String CLASS_ATTRIBUTE = "class";
   private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

   // 构造器
    public DefaultBeanFactory (String configFile) {
        loadBeanDefinition(configFile);
    }


    private void loadBeanDefinition(String configFile) {
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement(); // <beans> 获取根节点
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                Element ele = (Element) iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                // BeanDefinition 为接口, 需要自己实现一个类
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                this.beanDefinitionMap.put(id, bd);
            }
        } catch (DocumentException e) {
            // TODO 跑出异常
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if (bd == null){
            return null;
        }
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }

        return null;
    }
}

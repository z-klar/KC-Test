import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FieldMapper2 {

    private static final Logger log = LoggerFactory.getLogger(FieldMapper2.class);


    public static void copy(Object from, Object to) throws Exception {
        FieldMapper2.copy(from, to, Object.class);
    }

    public static void copy(Object from, Object to, Class depth) throws Exception {
        Class fromClass = from.getClass();
        Class toClass = to.getClass();
        List<Method> getters = collectGetters(fromClass, depth);
        String pureNameSrc;

        for (Method method : toClass.getDeclaredMethods()) {
            String methodName = method.getName();
            if(methodName.startsWith("set")) {
                String pureNameDst = methodName.substring(3);
                for(Method m : getters) {
                    String mn = m.getName();
                    if(mn.startsWith("get"))
                        pureNameSrc = m.getName().substring(3);
                    else
                        pureNameSrc = m.getName().substring(2);
                    if(pureNameDst.compareTo(pureNameSrc) == 0) {
                        String spom;
                        if(m.invoke(from) == null) spom = "NULL";
                        else spom = m.invoke(from).toString();
                        log.info("Mutual field: " + pureNameDst + "   Value: " + spom);

                        method.invoke(to, m.invoke(from));     // DST object, VALUE
                    }
                }
            }
        }
    }

    private static List<Method> collectGetters(Class c, Class depth) {
        List<Method> accessibleFields = new ArrayList<>();
        do {
            for (Method method : c.getDeclaredMethods()) {
                if (method.getName().startsWith("get")) {
                    accessibleFields.add(method);
                }
                else if(method.getName().startsWith("is")) {
                    accessibleFields.add(method);
                }
            }
            c = c.getSuperclass();
        } while (c != null && c != depth);
        return accessibleFields;
    }

}

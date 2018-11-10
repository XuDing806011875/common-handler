package com.dxt.common.omm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;

/**
 * 参数转换封装类
 */
public class Converter {

    private static Logger logger = LoggerFactory.getLogger(Converter.class);
    private static Map<Class, Map<String, Method>> cachedM = Collections.synchronizedMap(new HashMap<Class, Map<String, Method>>());

    /**
     * 转化 DynamicParameter 参数 为 ibatis example 对象
     *
     * @param dsp 原有查询条件
     * @param newExample 创建新的查询对象
     * @return
     */
    public static final Object paramToExampleExtendedNoException(DynamicSqlParameter dsp, Object newExample) {
        try {
            return paramToExampleExtended(dsp, newExample);
        } catch (Exception e) {
            logger.warn("转化 DynamicParameter 参数 为example 对象失败!", e);
        }
        return null;
    }

    public static final Object paramToExampleExtended(DynamicSqlParameter dsp, Object newExample) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        paramToExampleExtendedCriteria(dsp, newExample);
        return newExample;
    }

    public static final Object paramToExampleExtendedCriteria(DynamicSqlParameter dsp, Object newExample) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (dsp == null && newExample == null) return null;

        if (dsp.getEqual() != null && dsp.getEqual().get("modelName") != null)//删除之前所需的参数
            dsp.getEqual().remove("modelName");

        Object criteria = Converter.publicCall(newExample, "createCriteria", new Class[]{}, new Object[]{});
        if (criteria == null) throw new IllegalArgumentException("newExample is not Ibatis Example parameter.");
        if (dsp == null) return criteria;

        String exaName = newExample.getClass().getName();
        String beanName = (exaName.endsWith("Example")) ? exaName.substring(0, exaName.length() - 7) : ((exaName.endsWith("ExampleExtended")) ? exaName.substring(0, exaName.length() - 15) : null);
        if (beanName == null)
            throw new IllegalArgumentException("newExample :" + exaName + " , cann't found bean  XXXExampleExtended.");

        Class beanClass = Class.forName(beanName);

        if (beanClass == null) throw new IllegalArgumentException("newExample :" + exaName + " , cann't found bean");


        Map<String, String> params = null;
        //// handle  EqualTo
        params = dsp.getEqual();
        if (params != null) andCriteria(criteria, beanClass, params, "EqualTo");

        params = dsp.getNotequal();
        if (params != null) andCriteria(criteria, beanClass, params, "NotEqualTo");

        params = dsp.getLike();
        if (params != null) andCriteria(criteria, beanClass, params, "Like");

        Map<String, Object> temp = dsp.getInMap();
        if (temp != null) {
            params = new HashMap<String, String>();
            for (String key : temp.keySet()) {
                Object tempv = temp.get(key);
                if (tempv instanceof Object[]) {
                    tempv = Arrays.asList((Object[]) tempv);
                }
                if (tempv instanceof List) {
                    List invals = (List) tempv;
                    for (int i = 0; i < invals.size(); i++) {
                        if (i == 0) {
                            tempv = "" + invals.get(i);
                        } else {
                            tempv = tempv + "," + invals.get(i);
                        }
                    }
                }

                params.put(key, tempv.toString());
            }
            andCriteria(criteria, beanClass, params, "In");
        }

        Map<String, Object> nottemp = dsp.getNotInMap();
        if (nottemp != null) {
            params = new HashMap<String, String>();
            for (String key : nottemp.keySet()) {
                Object tempv = nottemp.get(key);
                if (tempv instanceof Object[]) {
                    tempv = Arrays.asList((Object[]) tempv);
                }
                if (tempv instanceof List) {
                    List invals = (List) tempv;
                    for (int i = 0; i < invals.size(); i++) {
                        if (i == 0) {
                            tempv = "" + invals.get(i);
                        } else {
                            tempv = tempv + "," + invals.get(i);
                        }
                    }
                }

                params.put(key, tempv.toString());
            }
            andCriteria(criteria, beanClass, params, "NotIn");
        }

        paramToExampleSetStartwith(dsp, criteria, beanClass);

        paramToExampleSetEndwith(dsp, criteria, beanClass);

        newExample = paramToExampleSetOrderByClause(dsp, newExample);

        if (dsp.getPage() != null&&dsp.getPage() != 0) {
            Page page = new Page();
            if (dsp.getPage()!=null&&dsp.getPage()>0&&dsp.getRows()!=null&&dsp.getRows() > 0) {
//                page.setBegin(dsp.getPage());
//                page.setLength(dsp.getRows());
                page.setBegin((dsp.getPage()-1)*dsp.getRows());
                page.setLength(dsp.getRows());
                Converter.publicCall(newExample, "setPage", new Class[]{Page.class}, new Object[]{page});
            }
        }
        return criteria;
    }

    public static final void paramToExampleSetStartwith(DynamicSqlParameter dsp, Object criteria, Class beanClass) throws InvocationTargetException, IllegalAccessException {
        Map<String, String> params = dsp.getStartwith();
        if (params != null) {
            andCriteria(criteria, beanClass, params, "GreaterThanOrEqualTo");
        }
    }

    public static final void paramToExampleSetEndwith(DynamicSqlParameter dsp, Object criteria, Class beanClass) throws InvocationTargetException, IllegalAccessException {
        Map<String, String> params = dsp.getEndwith();
        if (params != null) {
            andCriteria(criteria, beanClass, params, "LessThanOrEqualTo");
        }
    }

    public static final <T> T paramToExampleSetOrderByClause(DynamicSqlParameter dsp, T newExample) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String orderField = dsp.getOrder();
        String orderSort = dsp.getSort();
        if (!StringUtils.isEmpty(orderField) && !StringUtils.isEmpty(orderSort)) {
            try {
                PropertyDescriptor m = new PropertyDescriptor("orderByClause", newExample.getClass());
                Method writeMethod = m.getWriteMethod();
                String oracleField = getOracleFiled(orderField);
                if (!StringUtils.isEmpty(orderField) && !StringUtils.isEmpty(orderSort)) {
                    writeMethod.invoke(newExample, oracleField + " " + orderSort + "");
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
        return newExample;
    }

    private static String getOracleFiled(String orderField) {
        char[] charArray = orderField.toCharArray();
        StringBuffer bu = new StringBuffer();
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isUpperCase(charArray[i]) && i != 0) {
                bu.append("_").append(charArray[i]);
            } else {
                bu.append(charArray[i]);
            }
        }
        return bu.toString();
    }

    private static void andCriteria(Object criteria, Class beanClass, Map<String, String> params, String operator) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (params != null) {
            for (String key : params.keySet()) {
                //ignore the "" string
                String v = params.get(key);
                if (v == null || "".equals(v.trim())) continue;
                String mkey = (key.charAt(0) + "").toUpperCase() + key.substring(1);
                Method getM = Converter.getMethod(beanClass, "get" + mkey, false);
                if (getM == null) {
                    logger.warn("当拼写查询条件时有无效属性,没有找到对应的GET方法：" + key);
                    continue;
                }
                Class fieldClaz = getM.getReturnType();
                Method m = Converter.getMethod(criteria.getClass(), "and" + mkey + operator, false);
                if (m == null) {
                    continue;
                }
                Class[] pts = m.getParameterTypes();
                Object[] vals = new Object[pts.length];
                int i = 0;
                for (Class pt : pts) {
                    vals[i] = convertType(pt, fieldClaz, params.get(key));
                    i++;
                }
                m.invoke(criteria, vals);
            }
        }
    }

    private static Object convertType(Class claz, Class fieldclaz, String v) {
        if (Long.class.equals(claz)) {
            return Long.valueOf(v);
        } else if (Integer.class.equals(claz)) {
            return Integer.valueOf(v);
        } else if (Short.class.equals(claz)) {
            return Short.valueOf(v);

        } else if (BigDecimal.class.equals(claz)) {
            return new BigDecimal(v);

        } else if (List.class.equals(claz)) {
            StringTokenizer stoken = new StringTokenizer(v, ",");
            List ret = new ArrayList();
            while (stoken.hasMoreTokens()) {
                ret.add(convertType(fieldclaz, fieldclaz, stoken.nextToken()));
            }
            return ret;
        } else if(Date.class.equals(claz)){
            return new Date(Long.valueOf(v));
        }else{
            return v;
        }
    }

    public static final Method getMethod(Class claz, String methodname, boolean isStatic) {
        if (methodname == null) return null;
        if (cachedM.containsKey(claz) && cachedM.get(claz).containsKey(methodname.toLowerCase())) {
            return cachedM.get(claz).get(methodname.toLowerCase());
        }

        Map<String, Method> methods = cachedM.get(claz);
        if (methods == null) {
            methods = new HashMap<String, Method>();
            cachedM.put(claz, methods);
        }

        for (Method m : claz.getMethods()) {
            if (!Modifier.isPublic(m.getModifiers()) || isStatic && !Modifier.isStatic(m.getModifiers()) || !isStatic && Modifier.isStatic(m.getModifiers())) {
                continue;
            }

            if (m.getName().equalsIgnoreCase(methodname)) {
                methods.put(m.getName().toLowerCase(), m);
                return m;
            }

        }

        return null;
    }

    public static final Object publicCall(Object This, String method, Class[] ptypes, Object[] params) {

        try {
            Method m = This.getClass().getMethod(method, ptypes);
            if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
                return m.invoke(This, params);
            }
        } catch (SecurityException e) {
            logger.warn("反射方法值失败!", e);
        } catch (NoSuchMethodException e) {
            logger.warn("反射方法值失败!", e);
        } catch (IllegalArgumentException e) {
            logger.warn("反射方法值失败!", e);
        } catch (IllegalAccessException e) {
            logger.warn("反射方法值失败!", e);
        } catch (InvocationTargetException e) {
            logger.warn("反射方法值失败!", e);
        }
        return null;
    }
}

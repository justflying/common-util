package com.self.common.old;



import com.novel.cn.aspetct.annotion.Alias;
import com.novel.cn.common.enums.WXResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class PayCenterUtil {

    private static final Logger logger = LoggerFactory.getLogger(PayCenterUtil.class);

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, Object> xmlToMap(String strXML) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            logger.warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
        }
       return data;
    }


    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<Object, Object> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (Object key: data.keySet()) {
            Object value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.toString().trim();
            org.w3c.dom.Element filed = document.createElement(key.toString());
            filed.appendChild(document.createTextNode(value.toString()));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }


    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }


    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    public static <T> Map<String, Object> beanToMap(T valueClass) throws Exception {
        Map<String, Object> map=new HashMap<String,Object>();
        Field[] fields=getAllFields(valueClass);
        for(Field field:fields){
            field.setAccessible(true);
            Boolean isAliasAnnotation=field.isAnnotationPresent(Alias.class);
            if(isAliasAnnotation){
                Alias alias=field.getAnnotation(Alias.class);
                /*校验类型是否为空*/
                if("N".equals(alias.isNull())&&!field.getName().equals(WXResponseEnum.SIGN.getCode())){
                    Assert.notNull(field.get(valueClass),valueClass.getClass()+"中"+field.getName()+"属性不能为空");
                }
                /*根据别名赋值*/
                if(StringUtils.isEmpty(alias.name())){
                    map.put(field.getName(),String.valueOf(field.get(valueClass)));
                }else{
                    map.put(alias.name(),String.valueOf(field.get(valueClass)));
                }
            }else{
                map.put(field.getName(),String.valueOf(field.get(valueClass)));
            }
        }
        return sortMapByKey(map);
    }

    public static <T> T beanToMap(Map<String,String> params,Class<?> beanClass) throws Exception {
        if (params == null){return null;}
        T obj = (T)beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){continue;}
            field.setAccessible(true);
            field.set(obj, params.get(field.getName()));
        }
        return obj;
    }


    /*获取父类所有的属性*/
    public static <T> Field[] getAllFields(T object){
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + API_KEY);

        //算出摘要
        String mysign = MD5Utils.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();
        return tenpaySign.equals(mysign);
    }

    /**
     * @author
     * @date 2016-4-22
     * @Description：sign签名
     * @param characterEncoding
     *            编码格式
     *            请求参数
     * @return
     */
    public static <T> String createSign(String characterEncoding, T bean, String API_KEY) throws Exception {
        Map<String, Object> beanMap = beanToMap(bean);
        Map<String, Object> signMap = sortMapByKey(beanMap);
        StringBuffer sb = new StringBuffer();
        Set es = signMap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=").append(API_KEY);
        return MD5(sb.toString()).toUpperCase();
    }

    public static String createSign(Map<String,Object> signMap, String API_KEY){
        StringBuffer sb = new StringBuffer();
        signMap = sortMapByKey(signMap);
        Set es = signMap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=").append(API_KEY);
        return MD5(sb.toString()).toUpperCase();
    }



    public static Map<String, Object> sortMapByKey(Map<String, Object> valueMap) {
        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                    }
                });
        sortMap.putAll(valueMap);
        return sortMap;
    }



    /**
     * @author
     * @date 2016-4-22
     * @Description：将请求参数转换为xml格式的string
     * @param parameters
     *            请求参数
     * @return
     */
    public static String getRequestXml(Map<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length
     *            int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     *
     * @return String
     */
    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return outFormat.format(now);
    }

    /*生产微信支付随机字符串*/
    public static String buildNonceStr(){
        String currTime = getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = buildRandom(4) + "";
        return strTime + strRandom;
    }

    /**
     * 获取request 里的参数
     * @param request
     * @return
     */
    public static Map<String,String> getParams(HttpServletRequest request){
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error("获取支付宝回调参数HttpServletRequest错误{}",e.getMessage());
            }
            log.info("解析request值name{}和value{}",name,valueStr);
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 生成订单流水号
     * @param busCode   支付业务编码
     * @param nowDate   系统时间
     * @param userId    用户id
     * @return  业务编码 + 系统时间yyyyMMddHHmmss + 左补0的九位用户id
     */
    public static String getTradeNumber(String busCode, String nowDate, Long userId) {
        StringBuffer tradeNumber = new StringBuffer(busCode);
        tradeNumber.append(nowDate);
        tradeNumber.append(String.format("%09d", userId));
        return tradeNumber.toString();
    }
}

/**
 * Copyright 2016 
 *
 * All righ reserved
 *
 * create on 2016年8月13日 上午9:12:07
 *
 * auth Administrator
 *
 */
package com.self.common.old;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Map;


public class JsonTool {
	
	/**
	 * Desc: map Class to json String
	 *  
	 * @param  data
	 * @return json string
	 */
	public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw,data);
            return sw.toString();
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
	
	/**
     * Desc: map Class to json String with null
     *  
     * @param data
     * @return json string
     */
    public static String dataToJsonWithNull(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			//mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw,data);
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException("IOException from a StringWriter?");
		}
    }

	/**
	 * Desc: parse json string to map
	 *  
	 * @param data string
	 * @return Map
	 */
	public static Map<String,Object> jsonToMap(String data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(data,Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Desc: parse Json to dedicated class
	 *  
	 * @param data:  Json string
	 * @param valueClass: object.Class
	 * @param fullmap: ignore empty properties if false
	 * @return
	 */
	public static <V> V jsonToObject(String data, Class<V> valueClass, boolean fullmap) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (!fullmap) {
				objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			}
			return objectMapper.readValue(data, valueClass);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;   
    }
	
	/**
	 * Desc: parse Json to dedicated class
	 *  
	 * @param data:  Json string
	 * @param valueClass: object.Class
	 * @return
	 */
	public static <V> V jsonToObject(String data, Class<V> valueClass) {
		return jsonToObject(data, valueClass,false);
    }
	
	/**
	 * Desc:
	 *  
	 * @param data
	 * @param typeReference
	 * @param fullmap : ignore empty properties if false
	 * @return
	 */
	public static Object setJsonToObject(String data, TypeReference typeReference,
			boolean fullmap) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (!fullmap) {
				objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			}
			return objectMapper.readValue(data, typeReference);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}   
}

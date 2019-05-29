package com.self.common.old;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
public class AjaxPageResult implements Serializable {
	
	private final static Logger logger = LoggerFactory.getLogger(AjaxPageResult.class);
	
	private static final long serialVersionUID = 6681841768477488492L;
	
	private static final String DEFAULT_SUCCESS_CODE = "1";
	
	private static final String DEFAULT_FAIL_CODE = "0";
	
	private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	
	private static final String DEFAULT_FAIL_MESSAGE = "操作失败";
	
	private static final String DEFAULT_EXCEPTION_MESSAGE = "服务器繁忙,请稍后重试";
	
	/** 状态 */
	private boolean success = false;
	
	/** 结果码 */
	private String code;
	
	/** 应答信息 */
	private String message;
	
	/** 返回详情数据 */
	private Object data;

	private BasePage basePage;

	public AjaxPageResult() {
	}

	public AjaxPageResult(boolean success, String code, String message, Object data) {
		this.code = code;
		this.success = success;
		if (hasException(message) && !success) {
			this.message = DEFAULT_EXCEPTION_MESSAGE;
		} else {
			this.message = message;
		}
		this.data = data;
		logger.debug("请求应答>>>code：{}，message：{}", code, message);
	}

	public AjaxPageResult(boolean success, String code, String message, Object data, BasePage basePage) {
		this.code = code;
		this.success = success;
		if (hasException(message) && !success) {
			this.message = DEFAULT_EXCEPTION_MESSAGE;
		} else {
			this.message = message;
		}
		this.data = data;
		this.basePage = basePage;
		logger.debug("请求应答>>>code：{}，message：{}", code, message);
	}

	public static boolean hasException(String message) {
		return StringUtils.hasText(message) && message.indexOf("Exception") >= 0;
	}


	/**
	 * 返回成功信息到客户端
	 * @param code 返回码
	 * @param message 描述信息
	 * @param data 还回的数据对象
	 * @return
	 */
	public static AjaxPageResult success(String code, String message, Object data) {
		return new AjaxPageResult(true, code, message, data);
	}
	
	/**
	 * 返回成功信息到客户端
	 * @param message 描述信息
	 * @param data 还回的数据对象
	 * @return
	 */
	public static AjaxPageResult success(String message, Object data) {
		return success(DEFAULT_SUCCESS_CODE, message, data);
	}

	public static AjaxPageResult success(String message, Object data,BasePage basePage) {
		return new AjaxPageResult(true, DEFAULT_SUCCESS_CODE, message, data,basePage);
	}
	
	/**
	 * 返回成功信息到客户端 描述信息固定为<code>DEFAULT_SUCCESS_MESSAGE</code>
	 * @param data 返回的数据对象
	 * @return
	 */
	public static AjaxPageResult success(Object data) {
		return success(DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	/**
	 * 返回成功信息到客户端 描述信息固定为<code>DEFAULT_SUCCESS_MESSAGE</code>
	 * @param message 返回的描述信息
	 * @return
	 */
	public static AjaxPageResult success(String message) {
		return success(message, null);
	}
	
	/**
	 * 返回失败信息到客户端 描述信息固定为<code>DEFAULT_fail_MESSAGE</code>
	 * @param data 返回的数据对象
	 * @return
	 */
	public static AjaxPageResult fail(Object data) {
		return fail(DEFAULT_FAIL_MESSAGE, data);
	}
	
	/**
	 * 返回成功信息到客户端
	 * @param message 描述信息
	 * @param data 还回的数据对象
	 * @return
	 */
	public static AjaxPageResult fail(String message, Object data) {
		return fail(DEFAULT_FAIL_CODE, message, data);
	}
	
	/**
	 * 返回成功信息到客户端
	 * @param message 描述信息
	 * @return
	 */
	public static AjaxPageResult fail(String message) {
		return fail(DEFAULT_FAIL_CODE, message, null);
	}
	
	/**
	 * 返回成功信息到客户端
	 * @param code 返回码
	 * @param message 描述信息
	 * @param data 还回的数据对象
	 * @return
	 */
	public static AjaxPageResult fail(String code, String message, Object data) {
		return new AjaxPageResult(false, code, message, data);
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public AjaxPageResult setData(Object data) {
		this.data = data;
		return this;
	}

}

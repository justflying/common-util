/**   
 * All rights Reserved, Designed By MiGu  
 * Copyright:    Copyright(C) 2016-2020  
 * Company       MiGu  Co., Ltd.
*/
package com.self.common.old;

import lombok.Data;

/**
 * @ClassName: BaseForm  
 * @Description:分页查询基础类（分页查询时-查询条件请继承该类）
 * @author le
 * @date 2017年3月30日     
 */  
@Data
public class BasePage {
	
	/**
	 *	分页时用到的变量
	 *	pageNo      : 当前页页码，当查询最后一页的数据时，前台组件相对应的该值设为-1
	 *	pageSize    : 每页显示的记录数
	 *	pages       : 总页数，只有当用户查询最后一页数据时由系统自己填入。查询其它页时，total的值应为0
	 *	rowSrt      : 起始行号
	 *	rowEnd      : 结束行号 
	 *	counts      : 总记录数
	 *	sortFields  : 排序字段
	 */
	private int pageNo ;
	private int pageSize ;
	private int pages ;
	private int rowSrt;
	private int rowEnd;
	private int counts;
	private String sortFields;
}

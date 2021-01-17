package com.gmail.chibitopoochan.soqlexec.soql;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * クエリの構文情報
 */
public class SOQL {
	public static final List<String> SUMMARY_FUNCTION = Collections.unmodifiableList(Arrays.asList(
			"count","avg","max","min","count_distinct"));

	private String objectName = new String();
	private List<SOQLField> selectFieldList = new LinkedList<>();
	private List<SOQLField> orderByFieldList = new LinkedList<>();
	private List<SOQLField> groupByFieldList = new LinkedList<>();
	private String having = "";
	private String where = "";
	private int limit = 0;
	private int offset = 0;

	public void addSelectField(SOQLField field) {
		this.selectFieldList.add(field);
	}

	public List<SOQLField> getSelectFields() {
		return selectFieldList;
	}

	public void setFromObject(String name) {
		this.objectName = name;
	}

	public String getFromObject() {
		return objectName;
	}

	public void addOrderByField(SOQLField field) {
		this.orderByFieldList.add(field);
	}

	public List<SOQLField> getOrderByFields() {
		return orderByFieldList;
	}

	public void addGroupByField(SOQLField field) {
		this.groupByFieldList.add(field);
	}

	public List<SOQLField> getGroupByFields() {
		return groupByFieldList;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append(selectFieldList.stream().map(f -> f.toString()).collect(Collectors.joining(",")));
		builder.append(" FROM ").append(objectName);
		if(!where.isEmpty())	builder.append(" WHERE ").append(where);
		if(!having.isEmpty())	builder.append(" HAVING ").append(having);
		if(limit != 0)			builder.append(" LIMIT ").append(limit);
		if(offset != 0)			builder.append(" OFFSET ").append(offset);
		return builder.toString();
	}

	/**
	 * @return having
	 */
	public String getHaving() {
		return having;
	}

	/**
	 * @param having セットする having
	 */
	public void setHaving(String having) {
		this.having = having;
	}

	/**
	 * @return where
	 */
	public String getWhere() {
		return where;
	}

	/**
	 * @param where セットする where
	 */
	public void setWhere(String where) {
		this.where = where;
	}

	/**
	 * @return limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit セットする limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset セットする offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

}

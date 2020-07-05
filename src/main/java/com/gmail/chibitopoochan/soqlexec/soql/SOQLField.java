package com.gmail.chibitopoochan.soqlexec.soql;

import java.util.Optional;

public class SOQLField {
	private String apiName;
	private Optional<String> label = Optional.empty();
	private Optional<SOQLField> relation = Optional.empty();
	private Optional<SOQL> subquery = Optional.empty();

	public SOQLField(String name) {
		this.apiName = name;
	}

	public void setLabel(String label) {
		this.label = Optional.of(label);
	}

	public void setRelation(SOQLField rel) {
		this.relation = Optional.of(rel);
	}

	public void setSubQuery(SOQL soql) {
		this.subquery = Optional.of(soql);
	}

	public String getName() {
		return apiName;
	}

	public String getLabel() {
		return label.orElse(apiName);
	}

	public Optional<SOQLField> getRelation() {
		return relation;
	}

	public Optional<SOQL> getSubQuery() {
		return subquery;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(subquery.isPresent()) {
			builder.append("(");
			builder.append(subquery.get());
			builder.append(")");
		} else {
			builder.append(getLabel());
			if(relation.isPresent()) {
				builder.append(".");
				builder.append(relation.get().toString());
			}
		}
		return builder.toString();
	}
}

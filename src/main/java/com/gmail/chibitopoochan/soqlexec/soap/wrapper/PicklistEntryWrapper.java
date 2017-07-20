package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import com.sforce.soap.partner.PicklistEntry;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class PicklistEntryWrapper {
	private PicklistEntry reesult;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public PicklistEntryWrapper(){}

	/**
	 * {@link com.sforce.soap.partner.PicklistEntry}をラップ
	 * @param result ラップ対象
	 */
	public PicklistEntryWrapper(PicklistEntry result) {
		setPicklistEntryWrapper(result);
	}

	/**
	 * {@link com.sforce.soap.partner.PicklistEntry}をラップ
	 * @param result ラップ対象
	 */
	public void setPicklistEntryWrapper(PicklistEntry result) {
		this.reesult = result;
	}

	/**
	 * {@link com.sforce.soap.partner.PicklistEntry#isActive()}のラップ
	 * @return 選択肢が有効ならtrue
	 */
	public boolean isActive() {
		return reesult.isActive();
	}

	/**
	 * {@link com.sforce.soap.partner.PicklistEntry#getValue()}のラップ
	 * @return 選択肢の値
	 */
	public String getValue() {
		return reesult.getValue();
	}

}

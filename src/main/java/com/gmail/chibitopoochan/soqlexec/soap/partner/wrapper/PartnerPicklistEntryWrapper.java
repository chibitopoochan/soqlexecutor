package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PicklistEntryWrapper;
import com.sforce.soap.partner.PicklistEntry;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class PartnerPicklistEntryWrapper implements PicklistEntryWrapper{
	private PicklistEntry reesult;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public PartnerPicklistEntryWrapper(){}

	/**
	 * {@link com.sforce.soap.partner.PicklistEntry}をラップ
	 * @param result ラップ対象
	 */
	public PartnerPicklistEntryWrapper(PicklistEntry result) {
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

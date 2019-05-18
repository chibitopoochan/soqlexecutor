package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PicklistEntryWrapper;
import com.sforce.soap.tooling.PicklistEntry;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class ToolingPicklistEntryWrapper implements PicklistEntryWrapper{
	private PicklistEntry reesult;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public ToolingPicklistEntryWrapper(){}

	/**
	 * {@link com.sforce.soap.partner.PicklistEntry}をラップ
	 * @param result ラップ対象
	 */
	public ToolingPicklistEntryWrapper(PicklistEntry result) {
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

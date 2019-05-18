package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper;
import com.sforce.soap.tooling.DescribeGlobalSObjectResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class ToolingDescribeGlobalSObjectResultWrapper implements DescribeGlobalSObjectResultWrapper {
	private DescribeGlobalSObjectResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public ToolingDescribeGlobalSObjectResultWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public ToolingDescribeGlobalSObjectResultWrapper(DescribeGlobalSObjectResult result) {
		setDescribeGlobalSObjectResult(result);
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public void setDescribeGlobalSObjectResult(DescribeGlobalSObjectResult result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getName()}のラップ
	 * @return オブジェクト名
	 */
	public String getName() {
		return result.getName();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getLabel()}のラップ
	 * @return ラベル名
	 */
	public String getLabel() {
		return result.getLabel();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getKeyPrefix()}のラップ
	 * @return オブジェクトのプレフィックス
	 */
	public String getKeyPrefix() {
		return result.getKeyPrefix();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getLabelPlural()}のラップ
	 * @return オブジェクトの複数名
	 */
	public String getLabelPlural() {
		return result.getLabelPlural();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getActivateable()}のラップ
	 * @return オブジェクトの有効化有無
	 */
	public boolean getActivateable() {
		return result.getActivateable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getCustom()}のラップ
	 * @return オブジェクトのカスタム有無
	 */
	public boolean getCustom() {
		return result.getCustom();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getCustomSetting()}のラップ
	 * @return オブジェクトのカスタム設定有無
	 */
	public boolean getCustomSetting() {
		return result.getCustomSetting();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getDeletable()}のラップ
	 * @return オブジェクトの削除可否
	 */
	public boolean getDeletable() {
		return result.getDeletable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getDeprecatedAndHidden()}のラップ
	 * @return オブジェクトの無効と非表示
	 */
	public boolean getDeprecatedAndHidden() {
		return result.getDeprecatedAndHidden();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getFeedEnabled()}のラップ
	 * @return オブジェクトのFeed可否
	 */
	public boolean getFeedEnabled() {
		return result.getFeedEnabled();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getHasSubtypes()}のラップ
	 * @return オブジェクトのサブタイプ保有有無
	 */
	public boolean getHasSubtypes() {
		return result.getHasSubtypes();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getIdEnabled()}のラップ
	 * @return オブジェクトのID有無
	 */
	public boolean getIdEnabled() {
		return true;
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getIsSubtype()}のラップ
	 * @return オブジェクトのサブタイプ有無
	 */
	public boolean getIsSubtype() {
		return result.getIsSubtype();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getLayoutable()}のラップ
	 * @return オブジェクトのレイアウト可否
	 */
	public boolean getLayoutable() {
		return result.getLayoutable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getMergeable()}のラップ
	 * @return オブジェクトの統合可否
	 */
	public boolean getMergeable() {
		return result.getMergeable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getMruEnabled()}のラップ
	 * @return オブジェクトのMRU有効
	 */
	public boolean getMruEnabled() {
		return result.getMruEnabled();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getQueryable()}のラップ
	 * @return オブジェクトのクエリ可否
	 */
	public boolean getQueryable() {
		return result.getQueryable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getReplicateable()}のラップ
	 * @return オブジェクトのReplicateable
	 */
	public boolean getReplicateable() {
		return result.getReplicateable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getRetrieveable()}のラップ
	 * @return オブジェクトの取得可否
	 */
	public boolean getRetrieveable() {
		return result.getRetrieveable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getSearchable()}のラップ
	 * @return オブジェクトの検索可否
	 */
	public boolean getSearchable() {
		return result.getSearchable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getTriggerable()}のラップ
	 * @return オブジェクトのトリガー可否
	 */
	public boolean getTriggerable() {
		return result.getTriggerable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getUndeletable()}のラップ
	 * @return オブジェクトの削除取消可否
	 */
	public boolean getUndeletable() {
		return result.getUndeletable();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getUpdateable()}のラップ
	 * @return オブジェクトの更新可否
	 */
	public boolean getUpdateable() {
		return result.getUpdateable();
	}

}

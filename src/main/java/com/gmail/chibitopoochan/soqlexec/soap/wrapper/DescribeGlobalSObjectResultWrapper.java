package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * TODO 気が向いたらコメントを修正
 */
public interface DescribeGlobalSObjectResultWrapper {
	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getName()}のラップ
	 * @return オブジェクト名
	 */
	public String getName();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getLabel()}のラップ
	 * @return ラベル名
	 */
	public String getLabel();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getKeyPrefix()}のラップ
	 * @return オブジェクトのプレフィックス
	 */
	public String getKeyPrefix();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getLabelPlural()}のラップ
	 * @return オブジェクトの複数名
	 */
	public String getLabelPlural();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getActivateable()}のラップ
	 * @return オブジェクトの有効化有無
	 */
	public boolean getActivateable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getCustom()}のラップ
	 * @return オブジェクトのカスタム有無
	 */
	public boolean getCustom();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getCustomSetting()}のラップ
	 * @return オブジェクトのカスタム設定有無
	 */
	public boolean getCustomSetting();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getDeletable()}のラップ
	 * @return オブジェクトの削除可否
	 */
	public boolean getDeletable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getDeprecatedAndHidden()}のラップ
	 * @return オブジェクトの無効と非表示
	 */
	public boolean getDeprecatedAndHidden();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getFeedEnabled()}のラップ
	 * @return オブジェクトのFeed可否
	 */
	public boolean getFeedEnabled();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getHasSubtypes()}のラップ
	 * @return オブジェクトのサブタイプ保有有無
	 */
	public boolean getHasSubtypes();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getIdEnabled()}のラップ
	 * @return オブジェクトのID有無
	 */
	public boolean getIdEnabled();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getIsSubtype()}のラップ
	 * @return オブジェクトのサブタイプ有無
	 */
	public boolean getIsSubtype();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getLayoutable()}のラップ
	 * @return オブジェクトのレイアウト可否
	 */
	public boolean getLayoutable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getMergeable()}のラップ
	 * @return オブジェクトの統合可否
	 */
	public boolean getMergeable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getMruEnabled()}のラップ
	 * @return オブジェクトのMRU有効
	 */
	public boolean getMruEnabled();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getQueryable()}のラップ
	 * @return オブジェクトのクエリ可否
	 */
	public boolean getQueryable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getReplicateable()}のラップ
	 * @return オブジェクトのReplicateable
	 */
	public boolean getReplicateable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getRetrieveable()}のラップ
	 * @return オブジェクトの取得可否
	 */
	public boolean getRetrieveable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getSearchable()}のラップ
	 * @return オブジェクトの検索可否
	 */
	public boolean getSearchable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getTriggerable()}のラップ
	 * @return オブジェクトのトリガー可否
	 */
	public boolean getTriggerable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getUndeletable()}のラップ
	 * @return オブジェクトの削除取消可否
	 */
	public boolean getUndeletable();

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getUpdateable()}のラップ
	 * @return オブジェクトの更新可否
	 */
	public boolean getUpdateable();

}

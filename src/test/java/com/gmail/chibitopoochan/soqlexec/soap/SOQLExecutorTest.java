package com.gmail.chibitopoochan.soqlexec.soap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.gmail.chibitopoochan.soqlexec.soap.mock.PartnerConnectionWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.QNameWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.QueryResultWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SObjectWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.XmlObjectWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerXmlObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soql.QueryAnalyzeUtils;
import com.gmail.chibitopoochan.soqlexec.soql.QueryAnalyzeUtils.TokenException;
import com.gmail.chibitopoochan.soqlexec.soql.SOQL;
import com.sforce.ws.ConnectionException;

/**
 * SOQLExecutorのテストクラス
 */
public class SOQLExecutorTest {
	// 検証に使用するSOQL
	public static final String SOQL_NORMAL = "SELECT namE FROM USER";
	public static final String SOQL_NORMAL_CONDITION =
			"SELECT namE FROM USER where name = 'a;,_()&%$#\"\\'' and name in ('','ABC') group by name order by name desc nulls first limit 921 offset 2000";
	public static final String SOQL_ALL = "select naMe from user";
	public static final String SOQL_MORE = "Select nAme From User ";
	public static final String SOQL_REF = "select child1__r.child2__r.child3__r.NamE from User";
	public static final String SOQL_MULTI_COLUMN = "select id, username, email, createdby.name from user";
	public static final String SOQL_FUNCTION = "select id, count(id), name from user";
	public static final String SOQL_SUBQUERY_ONLY = "select (select id, name from contacts) from account";
	public static final String SOQL_SUBQUERY_DOUBLE = "select (select id, name from contacts),(select id,name from accounts) from account";
	public static final String SOQL_SUBQUERY_COMPLEX = "select id,(select id, name from contacts),name from account";

	// 検証に使用するインスタンス
	private PartnerConnectionWrapperMock connection;
	private SOQLExecutor executor;

	// 実行結果
	private SObjectWrapperMock[] normalRecords;
	private SObjectWrapperMock[] refRecords;
	private SObjectWrapperMock[] moreRecords;
	private SObjectWrapperMock[] allRecords;
	private SObjectWrapperMock[] multiColumnRecords;
	private SObjectWrapperMock[] subqueryRecords;

	/**
	 * 例外検証用のルールを用意
	 */
	@Rule public ExpectedException thrown = ExpectedException.none();

	/**
	 * テスト前の共通設定
	 */
	@Before public void setup() {
		// インスタンス化
		connection = new PartnerConnectionWrapperMock();
		executor = new SOQLExecutor(connection);

	}

	private void setMultiColumnRecords() {
		// レコードの作成
		multiColumnRecords = new SObjectWrapperMock[1];
		multiColumnRecords[0] = new SObjectWrapperMock();
		multiColumnRecords[0].addChild(createColumn("email","email1"));
		multiColumnRecords[0].addChild(createColumn("username","username1"));
		multiColumnRecords[0].addChild(createColumn("id","id1"));

		// クエリ結果を作成
		QueryResultWrapperMock result = new QueryResultWrapperMock();
		result.setRecords(multiColumnRecords);
		result.setSize(1);

		// Wrapperに設定
		connection.putSOQL(SOQL_MULTI_COLUMN, result);

	}

	/**
	 * 通常レコードを用意
	 */
	private void setNormalRecords() {
		// レコードの作成
		normalRecords = new SObjectWrapperMock[1];
		normalRecords[0] = new SObjectWrapperMock();
		normalRecords[0].addChild(createColumn("Name", "value"));

		// クエリ結果を作成
		QueryResultWrapperMock normalResult = new QueryResultWrapperMock();
		normalResult.setRecords(normalRecords);
		normalResult.setSize(1);
		normalResult.setDone(true);

		// Wrapperに設定
		connection.putSOQL(SOQL_NORMAL, normalResult);

	}

	/**
	 * 条件付き通常レコードを用意
	 */
	private void setNormalConditionRecords() {
		// レコードの作成
		normalRecords = new SObjectWrapperMock[1];
		normalRecords[0] = new SObjectWrapperMock();
		normalRecords[0].addChild(createColumn("Name", "value"));

		// クエリ結果を作成
		QueryResultWrapperMock normalResult = new QueryResultWrapperMock();
		normalResult.setRecords(normalRecords);
		normalResult.setSize(1);
		normalResult.setDone(true);

		// Wrapperに設定
		connection.putSOQL(SOQL_NORMAL_CONDITION, normalResult);

	}

	/**
	 * 参照項目を持つレコードを用意
	 */
	private void setRefRecords() {
		// 参照先レコードの作成
		XmlObjectWrapperMock child3 = new XmlObjectWrapperMock();
		child3.addChild(createColumn("name", "value"));

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock child2 = new XmlObjectWrapperMock();
		child2.addChild(createColumn("child3__r", child3));

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock child1 = new XmlObjectWrapperMock();
		child1.addChild(createColumn("child2__r", child2));

		// レコードの作成
		refRecords = new SObjectWrapperMock[1];
		refRecords[0] = new SObjectWrapperMock();
		refRecords[0].addChild(createColumn("child1__r", child1));

		// クエリ結果を作成
		QueryResultWrapperMock normalResult = new QueryResultWrapperMock();
		normalResult.setRecords(refRecords);
		normalResult.setSize(1);

		// Wrapperに設定
		connection.putSOQL(SOQL_REF, normalResult);

	}

	/**
	 * 追加取得のレコードを用意
	 */
	private void setMoreRecords() {
		// 通常レコードの作成
		normalRecords = new SObjectWrapperMock[1];
		normalRecords[0] = new SObjectWrapperMock();
		normalRecords[0].addChild(createColumn("name", "value1"));

		// クエリ結果の作成
		QueryResultWrapperMock normalResult = new QueryResultWrapperMock();
		normalResult.setRecords(normalRecords);
		normalResult.setDone(false);
		normalResult.setQueryLocator("locator");
		normalResult.setSize(2);

		// 追加レコードの作成
		moreRecords = new SObjectWrapperMock[1];
		moreRecords[0] = new SObjectWrapperMock();
		moreRecords[0].addChild(createColumn("name", "value2"));

		// クエリ結果の作成
		QueryResultWrapperMock moreResult = new QueryResultWrapperMock();
		moreResult.setRecords(moreRecords);
		moreResult.setQueryLocator("locator2");
		moreResult.setDone(false);
		moreResult.setSize(2);

		// 追加レコード２の作成
		SObjectWrapperMock[] moreRecords2 = new SObjectWrapperMock[1];
		moreRecords2[0] = new SObjectWrapperMock();
		moreRecords2[0].addChild(createColumn("name", "value3"));

		// クエリ結果の作成
		QueryResultWrapperMock moreResult2 = new QueryResultWrapperMock();
		moreResult2.setRecords(moreRecords2);
		moreResult2.setQueryLocator("locator3");
		moreResult2.setDone(true);
		moreResult2.setSize(2);

		// Wrapperに設定
		connection.putSOQL(SOQL_MORE, normalResult);
		connection.putSOQLMore("locator", moreResult);
		connection.putSOQLMore("locator2", moreResult2);

	}

	/**
	 * 集計関数を含むレコードを用意
	 */
	private void setFunctionRecord() {
		// レコードの作成
		multiColumnRecords = new SObjectWrapperMock[1];
		multiColumnRecords[0] = new SObjectWrapperMock();
		multiColumnRecords[0].addChild(createColumn("id","id1"));
		multiColumnRecords[0].addChild(createColumn("name","name1"));
		multiColumnRecords[0].addChild(createColumn("expr0","20"));

		// クエリ結果を作成
		QueryResultWrapperMock result = new QueryResultWrapperMock();
		result.setRecords(multiColumnRecords);
		result.setSize(1);

		// Wrapperに設定
		connection.putSOQL(SOQL_FUNCTION, result);

	}

	/**
	 * 削除のレコードを用意
	 */
	private void setAllRecords() {
		// 削除レコードの作成
		allRecords = new SObjectWrapperMock[1];
		allRecords[0] = new SObjectWrapperMock();
		allRecords[0].addChild(createColumn("name", "value"));

		// クエリ結果の作成
		QueryResultWrapperMock allResult = new QueryResultWrapperMock();
		allResult.setRecords(allRecords);
		allResult.setSize(1);

		// Wrapperに設定
		connection.putSOQLAll(SOQL_ALL, allResult);

	}

	/**
	 * サブクエリのレコードを用意
	 */
	private void setSubqueryOnlyRecords() {
		// 参照先レコードの作成
		XmlObjectWrapperMock child3 = new XmlObjectWrapperMock();
		child3.addChild(createColumn("id", "id2"));
		child3.addChild(createColumn("name", "name2"));

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock child2 = new XmlObjectWrapperMock();
		child2.addChild(createColumn("records", child3));

		// レコードの作成
		subqueryRecords = new SObjectWrapperMock[1];
		subqueryRecords[0] = new SObjectWrapperMock();
		subqueryRecords[0].addChild(createColumn("Contacts", child2));

		// クエリ結果を作成
		QueryResultWrapperMock subqueryResult = new QueryResultWrapperMock();
		subqueryResult.setRecords(subqueryRecords);
		subqueryResult.setSize(1);
		subqueryResult.setDone(true);

		// Wrapperに設定
		connection.putSOQL(SOQL_SUBQUERY_ONLY, subqueryResult);

	}

	/**
	 * サブクエリのレコードを用意
	 */
	private void setSubqueryDoubleRecords() {
		// 参照先レコードの作成
		XmlObjectWrapperMock accountChild2 = new XmlObjectWrapperMock();
		accountChild2.addChild(createColumn("id", "id2"));
		accountChild2.addChild(createColumn("name", "name2"));

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock accountChild = new XmlObjectWrapperMock();
		accountChild.addChild(createColumn("records", accountChild2));

		// 参照先レコードの作成
		XmlObjectWrapperMock contactChild2 = new XmlObjectWrapperMock();
		contactChild2.addChild(createColumn("id", "id2"));
		contactChild2.addChild(createColumn("name", "name2"));

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock contactChild = new XmlObjectWrapperMock();
		contactChild.addChild(createColumn("records", contactChild2));

		// レコードの作成
		subqueryRecords = new SObjectWrapperMock[1];
		subqueryRecords[0] = new SObjectWrapperMock();
		subqueryRecords[0].addChild(createColumn("Accounts", accountChild));
		subqueryRecords[0].addChild(createColumn("Contacts", contactChild));

		// クエリ結果を作成
		QueryResultWrapperMock subqueryResult = new QueryResultWrapperMock();
		subqueryResult.setRecords(subqueryRecords);
		subqueryResult.setSize(1);
		subqueryResult.setDone(true);

		// Wrapperに設定
		connection.putSOQL(SOQL_SUBQUERY_DOUBLE, subqueryResult);

	}

	/**
	 * サブクエリのレコードを用意
	 */
	private void setSubqueryComplexRecords() {
		// 参照先レコードの作成
		XmlObjectWrapperMock child3 = new XmlObjectWrapperMock();
		child3.addChild(createColumn("id", "id2"));
		child3.addChild(createColumn("name", "name2"));

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock child2 = new XmlObjectWrapperMock();
		child2.addChild(createColumn("records", child3));

		// レコードの作成
		subqueryRecords = new SObjectWrapperMock[1];
		subqueryRecords[0] = new SObjectWrapperMock();
		subqueryRecords[0].addChild(createColumn("id", "id1"));
		subqueryRecords[0].addChild(createColumn("name", "name1"));
		subqueryRecords[0].addChild(createColumn("Contacts", child2));

		// クエリ結果を作成
		QueryResultWrapperMock subqueryResult = new QueryResultWrapperMock();
		subqueryResult.setRecords(subqueryRecords);
		subqueryResult.setSize(1);
		subqueryResult.setDone(true);

		// Wrapperに設定
		connection.putSOQL(SOQL_SUBQUERY_COMPLEX, subqueryResult);

	}

	/**
	 * キーと値から列情報を作成.
	 * @param key キー項目
	 * @param value 値
	 * @return 列情報
	 */
	private PartnerXmlObjectWrapper createColumn(String key, String value) {
		QNameWrapperMock name = new QNameWrapperMock();
		name.setLocalPart(key);

		XmlObjectWrapperMock column = new XmlObjectWrapperMock();
		column.setName(name);
		column.setValue(value);

		return column;
	}

	/**
	 * キーと参照先から列情報を作成.
	 * @param key キー項目
	 * @param ref 参照先
	 * @return 列情報
	 */
	private PartnerXmlObjectWrapper createColumn(String key, XmlObjectWrapperMock ref) {
		QNameWrapperMock name = new QNameWrapperMock();
		name.setLocalPart(key);
		ref.setName(name);

		return ref;
	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:変更
	 * More:false
	 * All:false
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQL() throws ConnectionException {
		// 個別の初期化処理
		setNormalRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setBatchSize(1);
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_NORMAL);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));

		// 項目の値を取得できること
		assertThat(records.get(0).get("namE"), is((String)normalRecords[0].getField("Name").get()));

	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:初期
	 * More:false
	 * All:true
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLWithAll() throws ConnectionException {
		// 個別の初期化処理
		setAllRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(true);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_ALL);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));
		assertThat(records.get(0).get("naMe"), is(allRecords[0].getField("name").get()));
	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:初期
	 * More:true
	 * All:false
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLWithMore() throws ConnectionException {
		// 個別の初期化処理
		setMoreRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_MORE);
		List<Map<String, String>> moreList = new LinkedList<>();
		SOQLExecutor.QueryMore more = executor.getQueryMore();
		while(!more.isDone()) {
			moreList.addAll(more.execute());
		}

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));
		assertThat(records.get(0).get("nAme"), is(normalRecords[0].getField("name").get()));
		assertThat(Integer.valueOf(moreList.size()), is(Integer.valueOf(2)));
		assertThat(moreList.get(0).get("nAme"), is(moreRecords[0].getField("name").get()));
	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:初期
	 * More:true
	 * All:true
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLWithRef() throws ConnectionException {
		// 個別の初期化処理
		setRefRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_REF);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));
		assertNotNull(refRecords[0].getChild("child1__r"));
		assertNotNull(refRecords[0].getChild("child1__r").get().getChild("child2__r"));
		assertNotNull(refRecords[0].getChild("child1__r").get().getChild("child2__r").get().getChild("child3__r"));
		assertThat(records.get(0).get("child1__r.child2__r.child3__r.NamE"),
				is(refRecords[0].getChild("child1__r").get().getChild("child2__r").get().getChild("child3__r").get().getField("name").get()));

	}

	@Test public void testExecSOQLWithFunction() throws ConnectionException {
		// 個別の初期化処理
		setFunctionRecord();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_FUNCTION);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));
		assertThat(records.get(0).get("id"), is((String)multiColumnRecords[0].getField("id").get()));
		assertThat(records.get(0).get("name"), is((String)multiColumnRecords[0].getField("name").get()));
		assertThat(records.get(0).get("count(id)"), is((String)multiColumnRecords[0].getField("expr0").get()));

	}

	@Test public void testExecSOQLWithSubqueryOnly() throws ConnectionException {
		// 個別の初期化処理
		setSubqueryOnlyRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(false);
		executor.setJoinOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_SUBQUERY_ONLY);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));

		List<Map<String, String>> resultList = new LinkedList<>();
		Map<String, String> resultMap = new LinkedHashMap<>();
		resultMap.put("id", "id2");
		resultMap.put("name", "name2");
		resultList.add(resultMap);
		assertThat(records.get(0).get("contacts.id"), is("id2"));
		assertThat(records.get(0).get("contacts.name"), is("name2"));

	}

	@Test public void testExecSOQLWithSubqueryDouble() throws ConnectionException {
		// 個別の初期化処理
		setSubqueryDoubleRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_SUBQUERY_DOUBLE);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(2)));

		// サブクエリの確認
		List<Map<String, String>> resultList = new LinkedList<>();
		Map<String, String> resultMap = new LinkedHashMap<>();
		resultMap.put("id", "id2");
		resultMap.put("name", "name2");
		resultList.add(resultMap);
//		assertThat(records.get(0).get("contacts"), is(resultList.toString()));

		// サブクエリ２の確認
		List<Map<String, String>> resultList2 = new LinkedList<>();
		Map<String, String> resultMap2 = new LinkedHashMap<>();
		resultMap2.put("id", "id2");
		resultMap2.put("name", "name2");
		resultList2.add(resultMap2);
//		assertThat(records.get(0).get("accounts"), is(resultList2.toString()));

	}

	@Test public void testExecSOQLWithCondition() throws ConnectionException, TokenException {
		// 個別の初期化処理
		setNormalConditionRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(false);
		executor.setJoinOption(false);

		// SOQLを実行
		executor.execute(SOQL_NORMAL_CONDITION);

		SOQL query = QueryAnalyzeUtils.analyze(SOQL_NORMAL_CONDITION);
		assertThat(query.getFromObject(), is("USER"));
		assertThat(query.getGroupByFields().get(0).getLabel(), is("name"));
		assertThat(query.getOrderByFields().get(0).getLabel(), is("name"));
		assertThat(query.getOrderByFields().get(0).getExtend(), is("DESC NULLS FIRST"));
		assertThat(query.getLimit(), is(921));
		assertThat(query.getOffset(), is(2000));

	}

	@Test public void testExecSOQLWithSubqueryComplex() throws ConnectionException {
		// 個別の初期化処理
		setSubqueryComplexRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setAllOption(false);
		executor.setJoinOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_SUBQUERY_COMPLEX);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));
		assertThat(records.get(0).get("id"), is((String)subqueryRecords[0].getField("id").get()));
		assertThat(records.get(0).get("name"), is((String)subqueryRecords[0].getField("name").get()));

		List<Map<String, String>> resultList = new LinkedList<>();
		Map<String, String> resultMap = new LinkedHashMap<>();
		resultMap.put("id", "id2");
		resultMap.put("name", "name2");
		resultList.add(resultMap);
		assertThat(records.get(0).get("contacts.id"), is("id2"));
		assertThat(records.get(0).get("contacts.name"), is("name2"));

	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:変更
	 * More:false
	 * All:false
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLFailed() throws ConnectionException {
		// 個別の初期化処理
		setNormalRecords();

		// 接続設定
		connection.setSuccess(false);

		// SOQL実行のパラメータを設定
		executor.setBatchSize(1);
		executor.setAllOption(false);

		// 例外発生を想定
		thrown.expect(ConnectionException.class);

		// SOQLを実行
		executor.execute(SOQL_NORMAL);

	}

	/**
	 * 列名の整列を検証
	 * @throws ConnectionException 接続エラー
	 */
	@Test public void testColumnSort() throws ConnectionException {
		// 個別の初期化処理
		setMultiColumnRecords();

		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setBatchSize(1);
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_MULTI_COLUMN);
		String[] keys = records.get(0).keySet().toArray(new String[0]);

		assertThat(keys[0], is("id"));
		assertThat(keys[1], is("username"));
		assertThat(keys[2], is("email"));

	}

}

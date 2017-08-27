package com.gmail.chibitopoochan.soqlexec.soap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

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
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper;
import com.sforce.ws.ConnectionException;

/**
 * SOQLExecutorのテストクラス
 */
public class SOQLExecutorTest {
	// 検証に使用するSOQL
	public static final String SOQL_NORMAL = "SELECT namE FROM USER";
	public static final String SOQL_ALL = "select naMe from user";
	public static final String SOQL_MORE = "Select nAme From User ";
	public static final String SOQL_REF = "select child1.child2.child3.NamE from User";
	public static final String SOQL_MULTI_COLUMN = "select id, username, email from user";

	// 検証に使用するインスタンス
	private PartnerConnectionWrapperMock connection;
	private SOQLExecutor executor;

	// 実行結果
	private SObjectWrapperMock[] normalRecords;
	private SObjectWrapperMock[] refRecords;
	private SObjectWrapperMock[] moreRecords;
	private SObjectWrapperMock[] allRecords;
	private SObjectWrapperMock[] multiColumnRecords;

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

		// Wrapperに設定
		connection.putSOQL(SOQL_NORMAL, normalResult);

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
		child2.addChild(createColumn("child3", child3));

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock child1 = new XmlObjectWrapperMock();
		child1.addChild(createColumn("child2", child2));

		// レコードの作成
		refRecords = new SObjectWrapperMock[1];
		refRecords[0] = new SObjectWrapperMock();
		refRecords[0].addChild(createColumn("child1", child1));

		// クエリ結果を作成
		QueryResultWrapperMock normalResult = new QueryResultWrapperMock();
		normalResult.setRecords(refRecords);

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

		// 追加レコードの作成
		moreRecords = new SObjectWrapperMock[1];
		moreRecords[0] = new SObjectWrapperMock();
		moreRecords[0].addChild(createColumn("name", "value2"));

		// クエリ結果の作成
		QueryResultWrapperMock moreResult = new QueryResultWrapperMock();
		moreResult.setRecords(moreRecords);
		moreResult.setQueryLocator("locator");
		moreResult.setDone(true);

		// Wrapperに設定
		connection.putSOQL(SOQL_MORE, normalResult);
		connection.putSOQLMore("locator", moreResult);

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

		// Wrapperに設定
		connection.putSOQLAll(SOQL_ALL, allResult);

	}

	/**
	 * キーと値から列情報を作成.
	 * @param key キー項目
	 * @param value 値
	 * @return 列情報
	 */
	private XmlObjectWrapper createColumn(String key, String value) {
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
	private XmlObjectWrapper createColumn(String key, XmlObjectWrapperMock ref) {
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
		assertThat(records.get(0).get("namE"), is((String)normalRecords[0].getField("Name")));

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
		assertThat(records.get(0).get("naMe"), is(allRecords[0].getField("name")));
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
		assertThat(records.get(0).get("nAme"), is(normalRecords[0].getField("name")));
		assertThat(Integer.valueOf(moreList.size()), is(Integer.valueOf(1)));
		assertThat(moreList.get(0).get("nAme"), is(moreRecords[0].getField("name")));
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
		assertNotNull(refRecords[0].getChild("child1"));
		assertNotNull(refRecords[0].getChild("child1").getChild("child2"));
		assertNotNull(refRecords[0].getChild("child1").getChild("child2").getChild("child3"));
		assertThat(records.get(0).get("child1.child2.child3.NamE"),
				is(refRecords[0].getChild("child1").getChild("child2").getChild("child3").getField("name")));

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

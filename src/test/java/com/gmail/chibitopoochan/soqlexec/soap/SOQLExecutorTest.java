package com.gmail.chibitopoochan.soqlexec.soap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.gmail.chibitopoochan.soqlexec.soap.mock.PartnerConnectionWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.QueryResultWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SObjectWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.XmlObjectWrapperMock;
import com.sforce.ws.ConnectionException;

/**
 * SOQLExecutorのテストクラス
 */
public class SOQLExecutorTest {
	// 検証に使用するSOQL
	public static final String SOQL_NORMAL = "SELECT NAME FROM USER";
	public static final String SOQL_ALL = "select name from user";
	public static final String SOQL_MORE = "Select Name From User ";
	public static final String SOQL_REF = "select name.child1.child2.child3 from User";

	// 検証に使用するインスタンス
	private PartnerConnectionWrapperMock connection;
	private SOQLExecutor executor;

	// 実行結果
	private SObjectWrapperMock[] normalRecords;
	private SObjectWrapperMock[] refRecords;
	private SObjectWrapperMock[] moreRecords;
	private SObjectWrapperMock[] allRecords;

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

		// 各初期化を実行
		setNormalRecords();
		setMoreRecords();
		setAllRecords();
		setRefRecords();

	}

	/**
	 * 通常レコードを用意
	 */
	private void setNormalRecords() {
		// レコードの作成
		normalRecords = new SObjectWrapperMock[1];
		normalRecords[0] = new SObjectWrapperMock();
		normalRecords[0].putField("NAME", "value");

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
		child3.putField("name", "value");

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock child2 = new XmlObjectWrapperMock();
		child2.putChild("child3", child3);

		// 参照先（中間）レコードの作成
		XmlObjectWrapperMock child1 = new XmlObjectWrapperMock();
		child1.putChild("child2", child2);

		// レコードの作成
		refRecords = new SObjectWrapperMock[1];
		refRecords[0] = new SObjectWrapperMock();
		refRecords[0].putChild("child1", child1);

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
		normalRecords[0].putField("Name", "value1");

		// クエリ結果の作成
		QueryResultWrapperMock normalResult = new QueryResultWrapperMock();
		normalResult.setRecords(normalRecords);
		normalResult.setDone(false);
		normalResult.setQueryLocator("locator");

		// 追加レコードの作成
		moreRecords = new SObjectWrapperMock[1];
		moreRecords[0] = new SObjectWrapperMock();
		moreRecords[0].putField("Name", "value2");

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
		allRecords[0].putField("name", "value");

		// クエリ結果の作成
		QueryResultWrapperMock allResult = new QueryResultWrapperMock();
		allResult.setRecords(allRecords);

		// Wrapperに設定
		connection.putSOQLAll(SOQL_ALL, allResult);

	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:変更
	 * More:false
	 * All:false
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQL() throws ConnectionException {
		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setBatchSize(1);
		executor.setMoreOption(false);
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_NORMAL);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));

		// 項目の値を取得できること
		assertThat(records.get(0).get("name1"), is(normalRecords[0].getField("name1")));

	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:初期
	 * More:false
	 * All:true
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLWithAll() throws ConnectionException {
		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setMoreOption(false);
		executor.setAllOption(true);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_ALL);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));
		assertThat(records.get(0).get("name3"), is(normalRecords[0].getField("name3")));
	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:初期
	 * More:true
	 * All:false
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLWithMore() throws ConnectionException {
		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setMoreOption(true);
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_MORE);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(2)));
		assertThat(records.get(0).get("name"), is(normalRecords[0].getField("name")));
		assertThat(records.get(1).get("name"), is(moreRecords[0].getField("name")));
	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:初期
	 * More:true
	 * All:true
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLWithRef() throws ConnectionException {
		// 接続設定
		connection.setSuccess(true);

		// SOQL実行のパラメータを設定
		executor.setMoreOption(false);
		executor.setAllOption(false);

		// SOQLを実行
		List<Map<String, String>> records = executor.execute(SOQL_REF);

		// 結果の確認
		// パラメータが渡されていること
		// バッチサイズが正しい
		assertThat(Integer.valueOf(connection.getQueryOption()), is(Integer.valueOf(SOQLExecutor.DEFAULT_BATCH_SIZE)));
		assertThat(Integer.valueOf(records.size()), is(Integer.valueOf(1)));
		assertThat(records.get(0).get("name.child1.child2.child3"), is(normalRecords[0].getField("name.child1.child2.child3")));
	}

	/**
	 * 以下のパラメータで参照項目あり
	 * Size:変更
	 * More:false
	 * All:false
	 * @throws ConnectionException
	 */
	@Test public void testExecSOQLFailed() throws ConnectionException {
		// 接続設定
		connection.setSuccess(false);

		// SOQL実行のパラメータを設定
		executor.setBatchSize(1);
		executor.setMoreOption(false);
		executor.setAllOption(false);

		// 例外発生を想定
		thrown.expect(ConnectionException.class);

		// SOQLを実行
		executor.execute(SOQL_NORMAL);

	}

}

package org.herring.nfs;

import org.herring.nfs.client.NetworkFileSystemClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

/**
 * NetworkFileSystemClient 테스트 클래스.
 * 테스트를 하기 위해서 NetworkFileSystemClient 객체를 host, ip 로 초기화하여 사용한다.
 * User: hyunje
 * Date: 13. 6. 14.
 * Time: 오전 12:27
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NetworkFileSystemClientTest {
    public static final String host = "127.0.0.1";
    public static final int port = 9300;
    public static NetworkFileSystemClient client;

    @BeforeClass
    public static void testOpenConnection() throws Exception {
        System.out.println("Try to open connection");
        client = new NetworkFileSystemClient(host, port);
        client.openConnection();
    }

    @AfterClass
    public static void testCloseConnection() throws Exception {
        System.out.println("Close connection");
        client.closeConnection();
    }

    /**
     * boolean putData(String locate, String data) 테스트
     * @throws Exception
     */
    @Test
    public void test0PutData() throws Exception {
        System.out.println("============================");
        System.out.println("test0PutData");
        if (client.putData("100", "Test Data"))
            System.out.println("Success");
        else
            System.out.println("Fail");
        System.out.println("============================");

    }

    /**
     * boolean putData(String locate, List<String> data) 테스트
     * @throws Exception
     */
    @Test
    public void test1PutData() throws Exception {
        System.out.println("============================");
        System.out.println("test1PutData");
        String locate = "101";
        List<String> dataList = new ArrayList<String>();
        dataList.add("test data 1");
        dataList.add("test data 2");
        dataList.add("test data 3");
        dataList.add("test data 4");
        dataList.add("test data 5");
        dataList.add("test data 6");
        dataList.add("test data 7");
        dataList.add("test data 8");

        if (client.putData(locate, dataList))
            System.out.println("Success");
        else
            System.out.println("Fail");
        System.out.println("============================");

    }

    /**
     * boolean getData(String locate) 테스트
     * @throws Exception
     */
    @Test
    public void test2GetData() throws Exception {
        System.out.println("============================");

        System.out.println("test2GetData");
        String locate = "101";
        String result = client.getData(locate);
        System.out.println("In "+locate+" : "+result);
        System.out.println("============================");
    }

    /**
     * boolean getData(String locate, int offset, int size) 테스트
     * @throws Exception
     */
    @Test
    public void test3GetData() throws Exception {
        System.out.println("============================");
        System.out.println("test3GetData");
        String locate = "101";
        int offset = 8;
        int size = 20;
        String result = client.getData(locate,offset,size);
        System.out.println("locate : "+locate);
        System.out.println("offset : "+offset);
        System.out.println("size : "+size);
        System.out.println("result : "+result);
        System.out.println("============================");

}

    /**
     * boolean getLine(String locate, int linecount) 테스트
     * @throws Exception
     */
    @Test
    public void test4GetLine() throws Exception {
        System.out.println("============================");
        System.out.println("test4GetLine");
        String locate = "101";
        int linecount = 5;
        String result = client.getLine(locate,linecount);
        System.out.println("result : "+result);
        System.out.println("============================");
    }
}

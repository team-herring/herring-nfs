package org.herring.nfs;

import org.herring.nfs.client.NetworkFileSystemClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * << Description >>
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
        client.closeConnection();
    }

    @Test
    public void test0PutData() throws Exception {
        System.out.println("test0PutData");
        if (client.putData("101", "Test Data"))
            System.out.println("Success");
        else
            System.out.println("Fail");

    }

    @Test
    public void test1PutData() throws Exception {
        System.out.println("test1PutData");
    }

    @Test
    public void test2GetData() throws Exception {
        System.out.println("test2GetData");

    }

    @Test
    public void test3GetData() throws Exception {
        System.out.println("test3GetData");

    }

    @Test
    public void test4GetLine() throws Exception {
        System.out.println("test4GetLine");

    }
}

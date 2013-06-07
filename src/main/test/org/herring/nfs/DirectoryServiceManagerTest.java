package org.herring.nfs;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * DirectoryServiceManager 테스트 클래스
 * User: hyunje
 * Date: 13. 6. 7.
 * Time: 오후 9:04
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DirectoryServiceManagerTest {
    static DirectoryServiceManager manager = DirectoryServiceManager.getInstance();


    @BeforeClass
    public static void constructorTest() throws Exception{
        Assert.assertNotNull(DirectoryServiceManager.cache);
        Assert.assertNotNull(manager);
        Assert.assertNotNull(manager.configuration);
        Assert.assertNotNull(manager.fileHashMap);
        System.out.println("----------Configuration Value----------");
        System.out.println(manager.configuration.toString());
        System.out.println("---------------------------------------");

    }

    @Test
    public void test0PutData() throws Exception {

    }

    @Test
    public void test1PutData() throws Exception {

    }

    @Test
    public void test2GetData() throws Exception {

    }

    @Test
    public void test3GetData() throws Exception {

    }

    @Test
    public void test4GetLine() throws Exception {

    }
}

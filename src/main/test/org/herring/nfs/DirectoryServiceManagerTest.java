package org.herring.nfs;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * DirectoryServiceManager 테스트 클래스
 * User: hyunje
 * Date: 13. 6. 7.
 * Time: 오후 9:04
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DirectoryServiceManagerTest {
    static DirectoryServiceManager manager = new DirectoryServiceManager();


    @BeforeClass
    public static void setUp() throws Exception{
        Assert.assertNotNull(DirectoryServiceManager.cache);
        Assert.assertNotNull(manager);
        Assert.assertNotNull(manager.configuration);
        Assert.assertNotNull(manager.fileHashMap);
        System.out.println("----------Configuration Value----------");
        System.out.println(manager.configuration.toString());
        System.out.println("---------------------------------------");


        File rootLocate = new File(manager.configuration.root);
        File[] contents = rootLocate.listFiles();
        for(File content :contents){
            content.delete();
        }
        manager.fileHashMap.clear();
    }

    /**
     * void putData(String locate, String data) 테스트
     * @throws Exception
     */
    @Test
    public void test0PutData() throws Exception {
        String locate = "1";
        String data = "data_1";
        manager.putData(locate,data);
    }

    /**
     * void putData(String locate, List<String> data) 테스트
     * @throws Exception
     */
    @Test
    public void test1PutData() throws Exception {
        String locate = "2";
        String[] data = {"data_1", "data_2", "data_3", "data_4"};
        List<String> dataList = new LinkedList<String>();
        dataList.addAll(Arrays.asList(data));
        manager.putData(locate,dataList);
    }

    /**
     * byte[] getData(String locate) 테스트
     * @throws Exception
     */
    @Test
    public void test2GetData() throws Exception {
        String locate1 = "1";
        byte[] data = manager.getData(locate1);
        String dataString1 = new String(data,0,data.length);
        System.out.println("data "+locate1+" : "+dataString1);

        String locate2 = "2";
        byte[] data2 = manager.getData(locate2);
        String dataString2 = new String(data2,0,data2.length);
        System.out.println("data "+locate2+" : "+dataString2);

    }

    /**
     * byte[] getData(String locate, int offset, int size) 테스트
     * @throws Exception
     */
    @Test
    public void test3GetData() throws Exception {

    }

    /**
     * String getLine(String locate, int lineCount) 테스트
     * @throws Exception
     */
    @Test
    public void test4GetLine() throws Exception {

    }
}

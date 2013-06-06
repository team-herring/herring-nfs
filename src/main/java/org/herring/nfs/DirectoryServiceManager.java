package org.herring.nfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 6.
 * Time: 오후 10:38
 */
public class DirectoryServiceManager implements DirectoryServiceInterface {

    HashMap<String, Integer> fileHashMap;

    public DirectoryServiceManager() {
        fileHashMap = new HashMap<String, Integer>();
        Configuration configuration = Configuration.getInstance();
        File rootDir = new File(configuration.root);
        File[] rootContents = rootDir.listFiles();

        //이전에 저장되어 있는 파일의 목록 읽기
        for (File file : rootContents) {
            fileHashMap.put(file.getName(), 1);
        }
    }

    @Override
    public void putData(String locate, String data) {
        if (fileHashMap.get(locate) == 1) {
            System.out.println("Already exist file!");
            return;
        }

        try {
            Configuration configuration = Configuration.getInstance();
            String fileName = configuration.fileName;

            RandomAccessFile addedFile = new RandomAccessFile(fileName, "rw");
            FileChannel fileChannel = addedFile.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(data.length()+2);
            buffer.put(data.getBytes());
            buffer.put(configuration.delimiter.getBytes());
            buffer.flip();

            fileChannel.write(buffer);

            addedFile.close();
            fileChannel.close();
            buffer.clear();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void putData(String locate, List<String> data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

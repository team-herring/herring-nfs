package org.herring.nfs;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 실제적으로 File을 관리하는 클래스
 * 최근 사용된 데이터는 Cache로 관리한다.
 * User: hyunje
 * Date: 13. 6. 6.
 * Time: 오후 10:38
 */
public class DirectoryServiceManager implements DirectoryServiceInterface {

    static LinkedHashMap<String, byte[]> cache;

    final Configuration configuration = Configuration.getInstance();
    ConcurrentHashMap<String, Integer> fileHashMap;

    public static DirectoryServiceManager getInstance(){
        return DirectoryServiceManagerHolder.INSTANCE;
    }

    private static final class DirectoryServiceManagerHolder{
        private static final DirectoryServiceManager INSTANCE = new DirectoryServiceManager();
    }

    private DirectoryServiceManager() {
        fileHashMap = new ConcurrentHashMap<String, Integer>();

        //FIFO를 위한 cache 초기화
        cache = new LinkedHashMap<String, byte[]>(configuration.cacheSize + 1, .75F, false) {
            protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
                return size() > configuration.cacheSize;
            }
        };

        File rootDir = new File(configuration.root);
        if (!rootDir.exists()) {
            System.out.println("루트 디렉토리가 존재하지 않습니다.");
            rootDir.mkdir();
        }
        rootDir.listFiles();
        File[] rootContents = rootDir.listFiles();

        //이전에 저장되어 있는 파일의 목록 읽기
        if (rootContents == null || rootContents.length == 0) {
            System.out.println("파일 시스템을 새롭게 생성합니다.");
        } else {
            for (File file : rootContents) {
                fileHashMap.put(file.getName(), 1);
            }
        }
    }

    @Override
    public boolean putData(String locate, String data) {
        if (fileHashMap.get(locate) != null) {
            System.out.println("Already exist file!");
            return false;
        }
        try {
            fileHashMap.put(locate, 1);
            String rootDirectory = configuration.root;

            RandomAccessFile addedFile = new RandomAccessFile(rootDirectory + "/" + locate, "rw");
            FileChannel fileChannel = addedFile.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(data.length() + 2);
            buffer.put(data.getBytes());
            buffer.put(configuration.delimiter.getBytes());
            buffer.flip();

            //쓰기 중인 파일 채널을 Lock
            FileLock lock = fileChannel.lock();
            fileChannel.write(buffer);
            lock.release();

            addedFile.close();
            fileChannel.close();
            buffer.clear();

            //최근 내용을 Cache에 저장 - cache의 사이즈 초과라면, FIFO 방식으로 진행
            cache.put(locate, data.getBytes());


        } catch (Exception e) {
            return false;

        }
        return true;

    }

    @Override
    public boolean putData(String locate, List<String> data) {
        if (fileHashMap.get(locate) != null) {
            System.out.println("Already exist file!");
            return false;
        }

        try {
            fileHashMap.put(locate, 1);

            String rootDirectory = configuration.root;

            RandomAccessFile addedFile = new RandomAccessFile(rootDirectory + "/" + locate, "rw");
            FileChannel fileChannel = addedFile.getChannel();

            //쓰기 중인 파일 채널을 Lock
            FileLock lock = fileChannel.lock();
            String inputData = "";
            for (String aString : data) {
                ByteBuffer buffer = ByteBuffer.allocate(aString.length() + 2);
                buffer.put(aString.getBytes());
                buffer.put(configuration.delimiter.getBytes());
                buffer.flip();

                fileChannel.write(buffer);

                buffer.clear();
                inputData += (aString + configuration.delimiter);
            }
            lock.release();
            addedFile.close();
            fileChannel.close();

            inputData.substring(0, inputData.length() - configuration.delimiter.length());

            //최근 내용을 Cache에 저장 - cache의 사이즈 초과라면, FIFO 방식으로 진행
            cache.put(locate, inputData.getBytes());


        } catch (Exception e) {

            return false;
        }
        return true;
    }

    @Override
    public byte[] getData(String locate) {
        if (fileHashMap.get(locate) == null) {
            System.out.println("Do not exist " + locate);
            return null;
        }

        //Cache Hit
        if (cache.containsKey(locate)) {
            return cache.get(locate);
        } else { //Cache Hit Fail

            try {
                String rootDirectory = configuration.root;

                RandomAccessFile file = new RandomAccessFile(rootDirectory + "/" + locate, "rw");
                FileChannel fileChannel = file.getChannel();
                FileLock lock = fileChannel.lock();
                MappedByteBuffer mappedByteBuffer;
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
                lock.release();

                byte[] mappedByteBufferArray = mappedByteBuffer.array();
                //최근 내용을 Cache에 저장 - cache의 사이즈 초과라면, FIFO 방식으로 진행
                cache.put(locate, mappedByteBufferArray);
                return mappedByteBufferArray;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public byte[] getData(String locate, int offset, int size) {
        if (fileHashMap.get(locate) == null) {
            System.out.println("Do not exist " + locate);
            return null;
        }

        try {
            //Cache Hit
            if (cache.containsKey(locate)) {
//                byte[] cacheHit = cache.get(locate);
//                byte[] result =  Arrays.copyOfRange(cacheHit, offset, offset + size);
//                return result;
                return Arrays.copyOfRange(cache.get(locate),offset,offset+size);
            } else { //Cache Hit Fail
                String rootDirectory = configuration.root;

                RandomAccessFile file = new RandomAccessFile(rootDirectory + "/" + locate, "rw");
                FileChannel fileChannel = file.getChannel();
                FileLock lock = fileChannel.lock();

                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
                //최근 내용을 Cache에 저장 - cache의 사이즈 초과라면, FIFO 방식으로 진행
                cache.put(locate, mappedByteBuffer.array());
                mappedByteBuffer.clear();

                //MappedByteBuffer 재할당
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, offset, size);
                lock.release();

                return mappedByteBuffer.array();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("요청된 line 이 파일에 기록된 line 수를 초과합니다.");
            return null;
        }
        return null;
    }

    @Override
    public String getLine(String locate, int lineCount) {
        byte[] fileByteArr = getData(locate); //자동으로 Cache 확인.
        try {
            String decodedString = new String(fileByteArr, "UTF-8");
            String[] decodedArray = decodedString.split(configuration.delimiter);
            return decodedArray[lineCount];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("요청된 line 이 파일에 기록된 line 수를 초과합니다.");
            return null;
        }
        return null;
    }

    @Override
    public void clearAll() {
        File rootLocate = new File(configuration.root);
        File[] contents = rootLocate.listFiles();
        for (File content : contents) {
            content.delete();
        }
        fileHashMap.clear();
    }
}

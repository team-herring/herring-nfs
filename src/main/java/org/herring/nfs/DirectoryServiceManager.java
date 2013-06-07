package org.herring.nfs;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 6.
 * Time: 오후 10:38
 */
public class DirectoryServiceManager implements DirectoryServiceInterface {
    static LinkedHashMap<String, byte[]> cache;
    final Configuration configuration = Configuration.getInstance();
    ConcurrentHashMap<String, Integer> fileHashMap;

    public DirectoryServiceManager() {
        fileHashMap = new ConcurrentHashMap<String, Integer>();

        //FIFO를 위한 초기화
        cache = new LinkedHashMap<String, byte[]>(configuration.cacheSize + 1, .75F, false) {
            protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
                return size() > configuration.cacheSize;
            }
        };

        File rootDir = new File(configuration.root);
        File[] rootContents = rootDir.listFiles();

        //이전에 저장되어 있는 파일의 목록 읽기
        //TODO: 가장 처음 생성 시 처리
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

            //최근 내용을 Cache에 저장 - cache의 사이즈 초과라면, FIFO 방식으로 진행
            cache.put(locate, data.getBytes());

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
        if (fileHashMap.get(locate) == 1) {
            System.out.println("Already exist file!");
            return;
        }

        try {
            String rootDirectory = configuration.root;

            RandomAccessFile addedFile = new RandomAccessFile(rootDirectory + "/" + locate, "rw");
            FileChannel fileChannel = addedFile.getChannel();

            //쓰기 중인 파일 채널을 Lock
            FileLock lock = fileChannel.lock();
            for (String aString : data) {
                ByteBuffer buffer = ByteBuffer.allocate(aString.length() + 2);
                buffer.put(aString.getBytes());
                buffer.put(configuration.delimiter.getBytes());
                buffer.flip();

                fileChannel.write(buffer);

                buffer.clear();
            }
            lock.release();
            addedFile.close();
            fileChannel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getData(String locate) {
        if (fileHashMap.get(locate) == null) {
            System.out.println("Do not exist " + locate);
            return null;
        }
        try {
            String rootDirectory = configuration.root;

            RandomAccessFile file = new RandomAccessFile(rootDirectory + "/" + locate, "rw");
            FileChannel fileChannel = file.getChannel();
            FileLock lock = fileChannel.lock();
            MappedByteBuffer mappedByteBuffer;
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            lock.release();

            return mappedByteBuffer.array();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] getData(String locate, int offset, int size) {
        if (fileHashMap.get(locate) == null) {
            System.out.println("Do not exist " + locate);
            return null;
        }
        try {
            String rootDirectory = configuration.root;

            RandomAccessFile file = new RandomAccessFile(rootDirectory + "/" + locate, "rw");
            FileChannel fileChannel = file.getChannel();
            FileLock lock = fileChannel.lock();
            MappedByteBuffer mappedByteBuffer;
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, offset, size);
            lock.release();

            return mappedByteBuffer.array();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getLine(String locate, int lineCount) {
        byte[] fileByteArr = getData(locate);
        try {
            String decodedString = new String(fileByteArr, "UTF-8");
            String[] decodedArray = decodedString.split(configuration.delimiter);
            return decodedArray[lineCount];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("요청된 line 이 파일에 기록된 line 수를 초과합니다.");
        }
        return null;
    }


}

package org.herring.nfs.client;

import org.herring.core.protocol.ClientComponent;
import org.herring.core.protocol.codec.HerringCodec;
import org.herring.core.protocol.codec.SerializableCodec;
import org.herring.core.protocol.handler.MessageHandler;
import org.herring.core.protocol.handler.SyncMessageHandler;
import org.herring.nfs.command.*;
import org.herring.nfs.response.Response;

import java.util.List;

/**
 * NetworkFileSystemServer에서 해당 연산 수행을 요청 할 Client Class
 * API를 Call 하기 이전에 openConnection() 을 수행하여 연결을 먼저 해야 한다.
 * <p/>
 * TODO : Cache 추가
 * <p/>
 * User: hyunje
 * Date: 13. 6. 9.
 * Time: 오전 11:21
 */
public class NetworkFileSystemClient {
    private ClientComponent clientComponent;

    public NetworkFileSystemClient(String host, int port) {
        System.out.println("ClientComponent Initialize");
        HerringCodec codec = new SerializableCodec();
        MessageHandler messageHandler = new SyncMessageHandler();
        clientComponent = new ClientComponent(host, port, codec, messageHandler);
    }

    /**
     * Server에 연결
     *
     * @throws Exception
     */
    public void openConnection() throws Exception {
        clientComponent.start();
    }

    /**
     * Server와의 연결 종료
     *
     * @throws Exception
     */
    public void closeConnection() throws Exception {
        clientComponent.stop();
    }

    /**
     * File System의 원하는 위치에 데이터 추가.
     *
     * @param locate 위치
     * @param data   데이터
     * @throws InterruptedException
     */
    public boolean putData(String locate, String data) throws InterruptedException {
        if (!clientComponent.isActive()) {
            System.out.println("NetworkFileSystemClient가 실행중이지 않습니다.");
            return false;
        }
        PutDataWithLocateAndData command = new PutDataWithLocateAndData(locate, data);

        clientComponent.getNetworkContext().sendObject(command);
        clientComponent.getNetworkContext().waitUntil("received");
        Response response = (Response) clientComponent.getNetworkContext().getMessageFromQueue();
        return (Boolean) response.getResponse();

    }


    /**
     * File System의 원하는 위치에 다수의 데이터 한번에 추가
     *
     * @param locate   위치
     * @param dataList 데이터 List
     * @throws InterruptedException
     */


    public boolean putData(String locate, List<String> dataList) throws InterruptedException {
        if (!clientComponent.isActive()) {
            System.out.println("NetworkFileSystemClient가 실행중이지 않습니다.");
            return false;
        }
        PutDataWithLocateAndDataList command = new PutDataWithLocateAndDataList(locate, dataList);
        clientComponent.getNetworkContext().sendObject(command);
        clientComponent.getNetworkContext().waitUntil("received");
        Response response = (Response) clientComponent.getNetworkContext().getMessageFromQueue();
        boolean result = (Boolean) response.getResponse();
        return result;
    }

    /**
     * File System의 원하는 위치에 존재하는 파일을 읽는다.
     *
     * @param locate 위치
     * @return String 형태의 데이터 (delimiter : '\n')
     * @throws InterruptedException
     */
    public String getData(String locate) throws InterruptedException {
        if (!clientComponent.isActive()) {
            System.out.println("NetworkFileSystemClient가 실행중이지 않습니다.");
            return null;
        }
        GetDataWithLocate command = new GetDataWithLocate(locate);
        clientComponent.getNetworkContext().sendObject(command);
        clientComponent.getNetworkContext().waitUntil("received");
        Response response = (Response) clientComponent.getNetworkContext().getMessageFromQueue();
        return (String) response.getResponse();
    }

    /**
     * File System의 원하는 위치에 존재하는 파일을 읽는다.
     * 특정 offset부터 특정 크기만큼의 데이터를 읽는다.
     *
     * @param locate 위치
     * @param offset offset
     * @param size   크기
     * @return String 형태의 데이터 (delimiter :'\n')
     * @throws InterruptedException
     */
    public String getData(String locate, int offset, int size) throws InterruptedException {
        if (!clientComponent.isActive()) {
            System.out.println("NetworkFileSystemClient가 실행중이지 않습니다.");
            return null;
        }
        GetDataWithLocateAndOffset command = new GetDataWithLocateAndOffset(locate,offset,size);
        clientComponent.getNetworkContext().sendObject(command);
        clientComponent.getNetworkContext().waitUntil("received");

        Response response = (Response) clientComponent.getNetworkContext().getMessageFromQueue();
        return (String) response.getResponse();
    }

    /**
     * File System의 원하는 위치에 존재하는 파일을 읽는다.
     * 특정 line만 읽는다.
     *
     * @param locate    위치
     * @param linecount line number
     * @return String 형태의 데이터
     * @throws InterruptedException
     */

    public String getLine(String locate, int linecount) throws InterruptedException {
        if (!clientComponent.isActive()) {
            System.out.println("NetworkFileSystemClient가 실행중이지 않습니다.");
            return null;
        }

        GetLineWithLocateAndLinecount command = new GetLineWithLocateAndLinecount(locate,linecount);
        clientComponent.getNetworkContext().sendObject(command);
        clientComponent.getNetworkContext().waitUntil("received");

        Response response = (Response)clientComponent.getNetworkContext().getMessageFromQueue();
        return (String)response.getResponse();
    }
}

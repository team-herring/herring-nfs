package org.herring.nfs;

import org.herring.core.nfs.NetworkFileSystemAPIHandler;
import org.herring.core.protocol.NetworkContext;
import org.herring.core.protocol.ServerComponent;
import org.herring.core.protocol.codec.HerringCodec;
import org.herring.core.protocol.codec.SerializableCodec;
import org.herring.core.protocol.handler.AsyncMessageHandler;
import org.herring.core.protocol.handler.MessageHandler;
import org.junit.Assert;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 9.
 * Time: 오전 12:56
 */
public class DirectoryServiceManagerDaemon {
    private final static int port = 9300;
    private ServerComponent serverComponent;
    private static DirectoryServiceManager manager = new DirectoryServiceManager();

    public static void main(String[] args) throws Exception {
        final DirectoryServiceManagerDaemon managerDaemon = new DirectoryServiceManagerDaemon();
        HerringCodec codec = new SerializableCodec();
        MessageHandler messageHandler  =  new AsyncMessageHandler(){
            @Override
            public boolean messageArrived(NetworkContext context, Object data) throws Exception {
                NetworkFileSystemAPIHandler apiHandler = (NetworkFileSystemAPIHandler) data;
                byte[] responseResult = null;

                switch (apiHandler.getCommand()){
                    case putData_locate_data:
                        manager.putData(apiHandler.getLocate(), apiHandler.getData());
                        responseResult = "putData_locate_data method executed".getBytes();
                        break;
                    case putData_locate_datalist:
                        manager.putData(apiHandler.getLocate(), apiHandler.getDataList());
                        responseResult = "putData_locate_dataList method executed".getBytes();
                        break;
                    case getData_locate:
                        responseResult = manager.getData(apiHandler.getLocate());
                        break;
                    case getData_locate_offset_size:
                        responseResult = manager.getData(apiHandler.getLocate(), apiHandler.getOffset(), apiHandler.getSize());
                        break;
                    case getLine_locate_linecount:
                        responseResult = manager.getLine(apiHandler.getLocate(),apiHandler.getLinecount()).getBytes();
                        break;
                    default:
                        System.out.println("Command Error");
                        responseResult = "Command Error".getBytes();
                }

                Assert.assertNotNull(responseResult);
                context.sendObject(responseResult);

                return true; // 비동기 통신이므로 현재 핸들러에서 메시지를 사용했음을 알린다.
            }
        };
        managerDaemon.serverComponent = new ServerComponent(port,codec,messageHandler);
        //Daemon 시작
        System.out.println("DirectoryServiceManager를 시작합니다.");
        managerDaemon.serverComponent.start();
    }
}
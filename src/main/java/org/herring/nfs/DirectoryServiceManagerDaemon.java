package org.herring.nfs;

import org.herring.core.protocol.NetworkContext;
import org.herring.core.protocol.ServerComponent;
import org.herring.core.protocol.codec.HerringCodec;
import org.herring.core.protocol.codec.SerializableCodec;
import org.herring.core.protocol.handler.AsyncMessageHandler;
import org.herring.core.protocol.handler.MessageHandler;
import org.herring.nfs.command.Command;
import org.herring.nfs.command.CommandExecutor;
import org.herring.nfs.response.Response;

/**
 * Network File System Server의 Daemon.
 * clearOnStart flag 를 이용해 처음 시작할 때 기존의 데이터를 삭제할 것인지 여부를 판별한다.
 *
 * User: hyunje
 * Date: 13. 6. 9.
 * Time: 오전 12:56
 */
public class DirectoryServiceManagerDaemon {
    private final static int port = 9300;
    private static DirectoryServiceManager manager = DirectoryServiceManager.getInstance();
    private ServerComponent serverComponent;
    private final static boolean clearOnStart = true;

    public static void main(String[] args) throws Exception {
        final DirectoryServiceManagerDaemon managerDaemon = new DirectoryServiceManagerDaemon();

        if(clearOnStart){
            System.out.println("Network File System Daemon을 실행합니다. 기존 데이터를 삭제합니다.");
            manager.clearAll();
        }
        HerringCodec codec = new SerializableCodec();

        MessageHandler messageHandler = new AsyncMessageHandler() {
            @Override
            public boolean messageArrived(NetworkContext context, Object data) throws Exception {
                //Command 실행 준비
                CommandExecutor commandExecutor = new CommandExecutor(manager);
                Command requestedCommand = (Command) data;
                requestedCommand.setExecutor(commandExecutor);
                requestedCommand.registerToExecutor();

                //Command 실행
                Response response = requestedCommand.execute();

                //수행 결과 Client로 전송
                context.sendObject(response);

                return true; // 비동기 통신이므로 현재 핸들러에서 메시지를 사용했음을 알린다.
            }
        };
        managerDaemon.serverComponent = new ServerComponent(port, codec, messageHandler);
        //Daemon 시작
        System.out.println("DirectoryServiceManager를 시작합니다.");
        managerDaemon.serverComponent.start();
    }
}

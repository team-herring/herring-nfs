package org.herring.nfs;

import org.herring.core.protocol.NetworkContext;
import org.herring.core.protocol.ServerComponent;
import org.herring.core.protocol.codec.HerringCodec;
import org.herring.core.protocol.codec.SerializableCodec;
import org.herring.core.protocol.handler.AsyncMessageHandler;
import org.herring.core.protocol.handler.MessageHandler;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 9.
 * Time: 오전 12:56
 */
public class DirectoryServiceManagerDaemon {

    private final static int port = 9300;

    private ServerComponent serverComponent;

    public static void main(String[] args) throws Exception {
        final DirectoryServiceManagerDaemon managerDaemon = new DirectoryServiceManagerDaemon();
        HerringCodec codec = new SerializableCodec();
        MessageHandler messageHandler  =  new AsyncMessageHandler(){
            @Override
        public boolean messageArrived(NetworkContext context, Object data) throws Exception{
                context.sendObject("Message Arrived!");
                return true;
            }
        };

        managerDaemon.serverComponent = new ServerComponent(port,codec,messageHandler);
        //Daemon 시작
        System.out.println("DirectoryServiceManager를 시작합니다.");
        managerDaemon.serverComponent.start();
    }
}

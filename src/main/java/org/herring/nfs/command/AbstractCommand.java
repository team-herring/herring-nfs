package org.herring.nfs.command;

import org.herring.nfs.CommandType;
import org.herring.nfs.response.Response;

/**
 * Command의 추상객체
 *
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 8:50
 */
public abstract class AbstractCommand implements Command {
    protected CommandExecutor executor = null;
    protected CommandType type;

    //Command Type 지정
    public AbstractCommand(CommandType type) {
        setType(type);
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    //명령어를 수행 할 객체 지정
    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }


    public abstract Response execute();

    public abstract void registerToExecutor();
}

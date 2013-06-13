package org.herring.nfs.command;

import org.herring.nfs.CommandType;
import org.herring.nfs.response.Response;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 8:50
 */
public abstract class AbstractCommand implements Command {
    protected CommandExecutor executor = null;
    protected CommandType type;

    public AbstractCommand(CommandType type) {
        setType(type);
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public abstract Response execute();

    public abstract void registerToExecutor();
}

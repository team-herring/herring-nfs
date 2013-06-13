package org.herring.nfs.command;

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

    public abstract void execute();

    public abstract void registerToExecutor();

    enum CommandType {
        PUT, GET
    }
}

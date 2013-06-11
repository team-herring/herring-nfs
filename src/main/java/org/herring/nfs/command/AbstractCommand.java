package org.herring.nfs.command;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 8:50
 */
public abstract class AbstractCommand implements Command {
    private CommandType type;

    public AbstractCommand(CommandType type) {
        setType(type);
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public abstract void execute();

    enum CommandType {
        PUT, GET
    }
}

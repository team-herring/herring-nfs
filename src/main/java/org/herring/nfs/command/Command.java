package org.herring.nfs.command;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 3:43
 */
public interface Command {
    void execute();

    void setExecutor(CommandExecutor executor);

    void registerToExecutor();
}

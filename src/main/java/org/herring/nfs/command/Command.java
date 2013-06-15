package org.herring.nfs.command;

import org.herring.nfs.response.Response;

/**
 * Network File System 에서 사용될 기능들의 Command 객체.
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 3:43
 */
public interface Command {
    Response execute();

    void setExecutor(CommandExecutor executor);

    void registerToExecutor();
}

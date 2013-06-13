package org.herring.nfs.command;

import org.herring.nfs.DirectoryServiceManager;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 10:49
 */
public class CommandExecutor {
    private DirectoryServiceManager manager;
    private PutDataWithLocateAndData putData_locate_data;

    public CommandExecutor(DirectoryServiceManager manager) {
        this.manager = manager;
    }

    public void registerPutDataWithLocateAndData(PutDataWithLocateAndData command) {
        this.putData_locate_data = command;
    }

    public void execut_putData_locate_data() {
        manager.putData(putData_locate_data.getLocate(), putData_locate_data.getData());
    }
}

package org.herring.nfs.command;

import org.herring.nfs.DirectoryServiceManager;
import org.herring.nfs.response.PutCommandResponse;
import org.herring.nfs.response.Response;

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

    public Response execute_putData_locate_data() {
        boolean result = manager.putData(putData_locate_data.getLocate(), putData_locate_data.getData());
        return new PutCommandResponse(result);
    }
}

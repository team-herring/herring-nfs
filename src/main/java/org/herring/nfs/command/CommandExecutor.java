package org.herring.nfs.command;

import org.herring.nfs.DirectoryServiceManager;
import org.herring.nfs.response.GetCommandResponse;
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
    private PutDataWithLocateAndDataList putData_locate_dataList;
    private GetDataWithLocate getData_locate;

    public CommandExecutor(DirectoryServiceManager manager) {
        this.manager = manager;
    }


    //putData(String locate, String data)
    public void registerPutDataWithLocateAndData(PutDataWithLocateAndData command) {
        this.putData_locate_data = command;
    }

    public Response execute_putData_locate_data() {
        boolean result = manager.putData(putData_locate_data.getLocate(), putData_locate_data.getData());
        return new PutCommandResponse(result);
    }


    //putData(String locate, List<String> data)
    public void registerPutDataWithLocateAndDataList(PutDataWithLocateAndDataList command){
        this.putData_locate_dataList = command;
    }

    public Response execute_putData_locate_dataList(){
        boolean result = manager.putData(putData_locate_dataList.getLocate(),putData_locate_dataList.getData());
        return new PutCommandResponse(result);
    }


    //getData(String locate)
    public void registerGetDataWithLocate(GetDataWithLocate command) {
        this.getData_locate = command;
    }
    public Response execute_getDate_locate(){
        byte[] result = manager.getData(getData_locate.getLocate());
        return new GetCommandResponse(result);
    }

}

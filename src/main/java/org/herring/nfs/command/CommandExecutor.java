package org.herring.nfs.command;

import org.herring.nfs.DirectoryServiceManager;
import org.herring.nfs.response.GetCommandResponse;
import org.herring.nfs.response.PutCommandResponse;
import org.herring.nfs.response.Response;

/**
 * Command 객체들의 Mediator 객체.
 * Command 를 register 함수를 이용해 Executor로 등록을 한 후에,
 * Command 객체에서 정의된 Command.execute() 를 이용해 Command를 수행한다.
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 10:49
 */
public class CommandExecutor {
    private DirectoryServiceManager manager;
    private PutDataWithLocateAndData putData_locate_data;
    private PutDataWithLocateAndDataList putData_locate_dataList;
    private GetDataWithLocate getData_locate;
    private GetDataWithLocateAndOffset getData_locate_offset_size;
    private GetLineWithLocateAndLinecount getLine_locate_linecount;

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
    public Response execute_getData_locate(){
        byte[] result = manager.getData(getData_locate.getLocate());
        return new GetCommandResponse(result);
    }

    //getData(String locate, int offset, int size)
    public void registerGetDataWithLocateAndOffset(GetDataWithLocateAndOffset command) {
        this.getData_locate_offset_size = command;
    }
    public Response execute_getData_locate_offset_size() {
        byte[] result = manager.getData(getData_locate_offset_size.getLocate(),getData_locate_offset_size.getOffset(),getData_locate_offset_size.getSize());
        return new GetCommandResponse(result);
    }

    public void registerGetLineWithLocateAndLinecount(GetLineWithLocateAndLinecount command) {
        this.getLine_locate_linecount = command;
    }
    public Response execute_getLine_locate_linecount(){
        String result = manager.getLine(getLine_locate_linecount.getLocate(),getLine_locate_linecount.getLinecount());
        return new GetCommandResponse(result);
    }
}

package org.herring.nfs.command;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 10:40
 */
public class PutDataWithLocateAndData extends AbstractCommand {

    private String locate;
    private String data;


    public PutDataWithLocateAndData(String locate, String data) {
        super(CommandType.PUT);
        this.locate = locate;
        this.data = data;

    }

    @Override
    public void registerToExecutor() {
        this.executor.registerPutDataWithLocateAndData(this);
    }

    @Override
    public void execute() {
        executor.execut_putData_locate_data();
    }

    public String getLocate() {
        return locate;
    }

    public String getData() {
        return data;
    }
}

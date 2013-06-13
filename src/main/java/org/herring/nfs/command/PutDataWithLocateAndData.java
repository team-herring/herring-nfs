package org.herring.nfs.command;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 10:40
 */
public class PutDataWithLocateAndData extends AbstractCommand {
    private CommandExecutor register;

    private String locate;
    private String data;


    public PutDataWithLocateAndData(CommandExecutor register, String locate, String data) {
        super(CommandType.PUT);
        this.locate = locate;
        this.data = data;

        this.register = register;
        this.register.registerPutDataWithLocateAndData(this);
    }

    @Override
    public void execute() {
        register.execut_putData_locate_data();
    }

    public String getLocate() {
        return locate;
    }

    public String getData() {
        return data;
    }
}

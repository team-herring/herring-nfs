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
    public void execute() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getLocate() {
        return locate;
    }

    public String getData() {
        return data;
    }
}

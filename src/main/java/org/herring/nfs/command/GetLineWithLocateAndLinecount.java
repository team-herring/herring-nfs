package org.herring.nfs.command;

import org.herring.nfs.CommandType;
import org.herring.nfs.response.Response;

import java.io.Serializable;

/**
 * getLine(String location, int linecount)
 * 함수에 대한 Command 객체
 *
 * - Client에서 API를 호출 할 때
 *  호출하는 정보를 이용해 이 클래스를 초기화 하여 Server로 전송한다.
 * - Server에서 API를 받을 때
 *  Client에서 보낸 Object를 Command 객체로 UpCasting하여 사용.
 *
 * User: hyunje
 * Date: 13. 6. 11.
 * Time: 오전 10:40
 */
public class GetLineWithLocateAndLinecount extends AbstractCommand implements Serializable {

    private String locate;
    private int linecount;


    public GetLineWithLocateAndLinecount(String locate, int linecount) {
        super(CommandType.GET);
        this.locate = locate;
        this.linecount = linecount;
    }

    @Override
    public void registerToExecutor() {
        this.executor.registerGetLineWithLocateAndLinecount(this);
    }

    @Override
    public Response execute() {
        Response response = executor.execute_getLine_locate_linecount();
        return response;
    }

    public String getLocate() {
        return locate;
    }

    public int getLinecount() {
        return linecount;
    }
}

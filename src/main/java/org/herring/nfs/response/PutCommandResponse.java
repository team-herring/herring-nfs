package org.herring.nfs.response;

import org.herring.nfs.CommandType;

/**
 * Put 형태의 Command의 Response.
 * Boolean의 return을 갖는다.
 * User: hyunje
 * Date: 13. 6. 13.
 * Time: 오후 9:38
 */
public class PutCommandResponse implements Response {
    CommandType type;
    private boolean response;

    public PutCommandResponse(Object response) {
        this.type = CommandType.PUT;
        try {
            this.response = (Boolean) response;
        } catch (ClassCastException e) {
            System.out.println("setResponse Casting 오류!");
        }
    }

    @Override
    public Object getResponse() {
        if (type == null)
            return null;
        return response;
    }
}

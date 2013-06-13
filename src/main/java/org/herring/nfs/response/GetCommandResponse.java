package org.herring.nfs.response;

import org.herring.nfs.CommandType;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 13.
 * Time: 오후 9:31
 */
public class GetCommandResponse implements Response {
    CommandType type;
    String response;

    public GetCommandResponse() {
        this.type = CommandType.GET;
        this.response = "";
    }

    @Override
    public Object getResponse() {
        return response;
    }

    @Override
    public void setResponse(Object response) {
        try {
            this.response = (String) response;
        } catch (ClassCastException e) {
            System.out.println("setResponse Casting 오류");
        }
    }
}

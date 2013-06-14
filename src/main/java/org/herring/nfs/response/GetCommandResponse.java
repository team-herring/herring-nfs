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
    private String response;

    public GetCommandResponse(Object response){
        try {
            this.type = CommandType.GET;
            this.response = (String) response;
        } catch (ClassCastException e) {
            System.out.println("setResponse Casting 오류");
        }
    }

    @Override
    public Object getResponse() {
        if(type == null || response == null)
            return null;
        return response;
    }
}
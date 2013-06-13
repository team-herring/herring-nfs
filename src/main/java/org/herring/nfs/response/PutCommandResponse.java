package org.herring.nfs.response;

import org.herring.nfs.CommandType;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 13.
 * Time: 오후 9:38
 */
public class PutCommandResponse implements Response {
    CommandType type;
    boolean response;

    public PutCommandResponse(){
        this.type = CommandType.PUT;
        this.response = false;
    }

    @Override
    public Object getResponse() {
        return response;
    }

    @Override
    public void setResponse(Object response) {
        try{
            this.response = (Boolean)response;
        } catch (ClassCastException e){
            System.out.println("setResponse Casting 오류!");
        }
    }
}

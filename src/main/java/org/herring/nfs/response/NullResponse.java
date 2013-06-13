package org.herring.nfs.response;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 13.
 * Time: 오후 10:15
 */
public class NullResponse implements Response {
    @Override
    public Object getResponse() {
        return null;
    }
}

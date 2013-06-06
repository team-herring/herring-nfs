package org.herring.nfs;

import java.util.List;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 6.
 * Time: 오후 10:39
 */
public interface DirectoryServiceInterface {
    void putData(String locate, String data);
    void putData(String locate, List<String> data);
}

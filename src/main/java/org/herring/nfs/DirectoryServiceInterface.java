package org.herring.nfs;

import java.util.List;

/**
 * User: hyunje
 * Date: 13. 6. 6.
 * Time: 오후 10:39
 */
public interface DirectoryServiceInterface {
    void putData(String locate, String data);
    void putData(String locate, List<String> data);
    byte[] getData(String locate);
    byte[] getData(String locate, int offset, int size);
    String getLine(String locate, int lineCount);
}

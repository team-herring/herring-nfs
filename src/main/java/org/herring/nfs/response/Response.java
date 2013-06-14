package org.herring.nfs.response;

import java.io.Serializable;

/**
 * DirectoryServiceManagerDaemon에서 Command.execute()를 수행했을 때 DirectoryManager에서 수행한 결과로 사용 할 Class
 * Response를 받아서 사용하는 부분에서는 어떤 형태의 Response인지 판단하여 Down Casting해 주어야 한다.
 * User: hyunje
 * Date: 13. 6. 13.
 * Time: 오후 8:43
 */
public interface Response extends Serializable {
    Object getResponse();
}

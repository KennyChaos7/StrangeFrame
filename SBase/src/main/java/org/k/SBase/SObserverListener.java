package org.k.SBase;

import org.k.SBase.Listener.BaseListener;

/**
 * Created by Kenny on 18-11-5.
 */
@Deprecated
public interface SObserverListener extends BaseListener<String,Object> {

    /*
        更新数据
     */
    int MSG_TOPIC_DATA_UPDATE = 1;

    /*
        更新通知
     */
    int MSG_TOPIC_Notification = 2;

}
package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class BindToGroupResponseProtocal extends ResponseProtocal{
    public BindToGroupResponseProtocal(){
        super();
        this.setChannelType(ConstVar.SYSTEM_CHANNEL_QUEUE);
        this.setAction(ProtocalMap.Bind_to_group_channel_Response);
    }
}

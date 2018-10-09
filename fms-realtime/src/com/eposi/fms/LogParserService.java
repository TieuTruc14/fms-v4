package com.eposi.fms;

import com.eposi.common.util.AbstractBean;
import com.eposi.fms.track.FmsProto;
import com.google.protobuf.ExtensionRegistry;


public class LogParserService extends AbstractBean {
    private static final long serialVersionUID = -1311253242745890824L;
    /**
	 * FOR PROTOCOL BUFFER TO BE ABLE TO USE EXTENSION. DONT TOUCH IT!
	 */
	private ExtensionRegistry registry;

    public FmsProto.BaseMessage Parser(byte[] data) throws Exception {
        FmsProto.BaseMessage msg = null;
        try {
            msg = FmsProto.BaseMessage.newBuilder().mergeFrom(data, registry).build();
        } catch(Exception pbE) {

        }
        return msg;
    }

    public FmsProto.WayPoint ParserWayPoint(FmsProto.BaseMessage msg) throws Exception {
        return msg.getExtension(FmsProto.WayPoint.msg);
    }

    public FmsProto.WayPointBatch ParserWayPointBatch(FmsProto.BaseMessage msg) throws Exception {
        return msg.getExtension(FmsProto.WayPointBatch.msg);
    }

	public void start() {
		registry = ExtensionRegistry.newInstance();
		FmsProto.registerAllExtensions(registry);
	}

}

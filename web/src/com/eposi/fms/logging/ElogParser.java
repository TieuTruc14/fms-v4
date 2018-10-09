package com.eposi.fms.logging;

import com.eposi.fms.model.*;

public class ElogParser {

	public static String getJSONActivity(Activity item) {
		if (item == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
			sb.append("\"objectId\":\"").append(item.getObjectId()).append("\",");
			sb.append("\"objectName\":\"").append(item.getObjectName()).append("\",");
			sb.append("\"actionName\":\"").append(item.getActionName()).append("\",");
			sb.append("\"actorName\":\"").append(item.getActorName()).append("\",");
			sb.append("\"context\":\"").append(item.getContext()).append("\",");
			sb.append("\"date\":").append(item.getDate().getTime()/1000L).append(",");
			sb.append("\"icon\":\"").append("<i class=\\\"fa fa-stop time-icon bg-dark\\\"></i>").append("\",");
			sb.append("\"passive\":\"").append(item.isPassive()).append("\",");
			sb.append("\"iObjectName\":\"").append(item.getIndirectObjectName()).append("\"");
		sb.append("}");
		return sb.toString();
	}

}

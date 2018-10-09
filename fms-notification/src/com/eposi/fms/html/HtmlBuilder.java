package com.eposi.fms.html;

import com.eposi.common.util.FormatUtil;
import com.eposi.fms.model.*;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

public class HtmlBuilder {
	public static String toHtml(List<Xmit> items) {
		if (items == null) {
			throw new NullPointerException("msg is null!");
		}

		StringBuffer sb = new StringBuffer();

		sb.append("<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#dcf0f8\"");
		sb.append(" style=\"margin:0;padding:0;background-color:#f2f2f2;width:100%!important;font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#444;line-height:18px\">");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td align=\"center\" valign=\"top\"");
		sb.append(" style=\"font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#444;line-height:18px;font-weight:normal\">");
		sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"margin-top:15px\">");
		sb.append("<tbody>");
		sb.append(" <tr>");
		sb.append("<td>");
		sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"line-height:0\">");
		sb.append("<tbody>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td align=\"center\" valign=\"bottom\">");
		sb.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td valign=\"top\" bgcolor=\"#FFFFFF\" width=\"100%\" style=\"padding:0\">");
		sb.append("<a style=\"border:medium none;text-decoration:none;color:#007ed3\" href=\"http://giamsatgps.vn/\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=http://fms.eposi.vn/fms/login.action?request_locale=vi_VN\">");
		sb.append("<img alt=\"giamsatgps.vn\" src=\"http://qc31.vn/assets/images/logo.Eposi.gif\" style=\"border:none;outline:none;text-decoration:none;display:inline;min-height:auto\" class=\"CToWUd\">");
		sb.append("</a>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr style=\"background:#fff\">");
		sb.append("<td align=\"left\" width=\"600\" height=\"auto\" style=\"padding:15px\">");
		sb.append("<table>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<h1 style=\"font-size:14px;font-weight:bold;color:#444;padding:0 0 5px 0;margin:0\">");
		sb.append("Thông tin cảnh báo mất tín hiệu từ hệ thống giám sát hành trình !");
		sb.append("</h1>");
		sb.append("<p style=\"margin:4px 0;font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#444;line-height:18px;font-weight:normal\">");
		sb.append("Chúng tôi gửi tới quý khách thông tin chi tiết mất tín hiệu giám sát hành trình các phương tiện của quý công ty.");
		sb.append("</p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<h2 style=\"text-align:left;margin:10px 0;border-bottom:1px solid #ddd;padding-bottom:5px;font-size:13px;color:#d29400\">");
		sb.append("THÔNG TIN CHI TIẾT MẤT TÍN HIỆU</h2>");
		sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" style=\"background:#f5f5f5\">");
		sb.append(" <thead border=\"1px\">");
		sb.append("<tr>");
		sb.append("<th align=\"center\" bgcolor=\"#ffc333\"  ");
		sb.append("style=\"padding:6px 9px;color:#000;text-transform:uppercase;font-family:Arial,Helvetica,sans-serif;font-size:12px;line-height:14px;border:0.6px solid #b4b4b4;border-right:none;\">");
		sb.append("Biển số");
		sb.append("</th>");
		sb.append("<th align=\"center\" bgcolor=\"#ffc333\"");
		sb.append("style=\"padding:6px 9px;color:#000;text-transform:uppercase;font-family:Arial,Helvetica,sans-serif;font-size:12px;line-height:14px;border:0.5px solid #b4b4b4;\">");
		sb.append("Thời điểm mất");
		sb.append("</th>");
		sb.append("<th align=\"center\" bgcolor=\"#ffc333\"");
		sb.append("style=\"padding:6px 9px;color:#000;text-transform:uppercase;font-family:Arial,Helvetica,sans-serif;font-size:12px;line-height:14px;border:0.5px solid #b4b4b4;border-right:none;border-bottom:none;\">");
		sb.append("Địa chỉ");
		sb.append("</th>");
		sb.append("<th  align=\"center\" bgcolor=\"#ffc333\"");
		sb.append(" style=\"padding:6px 9px;color:#000;text-transform:uppercase;font-family:Arial,Helvetica,sans-serif;font-size:12px;line-height:14px;border:0.5px solid #b4b4b4;border-right:none;\">");
		sb.append("Lý do");
		sb.append("</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("");
		/**
		 * start xmit ∑
		 */
		for(int i=0;i<items.size();i++){
			if((i%2)==0){
				sb.append("<tbody bgcolor=\"#eee\" style=\"font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#444;line-height:18px\">");
			}else{
				sb.append("<tbody style=\"font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#444;line-height:18px\">");
			}
			sb.append("<tr>");
			sb.append(" <td align=\"center\" valign=\"top\" style=\"padding:3px 9px\">");
			sb.append("<span>" + items.get(i).getVehicle() + "</span></td>");
			String begin= FormatUtil.formatDate(items.get(i).getBeginTime(), "dd/MM/yyyy HH:mm:ss");
			String type="";
			if(items.get(i).getType()==1){
				type="Mất GPS";
			}else{
				type="Mất tín hiệu";
			}
			sb.append("<td align=\"center\" valign=\"top\" style=\"padding:3px 9px\">");
			sb.append("<span>"+begin+"</span></td>");
			sb.append("<td align=\"center\" valign=\"top\" style=\"padding:3px 9px\">");
			sb.append("<span>"+items.get(i).getAddress()+"</span></td>");
			sb.append("<td align=\"center\" valign=\"top\" style=\"padding:3px 9px\">");
			sb.append("<span>"+type+"</span>");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</tbody>");
		}
		/**
		 * end xmit
		 */
		sb.append("<tfoot style=\"font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#444;line-height:18px\">");
		sb.append("</tfoot>");
		sb.append("</table>");
		sb.append("<br>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<br>");
		sb.append("<p style=\"margin:10px 0 0 0;font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#444;line-height:18px;font-weight:normal\">");
		sb.append("Xin vui lòng liên hệ với đại lý gần nhất để được hỗ trợ.");
		sb.append("Hoặc gọi số điện thoại <strong style=\"color:#099202\">04.3783 5200</strong> số máy lẻ : 108, 210 (7-21h");
		sb.append("cả T7,CN). Đội ngũ Eposi luôn sẵn sàng hỗ trợ.");
		sb.append("</p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<p style=\"font-family:Arial,Helvetica,sans-serif;font-size:12px;margin:0;padding:0;line-height:18px;color:#444;font-weight:bold\">");
		sb.append("Một lần nữa Eposi cảm ơn quý khách.");
		sb.append("</p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td align=\"center\">");
		sb.append("<table width=\"600\">");
		sb.append("<tbody>");
		sb.append(" <tr>");
		sb.append("<td>");
		sb.append("<p style=\"font-family:Arial,Helvetica,sans-serif;font-size:11px;line-height:18px;color:#4b8da5;padding:10px 0;margin:0px;font-weight:normal\"");
		sb.append(" align=\"left\">");
		sb.append("Quý khách nhận được email này vì đã sử dụng dịch vụ giám sát hành trình của Eposi.<br>");
		sb.append("<b>Công ty thương mại Eposi:</b> 70/12 Đào Tấn, Phường Cống Vị, Ba Đình, Thành phố Hà Nội, Việt Nam");
		sb.append("</p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");

		return sb.toString();
	}


}

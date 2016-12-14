package autoETLsystem.service.imps;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * 项目名称：autoETLsystem<br>
 * *********************************<br>
 * <P>类名称：SendMailService</P>
 * *********************************<br>
 * <P>类描述：邮件发送服务类</P>
 * 创建人：王川<br>
 * 创建时间：2016-4-25 下午4:13:27<br>
 * 修改人：王川<br>
 * 修改时间：2016-4-25 下午4:13:27<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class SendMailService {
	private String host;
	private String protocol = "smtp";
	private String username;
	private String password;
	private String from;
	private String nick = "系统管理员";

	
	public SendMailService(String host,String username,String password){
		this.host = host;
		this.username = username;
		this.from = username;
		this.password = password;
	}

	public boolean sendMail(String to, String subject, String body) throws AddressException, MessagingException, UnsupportedEncodingException {
		// 参数修饰
		if (body == null) {
			body = "";
		}
		if (subject == null) {
			subject = "无主题";
		}
		// 创建Properties对象
		Properties props = System.getProperties();
		// 创建信件服务器
		props.put("mail.smtp.host", host);
		props.put("mail.transport.protocol", protocol);
		props.put("mail.smtp.auth", "true"); // 通过验证
		// 得到默认的对话对象
		Session session = Session.getDefaultInstance(props, null);
		//session.setDebug(true);
		// 创建一个消息，并初始化该消息的各项元素
		MimeMessage msg = new MimeMessage(session);
		nick = MimeUtility.encodeText(nick);
		msg.setFrom(new InternetAddress(nick + "<" + from + ">"));
		// 创建收件人列表
		if (to != null && to.trim().length() > 0) {
			String[] arr = to.split(",");
			int receiverCount = arr.length;
			if (receiverCount > 0) {
				InternetAddress[] address = new InternetAddress[receiverCount];
				for (int i = 0; i < receiverCount; i++) {
					address[i] = new InternetAddress(arr[i]);
				}
				msg.addRecipients(Message.RecipientType.TO, address);
				msg.setSubject(subject);
				// 设置邮件正文
				msg.setText(body);
				// 设置信件头的发送日期
				msg.setSentDate(new Date());
				msg.saveChanges();
				// 发送信件
				Transport transport = session.getTransport();
				transport.connect(host, username, password);
				transport.sendMessage(msg,msg.getRecipients(Message.RecipientType.TO));
				transport.close();
				return true;
			} else {
				System.out.println("None receiver!");
				return false;
			}
		} else {
			System.out.println("None receiver!");
			return false;
		}
	}
	
	public static void main(String[] args) {
		try {
			//SendMailService sendMailService = new SendMailService("smtp.sina.com", "wc_test@sina.com", "wc_test123456");
			SendMailService sendMailService = new SendMailService("smtp.163.com", "wc_amp@163.com", "wc123456");
			sendMailService.sendMail("wc_test@sina.com", "test", "收到邮件请回复");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}


}

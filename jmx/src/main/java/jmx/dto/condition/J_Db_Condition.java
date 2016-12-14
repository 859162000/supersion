package jmx.dto.condition;

import java.io.Serializable;

import jmx.dto.J_Server;


public class J_Db_Condition implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 7591379851667156691L;
	
	private J_Server server;

	public J_Server getServer() {
		return server;
	}

	public void setServer(J_Server server) {
		this.server = server;
	}
	
}

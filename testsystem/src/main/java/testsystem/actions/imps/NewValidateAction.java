package testsystem.actions.imps;

import com.opensymphony.xwork2.ActionSupport;

public class NewValidateAction extends ActionSupport{
    /** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  
	private String msg;  // 必须输入
    private int age;   // 在13和20之间
    public String getMsg()
    { 
        return msg;    
    }
    public void setMsg(String msg)
    {
        this.msg = msg;  
    } 
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
}
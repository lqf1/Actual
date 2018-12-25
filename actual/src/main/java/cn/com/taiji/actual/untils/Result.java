package cn.com.taiji.actual.untils;

import lombok.Data;

import java.io.Serializable;
/**
 * 返回结果工具类
 * @author zxx
 * @date 2018/12/17 10:50
 * @version 1.0
 */
@Data
public class Result implements Serializable {


	private static final long serialVersionUID = 8735555716171007361L;
	
	private int code;
	private Object data;
	private String msg;
	public Result(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public Result(int code,String msg, Object data) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

}

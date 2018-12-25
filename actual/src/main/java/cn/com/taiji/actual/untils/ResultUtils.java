package cn.com.taiji.actual.untils;
/**
 * 包装返回结果
 * @author zxx
 * @date 2018/12/17 10:50
 * @version 1.0
 */
public class ResultUtils {
	public static Result Success() {
		return new Result(1,"成功");
	}
	public static Result Success(String msg) {
		return new Result(1,msg);
	}
	public static Result Error() {
		return new Result(-1,"失败");
	}
}

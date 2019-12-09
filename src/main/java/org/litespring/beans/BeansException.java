package org.litespring.beans;

/**
 * @author YYDCYY
 * @create 2019-12-09
 *
 * 定义自己的异常类
 */
public class BeansException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BeansException(String msg) {
		super(msg);
	}

	public BeansException(String msg,Throwable cause) {
		super(msg, cause);
	}
}
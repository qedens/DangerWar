package me.skymc.wastelandwarcore.utils;

/**
 * 数字工具
 * 
 * @author sky
 * @since 2018年1月26日23:47:08
 */
public class NumberUtils {
	
	/**
	 * 根据对象识别出整数
	 * 
	 * @param object 对象
	 * @param defaultInt 默认值
	 * @return {@link Integer}
	 */
	public static Integer parserInteger(Object object, Integer defaultInt) {
		try {
			return Integer.valueOf(object.toString());
		}
		catch (Exception e) {
			return defaultInt;
		}
	}

}

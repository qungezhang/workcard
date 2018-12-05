package cn.stylefeng.guns.core.common.utils;

import cn.stylefeng.guns.core.common.exception.RRException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


/**
 * 数据校验
 */
public abstract class Assert {
	
	/**
	 * 验证对象是否为 null
	 * @param obj
	 * @return
	 */
    public static void isNull(Object object, String message) {
        if (isNull(object)) {
            throw new RRException(message);
        }
    }

	/**
	 * 验证对象是否为 null
	 *
	 * @param obj
	 * @return
	 */
	public static void isNull(Object object, String message, Integer code) {
		if (isNull(object)) {
			throw new RRException(message, code);
		}
	}
    /**
	 * 验证对象是否为 null
	 * @param obj
	 * @return
	 */
	public static <T> boolean isNull(T t) {
		if (null == t) {
			return true;
		}
		if (t instanceof Number && ((Number)t).doubleValue() == 0) {
			return true;
		}
		// t extends CharSequence
		if (t instanceof CharSequence && !StringUtils.hasText(t.toString())) {
			return true;
		}
		// t extends Collection
		if (t instanceof Collection && ((Collection<?>)t).isEmpty()) {
			return true;
		}
		// Map
		if (t instanceof Map && ((Map<?, ?>) t).isEmpty()) {
			return true;
		}
		// Array
		if (t.getClass().isArray() && Array.getLength(t) == 0) {
			return true;
		}
		return false;
	}

}

/*
 * 作者：赵敏
 */
package com.dhcc.dcs.fetch.hook;

import java.util.function.Function;

public class FetchTaskParkTimeHook implements Function<Object,Object>{

	public Object apply(Object t) {
		if(t instanceof Long)
			return 5L;
		return 0L;
	}

}

/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.publish;

import java.util.List;

public interface InterMessage {

	public List<Integer> intMessage();
	
	public List<Long> longMessage();
	
	public List<String> strMessage();
	
	public List<Object> objMessage();
	
}

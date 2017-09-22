/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.publish;

import lombok.Getter;

public abstract class Interaction implements InterMessage{

	@Getter
	protected boolean isVaild = true;
	
	public final int type = 1;
	
}

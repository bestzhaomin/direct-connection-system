/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.message.produce;

import com.dhcc.dcs.message.AbstractMessageTask;
import com.dhcc.dcs.publish.Interaction;

public class ProduceMessageFile extends AbstractMessageTask{

	public ProduceMessageFile() {
		super(null);
	}

	public void doTask(Interaction action) {
		
	}

	public Interaction apply(Interaction t) {
		return t;
	}

}

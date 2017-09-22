/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.fetch;

import java.util.function.Supplier;

import com.dhcc.dcs.publish.Interaction;
import com.dhcc.dcs.publish.PublisherService;

public abstract class AbstractFetchTask implements Runnable,Supplier<Interaction>{

	final PublisherService publisher;
	
	public AbstractFetchTask(PublisherService service) {
		publisher = service != null ? service : (action)->{};
	}
	
	public abstract void doTask();
	
	public final void run() {
		doTask();
		publisher.publish(get());
	}
}

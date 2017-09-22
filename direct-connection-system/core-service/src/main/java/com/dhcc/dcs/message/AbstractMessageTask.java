/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Function;

import com.dhcc.dcs.publish.DefaultPublisher;
import com.dhcc.dcs.publish.Interaction;
import com.dhcc.dcs.publish.PublisherService;

public abstract class AbstractMessageTask implements Function<Interaction,Interaction>{

	final BlockingQueue<Interaction> workqueue;
	
	final PublisherService publisher = DefaultPublisher.getDefaultPublisher();
	
	public AbstractMessageTask(BlockingQueue<Interaction> q) {
		workqueue = q == null ? new LinkedTransferQueue<Interaction>() : q;
	}
	
	private final Thread worker = new Thread(()->{
		_doTask();
	});
	
	public abstract void doTask(Interaction action);
	
	public void _doTask() {
		while(true)
			try {
				Interaction action = workqueue.take();
				doTask(action);
				publisher.publish(apply(action));
			} catch (InterruptedException ex) {
				// add log
			} catch (Exception ex) {
				// add log
			}
	}
	
	public void start() {
		worker.start();
	}
}

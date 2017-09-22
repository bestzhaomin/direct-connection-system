/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.publish;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class DefaultPublisher implements PublisherService{
	
	private DefaultPublisher() {}
	
	private static final DefaultPublisher publisher = new DefaultPublisher();
	
	public static DefaultPublisher getDefaultPublisher() { return publisher; }

	private Map<Integer,BlockingQueue<Interaction>> store = new HashMap<Integer,BlockingQueue<Interaction>>();
	
	public void publish(Interaction action) {
		if(action == null)
			return;
		BlockingQueue<Interaction> queue = store.get(action.type);
		if(queue != null)
			queue.add(action);
	}
	
	public void register(int key,BlockingQueue<Interaction> queue) {
		if(store.containsKey(key))
			throw new RuntimeException("DefaultPublisher's key is already exists");
		store.put(key, queue);
	}

}

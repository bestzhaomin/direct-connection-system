/*
 * 作者：赵敏
 */
package com.dhcc.dcs.fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;

public class FetchTaskGroup {

	static long t = TimeUnit.SECONDS.toNanos(5L);
	
	/**
	 * 用于扩展的Function接口，约束如下：
	 * key ----------- val
	 * 0   ----------- 组任务运行间隙时间计算的扩展  -- zhaomin
	 */
	private final Map<Integer,Function<Object,Object>> hook = new HashMap<Integer,Function<Object,Object>>();
	
	private long parkNanosTimes() {
		Function<Object, Object> f = hook.get(0);
		long time;
		return f != null && (time = (long) f.apply(System.currentTimeMillis())) >= 0 ? time : t;
	}
	
	private final Phaser phaser = new Phaser() {
	    protected boolean onAdvance(int phase, int registeredParties) {
	    	LockSupport.parkNanos(parkNanosTimes());
	        return false;
	    }
	};

	/**
	 * 工作线程，实际的工作线程采用重量级的Thread进行封装
	 * @author zhaomin
	 *
	 */
	static final class WorkThread extends Thread {

		final Runnable target;
		final Phaser phaser;

		public WorkThread(Runnable t, Phaser sync) {
			target = t;
			phaser = sync;
			phaser.register(); //每个
		}
		
		public WorkThread(Runnable t) {
			target = t;
			phaser = null;
		}

		public void run() {
			if (phaser == null)
				target.run();
			else
				for (;; phaser.arriveAndAwaitAdvance())
					target.run();
		}
	}

	/* 
	 * 用于动态接收任务，当任务组启动以后，_workers将转移至workers
	 */
	private List<WorkThread> _workers = new ArrayList<WorkThread>();
	
	private WorkThread[] workers;
	
	/**
	 * 注册任务
	 * @param worker 任务
	 */
	public void registerWorker(Runnable worker) {
		if(worker == null || _workers == null)
			return;
		_workers.add(new WorkThread(worker,phaser));
	}
	
	/**
	 * 注册扩展
	 * @param key
	 * @param val
	 */
	public void registerHook(int key,Function<Object,Object> val) {
		if(key < 0)
			throw new RuntimeException("the hook function's key must >= 0");
		if (hook.containsKey(key))
			throw new RuntimeException("the hook function is already exists");
		hook.put(key, val);
	}
	
	/**
	 * 启动任务组
	 */
	public void start() {
		int size = _workers.size();
		if(size == 0)
			throw new RuntimeException("FetchTaskGroup#start faild,there is no any workers");	
		workers = new WorkThread[size];
		for (int i = 0; i < size; workers[i] = _workers.get(i), 
							      workers[i++].start())
			;
		_workers = null;
	}
}

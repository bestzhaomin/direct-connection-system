/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.publish;

@FunctionalInterface
public interface PublisherService {
	
	public void publish(Interaction action);

}

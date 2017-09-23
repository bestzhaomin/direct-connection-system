/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.transaction;

import java.sql.Connection;

public abstract class AbstractTransactionTask {
	
	private final Connection con;
	
	public AbstractTransactionTask(Connection con) {
		this.con = con;
	}
	
	public final void execute(TransactionTodo td) {
		try {
			td.todo();
			con.commit();
		} catch (Exception ex) {
			needRollBack(con);
		}
	}
	
	static final void needRollBack(Connection con) {
		try {
			con.rollback();
		} catch (Exception igrone) {
			
		}
	}
	
}

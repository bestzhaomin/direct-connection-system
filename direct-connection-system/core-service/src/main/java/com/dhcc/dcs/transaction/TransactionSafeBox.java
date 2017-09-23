/**
 * 软件著作权：东华软件股份公司
 *
 * 作     者：zhaomin
 */
package com.dhcc.dcs.transaction;

import java.sql.Connection;

public class TransactionSafeBox extends AbstractTransactionTask{

	public TransactionSafeBox(Connection con) {
		super(con);
	}

}

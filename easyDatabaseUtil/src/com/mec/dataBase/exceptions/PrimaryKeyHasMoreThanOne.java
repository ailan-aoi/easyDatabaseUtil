package com.mec.dataBase.exceptions;

import java.io.PrintStream;

public class PrimaryKeyHasMoreThanOne extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7569166543457906299L;

	@Override
	public void printStackTrace(PrintStream s) {
		System.out.println("主键出现多个错误");
		super.printStackTrace(s);
	}
}

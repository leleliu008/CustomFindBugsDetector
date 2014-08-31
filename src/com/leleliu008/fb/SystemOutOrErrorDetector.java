package com.leleliu008.fb;

import edu.umd.cs.findbugs.BugReporter;

/**
 * 检测程序中是否有System.out或者System.error、e.printStacktrace()输出
 * 因为一般的程序都应该使用Log系统，并且有开关，在release的时候是不会打开Log的开关的。
 * 
 * @author leleliu008 2013-11-23
 */
public class SystemOutOrErrorDetector extends CustomDetector {

	public SystemOutOrErrorDetector(BugReporter bugReporter) {
		super(bugReporter);
	}

	/**
	 * 每扫描一条字节码就会进入sawOpcode方法
	 * @param seen JVM指令的枚举值
	 */
	@Override
	public void sawOpcode(int seen) {
		//8: getstatic     #26  // Field java/lang/System.out:Ljava/io/PrintStream;
		if (GETSTATIC == seen) {
			if ("java/lang/System".equals(getClassConstantOperand())
					&& ("out".equals(getNameConstantOperand()) 
					 || "err".equals(getNameConstantOperand()))) {
				reportBug("CHECK_SYSTEMCLASS", NORMAL_PRIORITY);
			}
		}
		//25: invokevirtual #41  // Method java/lang/Exception.printStackTrace:()V
		else if (INVOKEVIRTUAL == seen) {
			//几乎所有的异常的类名都会带“Exception”字符串
			if (getClassConstantOperand().contains("Exception")
					&& ("printStackTrace".equals(getNameConstantOperand()) 
					 || "getMessage".equals(getNameConstantOperand()))) {
				reportBug("CHECK_SYSTEMCLASS", NORMAL_PRIORITY);
			}
		}
	}
}

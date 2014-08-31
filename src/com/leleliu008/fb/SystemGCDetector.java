package com.leleliu008.fb;

import edu.umd.cs.findbugs.BugReporter;

/**
 * 检测程序中使用了System.gc的地方，避免滥用或者不正确的使用，所以需要知道哪些地方使用System.gc()
 * 现在生产环境中避免工程师误用System.gc()，大部分企业级java应用都会把程序中System.gc()的调用禁止掉。
 * 禁用方法：调整jvm参数-XX:+DisableExplicitGC
 * 
 * @author leleliu008 2014-05-06
 *
 */
public class SystemGCDetector extends CustomDetector {

	public SystemGCDetector(BugReporter bugReporter) {
		super(bugReporter);
	}

	@Override
	public void sawOpcode(int seen) {
		//41: invokestatic  #51   // Method java/lang/System.gc:()V
		if (INVOKESTATIC == seen) {
			if ("java/lang/System".equals(getClassConstantOperand()) 
					&& "gc".equals(getNameConstantOperand())) {
				reportBug("SYSTEM_GC", NORMAL_PRIORITY);
			}
		}
	}
}

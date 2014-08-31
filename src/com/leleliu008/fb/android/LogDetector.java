package com.leleliu008.fb.android;

import com.leleliu008.fb.CustomDetector;

import edu.umd.cs.findbugs.BugReporter;

/**
 * 检测直接使用android.util.Log的地方，应该有开关，在release的时候是不会打开Log的开关的。
 * 
 * @author leleliu008 2014-05-06
 */
public class LogDetector extends CustomDetector {

	public LogDetector(BugReporter bugReporter) {
		super(bugReporter);
	}
	
	@Override
	public void sawOpcode(int seen) {
		// 4: invokestatic  #20  // Method android/util/Log.d:(Ljava/lang/String;Ljava/lang/String;)I
		if (INVOKESTATIC == seen) {
			if ("android/util/Log".equals(getClassConstantOperand())) {
				reportBug("ANDROID_LOG", NORMAL_PRIORITY);
			}
		}
	}
}

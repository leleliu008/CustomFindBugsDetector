package com.leleliu008.fb;

import org.apache.bcel.classfile.Code;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * base class of custom detector
 * 
 * @author leleliu008 2014-05-06
 */
public abstract class CustomDetector extends OpcodeStackDetector {

	private BugReporter bugReporter;

	public CustomDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	/**
	 * 在每次进入字节码方法的时候调用，在每次进入新方法的时候清空标志位
	 */
	@Override
	public void visit(Code code) {
		super.visit(code);
	}
	
	/**
	 * 
	 * @param type     类型，同findbugs.xml和messages.xml中的BugPattern元素的type属性值一样
	 * @param priority 优先级，其值为以下5个之一：{@link #IGNORE_PRIORITY}
	 *                                        {@link #EXP_PRIORITY}
	 *                                        {@link #LOW_PRIORITY}
	 *                                        {@link #NORMAL_PRIORITY}
	 *                                        {@link #HIGH_PRIORITY}
	 */
	protected final void reportBug(String type, int priority) {
		BugInstance bug = new BugInstance(this, type, priority);
		bug.addClassAndMethod(this);
		bug.addSourceLine(this, getPC());
		bug.addInt(getPC());
		
		bugReporter.reportBug(bug);
	}
}

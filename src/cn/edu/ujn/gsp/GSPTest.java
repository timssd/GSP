package cn.edu.ujn.gsp;

import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * GSP的测试类 使用JUnit测试
 * 
 * @author Sunny
 * 
 */
public class GSPTest extends TestCase {

	public void testGSP() {
		GSP gsp = new GSP(2);
		gsp.outputInput();
		ArrayList<Sequence> result = gsp.getSequences();
	}
}

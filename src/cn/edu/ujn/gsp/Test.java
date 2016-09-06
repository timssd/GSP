package cn.edu.ujn.gsp;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) throws IOException {
		GSP gsp = new GSP(2);
		gsp.outputInput();
		ArrayList<Sequence> result = gsp.getSequences();
	}
}

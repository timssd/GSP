package cn.edu.ujn.gsp;

import java.util.ArrayList;

/**
 * <title>数据库序列集类</title>
 * 用于获取从数据库中扫描后获得序列集
 * 这里并没有从数据库获取，而是做好的初始化
 * @author Sunny
 *
 */
public class SeqDB {
    private ArrayList<Sequence> seqs;  //序列对象
    
    /**
     * 无参数构造方法
     * 初始化序列集
     *
     */
    public SeqDB() {
        this.seqs = new ArrayList<Sequence>();
        Sequence s;
     
        //<{1 5}{2}{3}{4}>
        s = new Sequence();
        s.addElement(new Element(new int[] {1, 5}));
        s.addElement(new Element(new int[] {2}));
        s.addElement(new Element(new int[] {3}));
        s.addElement(new Element(new int[] {4}));
        seqs.add(s);
        //<{1}{3}{4}{3 5}>
        s = new Sequence();
        s.addElement(new Element(new int[] {1}));
        s.addElement(new Element(new int[] {3}));
        s.addElement(new Element(new int[] {4}));
        s.addElement(new Element(new int[] {3, 5}));
        seqs.add(s);
        //<{1}{2}{3}{4}>
        s = new Sequence();
        s.addElement(new Element(new int[] {1}));
        s.addElement(new Element(new int[] {2}));
        s.addElement(new Element(new int[] {3}));
        s.addElement(new Element(new int[] {4}));

        seqs.add(s);
        //<{1}{3}{5}>
        s = new Sequence();
        s.addElement(new Element(new int[] {1}));
        s.addElement(new Element(new int[] {3}));
        s.addElement(new Element(new int[] {5}));
        seqs.add(s);
        //<{4}{5}>
        s = new Sequence();
        s.addElement(new Element(new int[] {4}));
    
        s.addElement(new Element(new int[] {4,5}));
        seqs.add(s);
    }
    
    /**
     * 获取序列集的大小即获取有几个序列
     * @return序列集大小
     */
    public int size(){
        return this.seqs.size();
    }
    
    /**
     * 获取序列集
     * @return序列集
     */
    public ArrayList<Sequence> getSeqs(){
        return this.seqs;
    }
}

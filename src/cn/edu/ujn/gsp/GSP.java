package cn.edu.ujn.gsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <title>GSP算法实现类</title>
 * 本类为核心类，在本类中实现了GSP算法
 * @author Sunny
 *
 */
public class GSP {
    private ArrayList<Sequence> c; //长度为i的候选序列模式
    private ArrayList<Sequence> l; //长度为i的序列模式
    private ArrayList<Sequence> result;
    private SeqDB db;
    private int support;
    
   /**
    * 构造方法
    * 在实例化GSP对象时，同时赋值支持度
    * 并获取序列集和初始化序列模式结果
    * @param support 支持度
    */
    public GSP(int support) {
        this.support = support;                   //赋值支持度
        this.db = new SeqDB();                    //从SeqDB类中获取设置好的序列集
        this.result = new ArrayList<Sequence>();  //初始化序列模式结果对象
    }
    
    /**
     * 产生序列模式
     * 核心方法，在该方法中调用连接和剪枝操作，并将最后获得的序列模式放到result中
     * @return 序列模式
     */
    public ArrayList getSequences() {
        long start = System.currentTimeMillis();
        //调用初始化方法
        initialize();
        System.out.println("序列模式L(1) 为：" +l);
        System.out.println(".................................................");
        for (int i = 0; i < l.size(); i++) {
        	//产生进行连接操作后的候选集
        	genCandidate();      
            if (!(c.size() > 0)) {
                break;
            }         
            System.out.println("剪枝前候选集的大小为："+c.size()+" 候选集c为："+c);
            //进行剪枝操作
            pruneC();
            System.out.println("剪枝后候选集的大小为："+c.size()+" 候选集c为："+c);
            //产生序列模式
            generateL();
            System.out.println("序列模式L(" + (i + 2) + ") 为：" +l);
            addToResult(l);
            System.out.println(".................................................");
        }
        long end = System.currentTimeMillis();
        System.out.println("计算花费时间" + (end - start) + "毫秒!");
        return this.result;
    }
    /*
     * 初始化方法
     * 获取设置好的序列集
     */
    private void initialize() {
        Map<Integer, Integer> can = new HashMap<Integer, Integer>();
        //对于序列集中的所有序列
        for (Sequence s : db.getSeqs()) {
        	//对于序列中的所有项目集
            for (Element e : s.getElements()) {
            	//对于项目集中的所有项目
                for (int i : e.getItems()) {
                	//比较项目的出现次数，并计数，用于与支持度比较
                    if (can.containsKey(i)) {
                        int count = can.get(i).intValue() + 1;
                        can.put(i, count);
                    } else {
                        can.put(i, 1);
                    }
                }
            }
        }
        this.l = new ArrayList<Sequence>();
        //对于产生的候选集，如果支持度大于最小支持度阈值，则添加到序列模式L中
        for (int i : can.keySet()) {
            if (can.get(i).intValue() >= support) {
                Element e = new Element(new int[] {i});
                Sequence seq = new Sequence();
                seq.addElement(e);
                this.l.add(seq);
            }
        }
        //将第一次频繁序列模式加入结果集中
        this.addToResult(l);
       
    }
    
    /*
     * 产生经过连接操作后的候选集
     * 
     */
    private void genCandidate() {
        this.c = new ArrayList<Sequence>();
        //对于种子集L进行连接操作
        for (int i = 0; i < this.l.size(); i++) {
            for (int j = i; j < this.l.size(); j++) {
                this.joinAndInsert(l.get(i), l.get(j));
                if (i != j) {
                    this.joinAndInsert(l.get(j), l.get(i));
                }
            }
        }
    }

   /*
    * 对种子集进行连接操作
    */
    private void joinAndInsert(Sequence s1, Sequence s2) {
        Sequence s, st;
        //去除第一个元素
        Element ef = s1.getElement(0).getWithoutFistItem(); 
        //去除最后一个元素
        Element ee = s2.getElement(s2.size() - 1).getWithoutLastItem();
        int i = 0, j = 0;
        if (ef.size() == 0) {
            i++;
        }
        for (; i < s1.size() && j < s2.size(); i++, j++) {
            Element e1, e2;

            if (i == 0) {
                e1 = ef;
            } else {
                e1 = s1.getElement(i);
            }
            if (j == s2.size() - 1) {
                e2 = ee;
            } else {
                e2 = s2.getElement(j);
            }
            if (!e1.equalsTo(e2)) {
                return;
            }
        } //end of for

        s = new Sequence(s1);
        //将s2的最后一个元素添加到s中
        (s.getElement(s.size() - 1)).addItem(s2.getElement(s2.size() - 1).
                                            getLastItem());
        //如果候选集中没有s，则添加到候选集
        if (s.notInSeqs(c)) {
            c.add(s);
        }
        st = new Sequence(s1);
        //将s2的最后一个元素添加到st中
        st.addElement(new Element(new int[] {s2.getElement(s2.size() - 1).
                                  getLastItem()}));
        //如果候选集中没有st，则添加到候选集
        if (st.notInSeqs(c)) {
            c.add(st);
        }
        return;
    }

    /*
     * 剪枝操作
     * 看每个候选序列的连续子序列是不是频繁序列
     * 采用逐个取元素，只去其中一个项目，然后看是不是有相应的频繁序列在l中。
     * 如果元素只有一个项目，则去除该元素做相应判断。
     */
    private void pruneC() {
        Sequence s;
        //对于序列中的所有元素
        for (int i = 0; i < this.c.size();i++) {
            s = c.get(i);
            //对于元素中的所有项目
            for (int j = 0; j < s.size(); j++) {
                Element ce = s.getElement(j);
                boolean prune=false;
                //只有一个元素的情况
                if (ce.size() == 1) {
                    s.removeElement(j);
                    //如果子序列不是序列模式，则将它从候选序列模式中删除，否则添加
                    if (s.notInSeqs(this.l)) {
                        prune=true;
                    }
                    s.insertElement(j, ce);
                } else {
                    for (int k = 0; k < ce.size(); k++) {
                        int item=ce.removeItem(k);
                        //如果子序列不是序列模式，则将它从候选序列模式中删除。否则添加
                        if (s.notInSeqs(this.l)) {
                            prune=true;
                        }
                        ce.addItem(item);
                    }
                }
                //如果剪枝，则将该序列删除
                if(prune){
                    c.remove(i);
                    i--;
                    break;
                }
            }
        } 
    }
    
    /*
     * 生成序列模式L
     * 用于已经经过连接和剪枝操作后的后选序列集
     */
    private void generateL() {
        this.l = new ArrayList<Sequence>();
        for (Sequence s : db.getSeqs()) {
            for (Sequence seq : this.c) {
                if (seq.isSubsequenceOf(s)) {
                	//支持度计数
                    seq.incrementSupport();
                }
            }
        }
        for (Sequence seq : this.c) {
        	//大于最小支持度阈值的放到序列模式中
            if (seq.getSupport() >= this.support) {
                this.l.add(seq);
            }
        }
    }
    
    /*
     * 将该频繁序列模式加入结果中
     */
    private void addToResult(ArrayList<Sequence> l) { 
        for (int i = 0; i < l.size(); i++) {
            this.result.add(l.get(i));
        }
    }
    
    /**
     * 输出输入的序列信息
     * 输出需要进行序列模式分析的序列以及最小支持度（计数）
     */
   public void outputInput() {
	   System.out.println("最小支持度计数为：" + this.support);
	   System.out.println("输入的序列集合为：");
	   System.out.println(db.getSeqs());
	   System.out.println();
   }
}

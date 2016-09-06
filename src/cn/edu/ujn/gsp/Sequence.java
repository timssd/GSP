package cn.edu.ujn.gsp;

import java.util.ArrayList;

/**
 * <title>序列类</title>
 * 序列信息和操作类，在本类中进行序列比较、支持度计数
 * 以及操作该序列的元素（项目集）
 * @author Sunny
 *
 */
public class Sequence {
    private int support; //该序列在数据库中的支持计数
    private ArrayList<Element> sequence; //存放元素序列
    
    /**
     * 不带参数的构造方法
     * 实例化Sequence对象时，同时初始化该对象的支持计数和sequence属性
     *
     */
    public Sequence() {
        this.sequence = new ArrayList<Element>();
        this.support = 0;
    }
    
    /**
     * 带参数的构造方法
     * 拷贝参数序列对象s中的所有元素到本对象的sequence属性中
     * 并初始化支持计数
     * @param s Sequence对象
     */
    public Sequence(Sequence s) {
        this.sequence = new ArrayList<Element>();
        this.support = 0;
        //拷贝s中的所有元素
        for (int i = 0; i < s.size(); i++) {
            this.sequence.add(s.getElement(i).clone());
        }
    }
    
    /**
     * 添加新的元素
     * 用于向序列中添加新的元素
     * @param e  Element e -- 被添加的元素
     */
    public void addElement(Element e) {
        this.sequence.add(e);
    }
    
    /**
     * 插入元素
     * 向序列中位置index插入新的元素
     * @param index 需要插入的元素位置
     * @param e Element e -- 被插入的元素
     */
    public void insertElement(int index,Element e){
        this.sequence.add(index,e);
    }
    
    /**
     * 删除元素
     * 删除位置index上的元素
     * @param index 位置序号
     * @return 返回删除后的sequence
     */
    public Element removeElement(int index){
        if(index<this.sequence.size()){
            return this.sequence.remove(index);
        }else 
            return null;
    }
    
    /**
     * 获取序列的元素
     * 获取第index个元素
     * @param index 元素在序列中的位置
     * @return 返回该元素
     */
    public Element getElement(int index) {
        if (index >= 0 && index < this.sequence.size()) {
            return this.sequence.get(index);
        } else {
            System.err.println("index outof bound in Seuqence.getElement()");
            return null;
        }
    }
    
    /**
     * 获取所有元素
     * 返回序列对象的sequence属性，也就是所有元素的集合
     * @return  ArrayList -- 所有元素放到ArrayList中
     */
    public ArrayList<Element> getElements() {
        return this.sequence;
    }
    
    /**
     * 获取序列大小
     * @return 序列大小
     */
    public int size() {
        return this.sequence.size();
    }

   /**
    * 比较序列间的元素
    * 将传递的参数序列对象与本序列对象比较
    * 看是否有相同的元素
    * @param seqs 被比较的序列
    * @return true--存在相同元素 false--不存在相同元素
    */ 
    public boolean notInSeqs(ArrayList<Sequence> seqs) {
        Sequence s;
        for (int i=0;i<seqs.size();i++) {
            s=seqs.get(i);
            //调用方法isSubsequenceOf()比较
            if (this.isSubsequenceOf(s) && s.isSubsequenceOf(this)){
                return false;
            }

        }
        return true;
    }
    
    /*
     * 比较序列中是否含有相同的元素
     */
    public boolean isSubsequenceOf(Sequence s) {
        int i = 0, j = 0;
        while (j < s.size() && i < this.sequence.size()) {
            if (this.getElement(i).isContainIn(s.getElement(j))) {
                i++;
                j++;
                if (i == this.sequence.size()) {
                    return true;
                }

            } else {
                j++;
            }
        }
        return false;
    }
    
    /**
     * 增加支持计数
     *
     */
    public void incrementSupport() {
        this.support++;
    }
    
    /**
     * 获取支持计数
     * @return
     */
    public int getSupport() {
        return this.support;
    }
    
    /**
     * 重写toString()
     * 用于输出时的字符处理
     * 
     */
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("<");
        for (int i = 0; i < this.sequence.size(); i++) {
            s.append(this.sequence.get(i));
            if (i != this.sequence.size() - 1) {
                s.append(" ");
            }
        }
        s.append(">");
        return s.toString();
    }
}

package cn.edu.ujn.gsp;

import java.util.ArrayList;

/**
 * <title>元素（项目集）类</title>
 * 元素信息和操作类，将项目集作为该实例的属性
 * 本类封装了对于项目集的基本操作
 * @author Sunny
 *
 */
public class Element {
    private ArrayList<Integer> itemset;//表示该元素的项目，按数字的升序存放
    
    /**
     * 无参数构造方法
     * 初始化项目集
     *
     */
    public Element() {
        this.itemset=new ArrayList<Integer>();
    }
    
    /**
     * 带参数构造方法
     * 初始化项目集，即将参数中的项目集拷贝过来
     * @param items 项目集
     */
    public Element(int [] items){
        this.itemset=new ArrayList<Integer>();
        for(int i=0;i<items.length;i++){
            this.addItem(items[i]);
        }
    }
    
    /**
     * 添加项目
     * 添加项目到项目集中
     * @param item 被添加的项目
     */
    public void addItem(int item){
        int i;
        for(i=0;i<itemset.size();i++){
            if(item<itemset.get(i)){
                break;
            }
        }
        itemset.add(i,item);
    }
    
    /**
     * 获得项目集
     * @return 项目集
     */
    public ArrayList<Integer> getItems(){
        return this.itemset;
    }
    
    /**
     * 获取最后一个位置的项目
     * @return 项目
     */
    public int getLastItem(){
        if(this.itemset.size()>0){
            return itemset.get(itemset.size() - 1);
        }
        else{
            System.err.println("空元素错误，Element.getLastItem()");
            return 0;
        }
    }

    /**
     * 本方法判断本元素是不是包含于元素e中
     * @param e 元素
     * @return true--是 false--否
     */
    public boolean isContainIn(Element e){

        if(this.itemset.size()>e.itemset.size()){//如果两个元素大小不同，则为不相等
            return false;
        }
        int i=0,j=0;
        while(j<e.size() && i<this.itemset.size() ){
            if(this.itemset.get(i).intValue() == e.itemset.get(j).intValue()){
                i++;j++;
            }else{
                j++;			
            }
        }
        if(i==this.itemset.size()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 获取去除第一个项目外的元素
     * @return 元素
     */
    public Element getWithoutFistItem(){
        Element e=new Element();
        for(int i=1 ;i<this.itemset.size();i++){
            e.addItem(this.itemset.get(i).intValue());
        }
        return e;
    }
    
    /**
     * 获取去除最后一个项目外的元素
     * @return 元素
     */
    public Element getWithoutLastItem(){
        Element e=new Element();
        for(int i=0 ;i<this.itemset.size()-1;i++){
            e.addItem(this.itemset.get(i).intValue());
        }
        return e;
    }
    
    /**
     * 删除项目
     * 项目位置i上的项目
     * @param i 位置序号
     * @return         
     */
    public int removeItem(int i){
        if(i<this.itemset.size()){
           return this.itemset.remove(i).intValue();
        }
        System.err.println("无效的索引！");
        return -1;
    }
    
    /**
     * 比较两个元素的大小
     * 将传递过来的参数o与本对象比较
     * @param o 被比较的元素
     * @return int -- -1 本元素小于参数  1 本元素大于参速
     */
     public int compareTo(Object o){
         Element e=(Element)o;
         int r=0;
         int i=0,j=0;
         while(i<this.itemset.size() && j<e.itemset.size()){
            if(this.itemset.get(i).intValue() < e.itemset.get(j).intValue()){
                r=-1;//本element小于e
                break;
            }else{
                if(this.itemset.get(i).intValue() > e.itemset.get(j).intValue()){
                    r=1;//本element大于e
                    break;
                }
            }
            i++;j++;//项目相同，都指向下一个项目
         }
         if(r==0){//如果目前还没有比较出谁大谁小的话
             if(this.itemset.size()>e.itemset.size()){
                 r=1;
             }
             if(this.itemset.size()<e.itemset.size()){
                 r=-1;
             }
         }
         return r;
    }
     
    /**
     * 获取项目集的大小
     * @return int--大小
     */
    public int size(){
        return this.itemset.size();
    }
    
    /**
     * 元素拷贝方法
     * 拷贝项目集
     */
    public Element clone(){
        Element clone=new Element();
        for(int i:this.itemset){
            clone.addItem(i);
        }
        return clone;
    }
    
    /**
     * 下判断两个元素是否相同
     * @param o           
     * @return  true--相同 false--不同
     */
    public boolean equalsTo(Object o){
       boolean equal=true;
       Element e=(Element)o;
       if(this.itemset.size()!=e.itemset.size()){//如果两个元素大小不同，则为不相等
           equal=false;
       }
       for(int i=0;equal && i<this.itemset.size();i++){
           if(this.itemset.get(i).intValue()!=e.itemset.get(i).intValue()){
               equal=false;
           }
       }
       return equal;
   }

    /**
     * 重写toString()
     * 用于输出时的字符处理
     */
    public String toString(){
        StringBuffer s=new StringBuffer();
        if(this.itemset.size()>1){
            s.append("(");
        }
        for(int i=0;i<this.itemset.size();i++){
            s.append(this.itemset.get(i).intValue());
            if(i<this.itemset.size()-1){
                s.append(",");
            }
        }
        if(this.itemset.size()>1){
            s.append(")");
        }
        return s.toString();
    }
}

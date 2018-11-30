package hk.hku.cs.myapplication.entity;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.entity
 *  文件名：    ChatListData
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 12:47
 *  描述：      TODO
 */public class ChatListData {
    //对话的类型
    private int type;
    //对话的文本
    private String text;

    public int getType(){
        return type;
    }
    public String getText(){
        return text;
    }
    public void setType(int type){
        this.type = type;
    }
    public void setText(String text){
        this.text = text;
    }
}

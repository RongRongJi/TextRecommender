package vo;

public class StockInfo {
    public String[] data;
    static public int stockLength;//StockInfo实例化总数


    public StockInfo() {
        data = new String[8];
        for(String d : data) {
            d=null;
        }

    }// end StockInfo constructor

    public int answerLength() {
        return data[7].length();
    }//end answerLength

}

package tf_idf;

import javafx.util.Pair;
import util.StockSorter;
import vo.StockInfo;

import java.util.List;

public interface TF_IDF {

    /**
     * 根据给定词汇数组，算出各个词汇tf-idf词频，按照词频从高到底进行排序，然后生成Pair类型数组
     * @param words the word after segment
     * @param stockInfos data
     * @return a sorted words,String is the word, and Integer is the frequency ,like : fin,123
     * @see StockSorter,Pair
     */
    Pair<String, Double>[] getResult(List<String> words, StockInfo[] stockInfos);
}

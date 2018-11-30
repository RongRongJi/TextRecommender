package tf_idf;

import javafx.util.Pair;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import util.StockSorter;
import util.StockSorterImpl;
import vo.StockInfo;

import java.util.*;

public class TF_IDFImpl implements TF_IDF {
    /**
     * this func you need to calculate words frequency , and sort by frequency.
     * you maybe need to use the sorter written by yourself in example 1
     *
     * @param words the word after segment
     * @return a sorted words
     * @see StockSorter
     */


    @Override
    public Pair<String, Double>[] getResult(List<String> words, StockInfo[] stockInfos) {
        //TODO: write your code here
        final int size = words.size();
        Hashtable hash = new Hashtable();
        String str = null;
        int len = 0;
        int num = 0;
        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            str = it.next();
            if (hash.get(str) == null){
                len ++;
                hash.put(str,1);
            }else {
                num = (int) hash.get(str) + 1;
                hash.put(str,num);
            }
        }
        Pair<String,Double>[] pair = new Pair[len];
        int m = 0;
        String key = null;
        for(Iterator<String> iterator = hash.keySet().iterator(); iterator.hasNext();){
            key = iterator.next();
            double tfidf = tf(words,key) * idf(stockInfos,key);
            pair[m] = new Pair<>(key,tfidf);
            m ++;
        }
        //排序
        for (int i = 0; i < pair.length; i++) {
            for(int j = 0; j<pair.length-i-1; j++){
                if(pair[j].getValue()<pair[j+1].getValue()){
                    Pair<String, Double> temp = pair[j];
                    pair[j] = pair[j+1];
                    pair[j+1] = temp;
                }
            }
        }//结束排序

        return pair;
    }

    public double tf(List<String> words,String check){
        double count = 0;
        double sum = 0;
        for(String w : words){
            sum++;
            if(w.equalsIgnoreCase(check)){
                count++;
            }
        }
        return count/sum;
    }

    public double idf(StockInfo[] stockInfos, String check){
        double count = 0;
        List<String> list = new ArrayList<String>();
        for(int j=1;j<StockInfo.stockLength-1;j++) {
            String save = stockInfos[j].data[7];
            Result result = NlpAnalysis.parse(save);
            List<Term> terms = result.getTerms();
            for (int i=0;i<terms.size();i++) {
                list.add(terms.get(i).getName());
            }
            for(String s: list){
                if(s.equalsIgnoreCase(check)){
                    count++;
                    break;
                }
            }
            list.clear();
        }
        return 1+Math.log(StockInfo.stockLength/count);
    }

    public double idf2(StockInfo[] stockInfos, String check){
        double count = 0;
        List<String> list = new ArrayList<String>();
        for(int j=1;j<StockInfo.stockLength-1;j++) {
            String save = stockInfos[j].data[5];
            Result result = NlpAnalysis.parse(save);
            List<Term> terms = result.getTerms();
            for (int i=0;i<terms.size();i++) {
                list.add(terms.get(i).getName());
            }
            for(String s: list){
                if(s.equalsIgnoreCase(check)){
                    count++;
                    break;
                }
            }
            list.clear();
        }
        return 1+Math.log(StockInfo.stockLength/count);
    }

    public Pair<String, Double>[] getResult2(List<String> words, StockInfo[] stockInfos) {
        //TODO: write your code here
        final int size = words.size();
        Hashtable hash = new Hashtable();
        String str = null;
        int len = 0;
        int num = 0;
        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            str = it.next();
            if (hash.get(str) == null){
                len ++;
                hash.put(str,1);
            }else {
                num = (int) hash.get(str) + 1;
                hash.put(str,num);
            }
        }
        Pair<String,Double>[] pair = new Pair[len];
        int m = 0;
        String key = null;
        for(Iterator<String> iterator = hash.keySet().iterator(); iterator.hasNext();){
            key = iterator.next();
            double tfidf = (double)(int)hash.get(key);
            pair[m] = new Pair<>(key,tfidf);
            m ++;
        }
        //排序
        for (int i = 0; i < pair.length; i++) {
            for(int j = 0; j<pair.length-i-1; j++){
                if(pair[j].getValue()<pair[j+1].getValue()){
                    Pair<String, Double> temp = pair[j];
                    pair[j] = pair[j+1];
                    pair[j+1] = temp;
                }
            }
        }//结束排序

        return pair;
    }

}

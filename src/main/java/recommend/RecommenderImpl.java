package recommend;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import segmenter.ChineseSegmenterImpl;
import tf_idf.TF_IDF;
import tf_idf.TF_IDFImpl;
import util.StockSorterImpl;
import vo.StockInfo;
import vo.UserInterest;
import javafx.util.Pair;
import java.util.*;

public class RecommenderImpl implements Recommender {

    /**
     * this function need to calculate stocks' content similarity,and return the similarity matrix
     *
     * @param stocks stock info
     * @return similarity matrix
     */

    @Override
    public double[][] calculateMatrix(StockInfo[] stocks) {
        //TODO: write your code here
        final int length = 60;
        List<String> a[] = new ArrayList[StockInfo.stockLength];
        TF_IDFImpl tf_idf = new TF_IDFImpl();
        List<String> Set = new ArrayList<String>();
        HashSet<String> hashSet = new HashSet<>();
        //1.对content进行分词，并使用TF-IDF算法找出关键词
        ChineseSegmenterImpl c = new ChineseSegmenterImpl();
        for(int i=0;i<60;i++){
            a[i] = new ArrayList<String>();
            a[i] = c.getWordsFromString(stocks[i+1].data[5]);
            //排序
            Pair<String,Double>[] maps = tf_idf.getResult2(a[i],stocks);
            //结束排序
            //每个content各取出10个关键词
            for(int j=0;j<10;j++){
                if(j>=maps.length) break;
                if (!hashSet.contains(maps[j].getKey())) {
                    Set.add(maps[j].getKey());
                    hashSet.add(maps[j].getKey());
                }
            }
        }
        int frequency[][] = new int[length][Set.size()];//词频


        //计算每个content关于他的词频
        for(int i=0;i<length;i++){
            for(int j=0;j<Set.size();j++){
                int isBelong = 0;
                for (int k = 0; k < a[i].size();k++){
                    if (a[i].get(k) == Set.get(j))
                        isBelong++;
                }
                frequency[i][j]=isBelong;
            }
        }
        for (int i = 0 ; i  < length; i ++){
            for (int j = 0; j < length; j ++){
                System.out.print(frequency[i][j] + " ");
            }
            System.out.print("\n");
        }

        //计算相似度
        double[][] cosine = new double[length][length];
        for(int i=0;i<length;i++){
            for(int j= i + 1;j<length;j++){

                //cosine
                double sum=0,x=0,y=0;
                for(int k=0;k<Set.size();k++){
                    sum = sum + frequency[i][k] * frequency[j][k];
                    x = x + frequency[i][k] * frequency[i][k];
                    y = y + frequency[j][k] * frequency[j][k];
                }
                cosine[i][j] = cosine[j][i] = sum/(Math.sqrt(x)*Math.sqrt(y));
            }
        }
        for (int i = 0 ; i  < cosine.length; i ++){
            for (int j = 0; j < cosine.length; j ++){
                System.out.print(cosine[i][j] + " ");
            }
            System.out.print("\n");
        }
        return cosine;



    }

    /**
     * this function need to recommend the most possibility stock number
     *
     * @param matrix       similarity matrix
     * @param userInterest user interest
     * @return commend stock number
     */
    @Override
    public double[][] recommend(double[][] matrix, UserInterest[] userInterest) {
        //TODO: write your code here
        double[][] temp = new double[500][60];
        double sum;
        for(int i=0;i<userInterest.length;i++){
            for(int j=0;j<60;j++){
                if(userInterest[i].like[j]>0.1){
                    temp[i][j]=-1;
                }
                else{
                    sum=0;
                    for(int k=0;k<60;k++){
                        sum+= matrix[j][k]*userInterest[i].like[k];
                    }
                    temp[i][j]=sum;
                }
            }
        }
        //排序
        double [][] info = new double[500][60];
        for (int i = 0; i < 500 ; i ++)
            for (int j = 0; j < 60; j ++)
                info[i][j] = j;
        for (int ii = 0; ii < 500 ; ii ++) {
            for (int i = 0; i < temp[ii].length; i++) {
                for (int j = 0; j < temp[ii].length - i - 1; j++) {
                    //这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
                    if (temp[ii][j] < temp[ii][j + 1]) {
                        double temp1 = temp[ii][j];
                        temp[ii][j] = temp[ii][j + 1];
                        temp[ii][j + 1] = temp1;
                        double temp2 = info[ii][j];
                        info[ii][j] = info[ii][j + 1];
                        info[ii][j + 1] = temp2;
                    }
                }
            }
        }
        return info;
    }


    /**
     * 此函数根据用户打分矩阵，输出给用户推荐的头条新闻
     * @param commend stock number
     * @param userInterest user intereset
     * @return headline news
     */
    @Override
    public int[] headline(double[][] commend,UserInterest[] userInterest){
        int news[] = new int[500];
        double score = 0.0;
        for(int i=0;i<commend.length;i++){
            score=0.0;
            for(int j=0;j<commend[i].length;j++){
                if(commend[i][j]>score && userInterest[i].like[j]==0.0){
                    score=commend[i][j];
                    news[i]=j;
                }
            }
        }
        return news;
    }
}

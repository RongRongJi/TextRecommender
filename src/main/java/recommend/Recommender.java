package recommend;

import vo.StockInfo;
import vo.UserInterest;

public interface Recommender {

    /**
     * 该函数用于计算相似度矩阵，返回一个记录相似度二位数组（矩阵）
     * @param stocks stock info
     * @return similarity matrix
     */
    double[][] calculateMatrix(StockInfo[] stocks);

    /**
     * 此函数根据输入的用户兴趣信息，输入的新闻相似度矩阵，计算出推荐二维数组，每个用户推荐兴趣相似度前10名的相关新闻
     * @param matrix similarity matrix
     * @param userInterest user interest
     * @return commend stock number
     */
    double[][] recommend(double[][] matrix, UserInterest[] userInterest);


    /**
     * 此函数根据用户打分矩阵，输出给用户推荐的头条新闻
     * @param commend stock number
     * @param userInterest user intereset
     * @return headline news
     */
    int[] headline(double[][] commend,UserInterest[] userInterest);
}

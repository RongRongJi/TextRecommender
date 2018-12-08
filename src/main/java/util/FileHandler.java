package util;

import vo.StockInfo;
import vo.UserInterest;

public interface FileHandler {

    /**
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    StockInfo[] getStockInfoFromFile(String filePath);

    /**
     * @param filePath
     * @return
     */
    UserInterest[] getUserInterestFromFile(String filePath);

    /**
     * @param matrix the matrix you calculate
     */
    void setCloseMatrix2File(double[][] matrix);

    /**
     * @param recommend the recommend you calculate
     */
    void setRecommend2File(double[][] recommend);



}

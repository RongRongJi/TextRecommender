package util;

import vo.StockInfo;
import vo.UserInterest;

public interface FileHandler {

    /**
     * This func gets stock information from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    StockInfo[] getStockInfoFromFile(String filePath);

    /**
     * This func gets user interesting from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     * @param filePath
     * @return
     */
    UserInterest[] getUserInterestFromFile(String filePath);

    /**
     * This function need write matrix to files
     * @param matrix the matrix you calculate
     */
    void setCloseMatrix2File(double[][] matrix);

    /**
     * This function need write recommend to files
     * @param recommend the recommend you calculate
     */
    void setRecommend2File(double[][] recommend);



}

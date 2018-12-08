package util;

import vo.StockInfo;


public interface StockSorter {

    /**
     * @param info stock information
     * @return sorted stock
     */
    StockInfo[] sort(StockInfo[] info);

    /**
     * @param info stock information
     * @param order true:ascending,false:descending
     * @return sorted stock
     */
    StockInfo[] sort(StockInfo[] info, boolean order);

}

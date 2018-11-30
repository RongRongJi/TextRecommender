package util;

import vo.StockInfo;

public class StockSorterImpl implements StockSorter {
    /**
     * Accepting series of stock info, sorting them ascending according to their comment length.
     * List.sort() or Arrays.sort() are not allowed.
     * You have to write your own algorithms,Bubble sort、quick sort、merge sort、select sort,etc.
     *
     * @param info stock information
     * @return sorted stock
     */
    final int answer = 7;
    @Override
    public StockInfo[] sort(StockInfo[] info) {
        //TODO: write your code here
        int low = 0;
        int high= StockInfo.stockLength-1;
        String tmp;
        int j;


        while (low < high) {
            for (j= low; j< high; ++j)
                if (info[j].answerLength()> info[j+1].answerLength()) {
                    for(int k=0;k<8;k++) {
                        tmp = info[j].data[k];
                        info[j].data[k]=info[j+1].data[k];
                        info[j+1].data[k]=tmp;
                    }//end for
                } //end if & for
            --high;
            for ( j=high; j>low; --j)
                if (info[j].answerLength()< info[j-1].answerLength()) {
                    for(int k=1;k<8;k++) {
                        tmp = info[j].data[k];
                        info[j].data[k]=info[j-1].data[k];
                        info[j-1].data[k]=tmp;
                    }//end for
                }  // end if & for
            ++low;
        }   //end while

        return info;
    }

    /**
     * Accepting series of stock info, sorting them ascending, descending order.
     *
     * @param info  stock information
     * @param order true:ascending,false:descending
     * @return sorted stock
     */
    @Override
    public StockInfo[] sort(StockInfo[] info, boolean order) {
        //TODO: write your code here
        if(order) {
            sort(info);
            return info;
        }//end if
        else {
            int low = 0;
            int high= StockInfo.stockLength-1;
            String tmp;
            int j;
            while (low < high) {
                for (j= low; j< high; ++j)
                    if (info[j].answerLength()< info[j+1].answerLength()) {
                        for(int k=0;k<8;k++) {
                            tmp = info[j].data[k];
                            info[j].data[k]=info[j+1].data[k];
                            info[j+1].data[k]=tmp;
                        }//end for
                    } //end if & for
                --high;
                for ( j=high; j>low; --j)
                    if (info[j].answerLength()> info[j-1].answerLength()) {
                        for(int k=0;k<8;k++) {
                            tmp = info[j].data[k];
                            info[j].data[k]=info[j-1].data[k];
                            info[j-1].data[k]=tmp;
                        }//end for
                    }  //end if & for
                ++low;
            }   //end while
            return info;
        }//end else
    }//end sort
}

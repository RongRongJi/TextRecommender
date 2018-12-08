package segmenter;

import vo.StockInfo;

import java.util.List;

public interface ChineseSegmenter {

    /**
     * @param stocks stocks info
     * @return chinese word
     * @see ChineseSegmenterImpl
     */
    List<String> getWordsFromInput(StockInfo[] stocks);

}

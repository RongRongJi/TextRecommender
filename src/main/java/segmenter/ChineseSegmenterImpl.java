package segmenter;


import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import vo.StockInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChineseSegmenterImpl implements ChineseSegmenter {

    /**
     * this func will get chinese word from a list of stocks. You need analysis stocks' answer and get answer word.
     * And implement this interface in the class : ChineseSegmenterImpl
     * Example: 我今天特别开心 result : 我 今天 特别 开心
     *
     * @param stocks stocks info
     * @return chinese word
     * @see ChineseSegmenterImpl
     */
    @Override
    public List<String> getWordsFromInput(StockInfo[] stocks) {
        //TODO: write your code here
        List<String> list = new ArrayList<String>();
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
        String save = "";

        for(int i=1;i<StockInfo.stockLength-1;i++){
            save = stocks[i].data[7];
            Result result = NlpAnalysis.parse(save);
            List<Term> terms = result.getTerms();
            for(int j=0 ;j<terms.size();j++){
                String word = terms.get(j).getName();
                String natureStr = terms.get(j).getNatureStr();
                if(expectedNature.contains(natureStr)){
                    list.add(word);
                }
            }
        }


        return list;
    }

    public List<String> getWordsFromString(String str) {
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
        Result result = ToAnalysis.parse(str); //分词结果的一个封装，主要是一个List<Term>的terms
        List<Term> terms = result.getTerms();
        List<String> words = new ArrayList<>();
        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
                words.add(word);
            }
        }
        //System.out.println("here " + words.size());
        return words;
    }
}

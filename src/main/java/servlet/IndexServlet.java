package servlet;

import javafx.util.Pair;
import recommend.Recommender;
import recommend.RecommenderImpl;
import segmenter.ChineseSegmenter;
import segmenter.ChineseSegmenterImpl;
import tf_idf.TF_IDF;
import tf_idf.TF_IDFImpl;
import util.FileHandler;
import util.FileHandlerImpl;
import vo.StockInfo;
import vo.UserInterest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    private ChineseSegmenter segmenter;

    private TF_IDF tf_idf;

    private FileHandler fileHandler;

    private Recommender recommender;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 数据处理
        StockInfo[] stockInfos = fileHandler.getStockInfoFromFile(this.getClass().getClassLoader().getResource(".").getPath() + "data.txt");
        // 分词
        List<String> words = segmenter.getWordsFromInput(stockInfos);
        // 词频统计
        Pair<String,Double>[] maps = tf_idf.getResult(words,stockInfos);

        String[] arrays = new String[maps.length];
        double inputMin = maps[maps.length - 1].getValue(), inputMax = maps[0].getValue(), outputMin = 10, outputMax = 120;
        int i = 0;

        for (Pair<String,Double> s : maps) {
            //生成词云关键字
            StringBuilder sb = new StringBuilder();
            sb.append("{\"text\":\"").append(s.getKey())
                    .append("\",\"size\":")
                    .append(linerScale(inputMin,inputMax,outputMin,outputMax,s.getValue()))
                    .append("}");
            arrays[i++] = sb.toString();
        }

        //矩阵
        double[][] matrix, recommend;
        //获得用户兴趣数据
        UserInterest[] userInterests = fileHandler.getUserInterestFromFile(this.getClass().getClassLoader().getResource(".").getPath() + "interest.txt");
        //分析股票相似度
        matrix = recommender.calculateMatrix(stockInfos);
        //根据相似度矩阵推荐用户
        recommend = recommender.recommend(matrix,userInterests);
        //写入矩阵文件
        fileHandler.setCloseMatrix2File(matrix);
        //写入推荐文件
        fileHandler.setRecommend2File(recommend);
        //根据打分矩阵推荐头条
        int[] news = recommender.headline(recommend,userInterests);

        req.setAttribute("words", Arrays.toString(arrays));
        req.setAttribute("matrix",matrix);
        req.setAttribute("recommend",recommend);
        req.setAttribute("news",news);

        //重定向
        req.getRequestDispatcher("/WEB-INF/pic/picture.jsp").forward(req,resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        segmenter = new ChineseSegmenterImpl();
        tf_idf = new TF_IDFImpl();
        fileHandler = new FileHandlerImpl();
        recommender = new RecommenderImpl();
    }

    /**
     * 线性变化
     * @param inputMin 输入最小值
     * @param inputMax 输入最大值
     * @param outputMin 输出最小值
     * @param outputMax 输出最大值
     * @param item 待变化项
     * @return 变化后
     */
    private int linerScale(double inputMin,double inputMax,double outputMin,double outputMax,double item){
        double var1 = (outputMax - outputMin) / (inputMax - inputMin);
        double var2 = outputMax - var1 * inputMax;
        return (int) (var1*item + var2);
    }
}

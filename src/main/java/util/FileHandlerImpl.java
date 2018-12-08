package util;

import vo.StockInfo;
import vo.UserInterest;
import java.io.*;
import java.util.ArrayList;

public class FileHandlerImpl implements FileHandler {

    /**
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    @Override
    public StockInfo[] getStockInfoFromFile(String filePath) {
        StockInfo[] p = new StockInfo[600];
        //ArrayList<StockInfo[]> p1 = new ArrayList<>();
        try {

            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(filePath)),"UTF-8");
            BufferedReader br = new BufferedReader(read);
            String line = null;
            String str = "";
            int num=0;
            while((line=br.readLine())!=null) {
                p[num] = new StockInfo();
                str=line;
                p[num].data = str.split("\t");

                num++;

            }//end while
            StockInfo.stockLength = num;
            br.close();
        } catch (IOException e) {
            System.out.print("Exception");
        }
        return p;
    }

    /**
     * @param filePath
     * @return
     */
    @Override
    public UserInterest[] getUserInterestFromFile(String filePath) {
        try {
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(filePath)),"UTF-8");
            BufferedReader br = new BufferedReader(read);
            int line=0;
            String str = "";
            int num=0;
            while((str=br.readLine())!=null){
                line++;
            }
            UserInterest[] ps = new UserInterest[line];
            InputStreamReader read2 = new InputStreamReader(
                    new FileInputStream(new File(filePath)),"UTF-8");
            br = new BufferedReader(read2);
            double[] interest = new double[60];
            while((str=br.readLine())!=null){
                interest = new double[60];
                char[] user=str.toCharArray();
                for(int i=0;i<60;i++){
                    interest[i]=Double.valueOf((String.valueOf(user[i])));
                }
                UserInterest p = new UserInterest(interest);
                ps[num]=p;
                num++;

            }//end while
            br.close();
            return ps;
        } catch (IOException e) {
            System.out.print("Exception");
        }
    return null;
    }

    /**
     * @param matrix the matrix you calculate
     */
    @Override
    public void setCloseMatrix2File(double[][] matrix) {
        //TODO: write your code here
        try{
            File file = new File(this.getClass().getClassLoader().getResource(".").getPath() + "matrix.txt");
            FileWriter writer  = new FileWriter(file);
            for(int i=0;i<60;i++){
                for(int j=0;j<60;j++){
                    writer.write(matrix[i][j]+"\t");
                }
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e) {
            System.out.print("Exception");
        }

    }

    /**
     * @param recommend the recommend you calculate
     */
    @Override
    public void setRecommend2File(double[][] recommend) {
        //TODO: write your code here
            try{
            OutputStreamWriter out =null;
            FileOutputStream file = new FileOutputStream(this.getClass().getClassLoader().getResource(".").getPath() + "recommend.txt");
            out = new OutputStreamWriter(file,"UTF-8");
            for(int i=0;i<500;i++){
                for(int j=0;j<60;j++)
                    out.write((recommend[i][j])+"\t");
                out.write("\n");
            }
            out.close();
        }catch (IOException e) {
            System.out.print("Exception");
        }

    }
}

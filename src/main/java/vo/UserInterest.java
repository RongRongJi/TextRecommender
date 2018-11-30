package vo;

public class UserInterest {

    public double like[] = new double[60];

    public UserInterest(){
        for(double d : like){
            d=0.0;
        }
    }

    public UserInterest(double[] alike){
        like=alike;
    }
}


public class Word {
    
    private String word;
    private int count;
    private int accumulatedScore;
    
    public Word(String word, int score){
        this.word = word;
        this.count = 1;
        this.accumulatedScore = score;
    }
    
    public void incrementScore(int score){
        this.accumulatedScore += score;
    }
    
    public void incrementCount(){
        this.count++;
    }
    
    public String getWord(){
        return this.word;
    }
    
    public double getAverageScore(){
        return (double)this.accumulatedScore/this.count;
    }
    
    public int getCount(){
        return this.count;
    }
}

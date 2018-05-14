
public class Sentence {
    
    private int score;
    private String text;
    
    public Sentence(int score, String text){
        this.score = score;
        this.text = text;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public String getText(){
        return this.text;
    }
    
    public String toString(){
        return this.text;
    }
    
}

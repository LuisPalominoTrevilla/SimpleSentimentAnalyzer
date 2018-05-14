import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Analyzer {
    
    private String filename;
    private HashTableChain<String, Word> database;
    
    
    public Analyzer(String filename){
        this.filename = filename;
        DoublyLinkedList<Sentence> sentences = this.readSentences(filename);
        this.database = this.createDatabase(sentences);
        
    }

    private DoublyLinkedList<Sentence> readSentences(String filename){
        DoublyLinkedList<Sentence> sentences = new DoublyLinkedList<>();
        if(filename == null){
            return sentences;
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            
            String line = br.readLine();
            while(line != null && !line.isEmpty()){
                line = line.toLowerCase();
                StringTokenizer tk = new StringTokenizer(line, " ");
                String score = tk.nextToken();
                
                if(!score.matches("-?\\d+(\\.\\d+)?")){         // If score is not a number
                    line = br.readLine();
                    continue;
                }else if(Double.parseDouble(score) % 1 != 0.0){ // If the number has decimal part
                    line = br.readLine();
                    continue;
                }
                
                String text = "";
                while(tk.hasMoreTokens()){
                    text += tk.nextToken().replaceAll("[- , . ( ) ; — ' : \"]", "") + " ";
                    
                }
                sentences.addLast(new Sentence(Integer.parseInt(score), text));
                line = br.readLine();
            }
            
        }catch(IOException e){
            // The file was not found or could not be oppened
            return sentences;
        }
        // Nada salio mal
        return sentences;
    }
    
    private HashTableChain<String, Word> createDatabase(DoublyLinkedList<Sentence> sentences){
        HashTableChain<String, Word> database = new HashTableChain<>(50);
        if(sentences == null){
            return database;
        }
        
        for(int i = 0; i < sentences.size(); i++){
            int score = sentences.get(i).getScore();
            String sentence = sentences.get(i).getText();
            StringTokenizer tk = new StringTokenizer(sentence, " ");
            
            while(tk.hasMoreTokens()){
                String word = tk.nextToken();
                if(database.get(word) != null){
                    // If word is already on the dictionary
                    Word w = database.get(word);
                    w.incrementCount();
                    w.incrementScore(score);
                }else{
                    // If word was not on the dictionary
                    database.put(word, new Word(word, score));
                }
            }
        }
        return database;
    }
    
    /**
     * Calculates sentiment of a sentence using the database provided by the class
     * @param sentence the sentence that needs checking
     * @return the score of sentiment
     */
    public double calculateSentiment(String sentence){
        if(this.database == null){
            // Database not initialized
            return 0;
        }
        String phrase = sentence.toLowerCase();
        int validWords = 0;
        double score = 0;
        StringTokenizer tk = new StringTokenizer(phrase, " ");
        
        while(tk.hasMoreTokens()){
            String word = tk.nextToken().replaceAll("[- , . ( ) ; — ' : \"]", "");
            if(!word.matches("-?\\d+(\\.\\d+)?")){      // If the word is not a number
                validWords++;
                if(this.database.get(word) != null){
                    score += this.database.get(word).getAverageScore();  // Get the average score for each word found on the database
                }
            }
        }
        return score/validWords;
    }
}


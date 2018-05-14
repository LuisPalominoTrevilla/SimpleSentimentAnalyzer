import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputPanel extends JPanel{
    
    private JTextArea input;
    private JLabel text;
    private JButton examinar;
    private Analyzer analyzer;
    private JPanel sentiment;
    private static final String[] SENTIMENT_LEVEL = {"#bf0b0b", "#bc720b", "#e8e117", "#6eb22a", "#28a013", "#ffffff"};
    
    
    public InputPanel(){
        super();
        this.setPreferredSize(new Dimension(400, 380));
        this.input = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(this.input);
        this.text = new JLabel("Introduzca la frase a examinar");
        this.text.setFont(new Font("normal", Font.BOLD, 24));
        this.examinar = new JButton("Aceptar");
        this.analyzer = new Analyzer("reviews.txt");
        
        this.sentiment = new JPanel();
        this.sentiment.setPreferredSize(new Dimension(350, 200));
        this.sentiment.setBackground(Color.WHITE);
        
        this.examinar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = InputPanel.this.input.getText();
                int currentCol = 5;
                if(!text.matches("\\s+") && text.length() > 0){
                    double score = InputPanel.this.analyzer.calculateSentiment(text);
                    
                    if(score < -.3){
                        // Very negative
                        currentCol = 0;
                    }else if(score < -.005){
                        // Somewhat negative
                        currentCol = 1;
                    }else if(score < .005){
                        // Neutral
                        currentCol = 2;
                    }else if(score < .3){
                        // Somewhat positive
                        currentCol = 3;
                    }else{
                        //very positive
                        currentCol = 4;
                    }
                    System.out.println(score);
                    
                }else{
                    currentCol = 5;
                }
                InputPanel.this.sentiment.setBackground(Color.decode(InputPanel.SENTIMENT_LEVEL[currentCol]));
            }
        });
        
        
        this.add(this.text);
        this.add(scrollPane);
        this.add(this.examinar);
        this.add(this.sentiment);
    }
}

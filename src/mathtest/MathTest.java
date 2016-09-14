/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mathtest;
// all needed imports
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class MathTest extends JFrame implements MouseListener {
    
    static int mouseX = 0; // initializes mouse locations and starting variables
    static int mouseY = 0;
    static boolean playPressed = false;
    static int lives = 3;
    static boolean questionAnswered = false;
    
    static int totalScore = 0; //initializes different score variables
    static int scoreStreak = 0;
    static int bestStreak;
    static int answerIndexSelected;
    
    static String [] orderedAnsList = new String[4]; //creates ordered answer array which is used for each question
    static String currentQuestion;
    static int answerIndex; //the location in orderedAnsList where the right answer appears
    
    static Question [] fileQuestions = new Question[12]; //creates array for file questions
    
    
    public static Question getQuestion() { //gets question type from various guestion types
        
        Random pickQ = new Random(); 
        int randPick = pickQ.nextInt(6); //selects a random number at a max of question types
        
        if (randPick == 0) { //picks question type based on random selected
            Question q = Addition();
            return q;
        } else if (randPick == 1) {
            Question q = SolveX();
            return q;
        } else if (randPick == 2) {
            Question q = Subtraction();
            return q;
        } else if (randPick == 3) {
            Question q = Multiplication();
            return q;
        } else if (randPick == 4) {
            Question q = Division();
            return q;
        } else {
            Question q = pickFromFile();
            return q;
        }
    
    }
    
    public void importMoreQuestions() throws IOException{ //imports other questions from file
        
        FileReader f = new FileReader("ExtraQuestions.txt"); //sets up text fil reader
        Scanner scan = new Scanner(f); // sets up scanner to scan Strings and doubles
        
        String question; //creates methodal variables
        double rA,wA1,wA2,wA3;
        
        for(int i=0; i<fileQuestions.length;i++) {
            question = scan.next(); //scans the questions
            rA = scan.nextDouble(); //then the answers
            wA1 = scan.nextDouble();
            wA2 =scan.nextDouble();
            wA3=scan.nextDouble();
            fileQuestions[i] = new Question(question,rA,wA1,wA2,wA3); //uses the scan data to fill the Question array
            //System.out.println(fileQuestions[i].question);

        }
  
    }
    
    public static Question pickFromFile() { //takes a random indec from creating array in importMoreQuestions()
        Random r = new Random();
        int randIndex = r.nextInt(fileQuestions.length); //gets a random location in the array
        //uses the Question pieces to make a new question for the game
        return new Question(fileQuestions[randIndex].question,fileQuestions[randIndex].rightAnswer,fileQuestions[randIndex].wrongAnswer1,fileQuestions[randIndex].wrongAnswer2,fileQuestions[randIndex].wrongAnswer3);
    }
    
    public static Question Addition() { 
        
        Random rand = new Random();
        int a = rand.nextInt(2000) + 1; // creates random addition numbers from 1 to 2000
        int b = rand.nextInt(2000) + 1;
        
        String question = Integer.toString(a) + " + " + Integer.toString(b); //converts to question
        double answer = a+b; //adds up right answer
        double wAns1 = answer + rand.nextInt(50)+1; //creates multiple answers close to right answer
        double wAns2 = answer - rand.nextInt(50)+1;
        double wAns3 = answer + rand.nextInt(500)+1;
        if (answer == wAns1 || answer == wAns2 || answer == wAns3) {
            wAns1++;
            wAns2++;
            wAns3++; //adjusts answers if they happen to be the same
        }
                
        return new Question(question,answer,wAns1,wAns2,wAns3); //retruns question with created paramaters
    }
    
    public static Question Subtraction() {
        
        Random rand = new Random();
        int a = rand.nextInt(2000)+ 1; //similar to addition
        int b = rand.nextInt(2000) + 1;
        
        String question = Integer.toString(a) + " - " + Integer.toString(b); //converts to string question
        double answer = a-b; //gets right answer
        double wAns1 = answer + rand.nextInt(50); //gets wrong answers close to right answer
        double wAns2 = answer - rand.nextInt(50);
        double wAns3 = answer + rand.nextInt(500);
              
        return new Question(question,answer,wAns1,wAns2,wAns3); //returns answer 
    }
    
    public static Question Multiplication() {
        
        Random rand = new Random();
        int a = rand.nextInt(50)+1; // gets multiplication parts anywhere from 1 to 50
        int b = rand.nextInt(50)+1;
        
        String question = Integer.toString(a) + " x " + Integer.toString(b); //creates question to string by conversion
        double answer = a*b; //gets right multiplying answer
        double wAns1 = answer + rand.nextInt(100)+10; //gets wrong answers based on right answer
        double wAns2 = answer - rand.nextInt(100)-10;
        double wAns3 = answer + rand.nextInt(1000)+10;
        
        return new Question(question,answer,wAns1,wAns2,wAns3);
    }
    
    public static Question Division() {
        
        Random rand = new Random();
        int a = rand.nextInt(200)+1; //similar to multiplication
        int b = rand.nextInt(50)+1; 
        
        String question = Integer.toString(a) + " / " + Integer.toString(b); //converts to division string question
        double answer = (float)a/b;
        double wAns1 = answer + rand.nextInt(20)+10; //gets wrong answers similar to right answers
        double wAns2 = answer + rand.nextInt(20)+5;
        double wAns3 = answer + rand.nextInt(10)+1;
        
        return new Question(question,answer,wAns1,wAns2,wAns3);
    }
    
        
        
    public static Question SolveX(){
        
        Random rand = new Random();
        int a = rand.nextInt(10)+1; // a + bx = cx + d , gets variables for that equation
        int b = rand.nextInt(20)+1;
        int c = rand.nextInt(10)+1;
        int d = rand.nextInt(20)+1;
        
        if (b == c) {
            c = c + rand.nextInt(5)+1;
        }
        
        String question = Integer.toString(a) + "+" + Integer.toString(b) + "x" + "=" + Integer.toString(c) + "x+" + Integer.toString(d);
        //creates string question by conversion
        double answer = (float)(d-a)/(b-c); //gets accurate answer to equation, rounded later
        double wAns1 = answer + rand.nextInt(10)+1; // gets similar answers based on right answer
        double wAns2 = answer - rand.nextInt(20)-1;
        double wAns3 = answer + rand.nextInt(30)+2;

        return new Question(question, answer, wAns1, wAns2, wAns3);
    }
    

    public void checkMousePos() {  //calcuates current mouse position and updates x-value of mouse and y-value of mouse     
        mouseX = MouseInfo.getPointerInfo().getLocation().x;
        mouseY = MouseInfo.getPointerInfo().getLocation().y;
    }
    
    public void wasAnswerPicked() { //determines which answer option was selected
        
        if (mouseY>400 && mouseY <475) { 
            if (mouseX > 100 && mouseX < 200) { //furtheest left box (answer) is picked is mouse is with certain range
                answerIndexSelected = 0;
                questionAnswered = true;
            }
            else if (mouseX>266 && mouseX<366) { //2nd from left box is picked
                answerIndexSelected = 1;
                questionAnswered = true;
            }
            else if (mouseX>432 && mouseX<532) { //3rd from left vox is picked
                answerIndexSelected = 2;     
                questionAnswered = true;
            }
            else if (mouseX > 598 && mouseX < 700) { //furthest right box is picked
                answerIndexSelected = 3;
                questionAnswered = true;
            }
        }
    }
    
    public static void randomizeAnswers(Question q) { //puts right and wrong answers in random box locations
        
        ArrayList<String> ansList = new ArrayList<String>();
        ansList.add(Double.toString(q.rightAnswer));
        ansList.add(Double.toString(q.wrongAnswer1));
        ansList.add(Double.toString(q.wrongAnswer2));
        ansList.add(Double.toString(q.wrongAnswer3));
        //inserts all answer possibilities into an arraylist to be called then removed
        
        Random rP = new Random();
        
        for (int i = 0; i < orderedAnsList.length; i++) {
            int currRand = rP.nextInt(4-i); // gets a random index for ansList based on its current length
            orderedAnsList[i] = Float.toString(Math.round(10*Double.parseDouble(ansList.get(currRand)))/10f); //adds the answer possibilty from the index to an ordered array of answers
            //System.out.println(orderedAnsList[i]);
            if (ansList.get(currRand).equalsIgnoreCase(Double.toString(q.rightAnswer))) {
                answerIndex = i; //saves the actual correct answer's index for future reference
            }
            ansList.remove(currRand); //takes out that answer possibility from further inspection
            
            //System.out.println(currRand);
            //System.out.println(orderedAnsList[i] + " " + currRand);
            
        }
        
        
        currentQuestion = q.question; // updates the current question that is being referenced
        
        
    }
  
    public void initializeWindow() { //creates starting window
        setTitle("Math Challenge");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.BLUE);
        setVisible(true); //calls paint() for the first time
    }
    
    public static void sleep(int duration) { //allows for the program to pause when needed
        try {
            Thread.sleep(duration);
        } 
        catch (Exception e) {}
    }
    
    @Override
    public void paint(Graphics g) { //draws objects on screen based on the game's instance
        
        if (playPressed == false) { // draws objects for starting screen
            
            Font fTitle = new Font("arial",Font.BOLD,80); //makes "MATH GAME " title
            String titleString = "Math Test Game";
            g.setColor(Color.YELLOW);
            g.setFont(fTitle);
            g.drawString(titleString, 100, 120);
            
            g.setColor(Color.red); // create play box
            g.fillRect(300, 300, 200, 100);  
            g.setColor(Color.YELLOW);
            g.drawRect(300, 300, 200, 100);
            
            Font fPlay = new Font("arial", Font.ITALIC, 45);
            String playString = "PLAY"; //draws play word
            g.setColor(Color.YELLOW);
            g.setFont(fPlay);
            g.drawString(playString, 340, 360);
            
            Font fInstructions = new Font("arial", Font.BOLD,35); //draws instuctions
            g.setColor(Color.PINK);
            g.setFont(fInstructions);
            g.drawString("INSTRUCTIONS",270,450);
            
            Font fSent = new Font("arial",Font.BOLD,20); //draws more instructions
            g.setColor(Color.WHITE);
            g.setFont(fSent);
            g.drawString("Hover mouse over answers, don't overlap answers with the mouse", 100, 500);
            g.drawString("Have Fun!!!", 325, 550);
            g.drawString("Hover to play", 340, 280);
            
            Font names = new Font("arial",Font.BOLD,40); //draws creator names
            g.setColor(Color.ORANGE);
            g.setFont(names);
            g.drawString("Ali Toyserkani, Torry Chen, Michelle Ye", 25, 200);
         
        }
        else if (lives > 0) { //draws objects while game is in process           
            g.setColor(Color.BLUE); //takes away previous screen
            g.fillRect(0,0,800,600);
            
            g.setColor(Color.LIGHT_GRAY); //draws answer boxes
            g.fillRect(100, 400, 100, 75);           
            g.fillRect(266, 400, 100, 75);          
            g.fillRect(432, 400, 100, 75);        
            g.fillRect(598, 400, 100, 75);
            
            Font livesDisplay = new Font("arial",Font.BOLD, 20); //draws updated score/lives titles
            g.setFont(livesDisplay);
            g.setColor(Color.YELLOW);
            g.drawString("LIVES:", 350, 50);
            g.drawString("SCORE:", 500, 50);
            g.drawString("STREAK:", 650, 50);
            
            g.setColor(Color.RED);
            g.drawString(Integer.toString(lives), 450, 50); //draws current lives and score and streak
            g.drawString(Integer.toString(totalScore), 600, 50);
            g.drawString(Integer.toString(scoreStreak), 750, 50);
            
            Font answerDisplay = new Font("arial", Font.BOLD, 20);
            g.setColor(Color.BLACK);
            g.setFont(answerDisplay);
            for (int i=0; i< orderedAnsList.length; i++) { //draws random answer possibilities in gray boxes
                g.drawString(orderedAnsList[i], 125 + 166*i, 440); //puts answers in proper locations
            }
            
            Font questionDisplay = new Font("arial", Font.BOLD, 85); //draws current question onto the screen in white
            g.setColor(Color.WHITE);           
            g.setFont(questionDisplay);
            g.drawString(currentQuestion, 300 -15*currentQuestion.length(), 225);
            
            if (questionAnswered == true) { //draws a post answer result, whether the answered was correct or incorrect
                Font postQDisplay = new Font("arial", Font.BOLD, 30);
                g.setColor(Color.green);
                g.setFont(postQDisplay);
                if (answerIndex == answerIndexSelected) {
                    g.drawString("CORRECT",300 , 550);
                } else {
                    g.setColor(Color.red);
                    g.drawString("WRONG", 300, 550);
                }
                
                
            }
          
        }
        else { //draws game over screen after lives are depleted
            g.setColor(Color.BLUE);
            g.fillRect(0,0,800,600);
            
            Font gameOver = new Font("arial", Font.BOLD, 100);
            g.setColor(Color.MAGENTA);
            g.setFont(gameOver);
            g.drawString("GAME OVER", 100, 200);
            Font overTxt = new Font("arial", Font.BOLD,50);
            g.setFont(overTxt);
            g.drawString("Your final score: ", 100, 350);
            g.setColor(Color.PINK);
            g.drawString(Integer.toString(totalScore), 600, 350);
            g.setColor(Color.MAGENTA);
            g.drawString("Your best streak: ", 100, 450);
            g.setColor(Color.PINK);
            g.drawString(Integer.toString(bestStreak), 600, 450);
        }
        
    }
    
    
    public static void main(String[] args) throws IOException {
        
        MathTest m = new MathTest();
        
        m.initializeWindow(); //initializes program
        m.repaint();
        m.importMoreQuestions();
        // m.addMouseListener(m);
        
        while (playPressed == false) {
            //checks mouse position until the play button has been hovered
            m.checkMousePos();
            //System.out.println(m.mouseX);
            if (mouseX < 500 && mouseX > 300 && mouseY > 300 && mouseY < 400) {
                playPressed = true;
            }
      
        }
        
        while (lives >= 0) { //game runs until lives have been depleted
            
            questionAnswered = false; //is false until question has been answered
            Question currQ = getQuestion(); //calls method to get the new question
            //Question has a question, right answer, and 3 wrong answers

            randomizeAnswers(currQ); //randomize right and wrong answers into different positions
            
            m.repaint(); //updates screen

            while (questionAnswered == false) {
                //runs until an answer possibily has been selected
                m.checkMousePos();
                m.wasAnswerPicked();
        
            }

            if (answerIndex == answerIndexSelected) {
                //adds to score and streaks if the user answer selected matches the actaul right answer
                totalScore++;
                scoreStreak++;
            }
            else {
                //restes streak to 0 and takes away a life if the answer is wrong
                if (scoreStreak > bestStreak) {
                    bestStreak = scoreStreak;
                }
                scoreStreak = 0;
                lives--;
            }
            
            m.repaint(); //updates screen
            sleep(1800); //pauses program for 1.5 seconds to keep "CORRECT" or "INCORRECT" on screen briefly
         
        }
    }

    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        //System.out.println(this.mouseX);
    }
    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

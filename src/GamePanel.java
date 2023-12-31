import lombok.Data;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

@Data
public class GamePanel extends JPanel implements ActionListener {
    //********************** DEFAULT INFO ********************
    boolean running = true;
    boolean gridMode = true;//if you want to have grid like separated square
    Color GridColor = new Color(0x797979);
    public int UNIT_SIZE = 50;
    final int HEADER_HEIGHT = 80;
    Random random ;
    Timer timer;
    int delay = 200;
    public static char direction = 'R';//'R' right ,'L' left ,'U' Up ,'D' down

    //************************* PANEL ************************
    final int PANEL_WIDTH = 600 + 1;
    final int PANEL_HEIGHT = 600 + HEADER_HEIGHT + 1;//+1  is because of difference  of line of grid
    Color panelColor = new Color(0x04395e);


    //************************* FIELD ************************
    //it is not actually the extra panel that we want to add just want to
    //have separation between header and game map
    final int FIELD_WIDTH = PANEL_WIDTH - 1;//-1 is because of difference of  line of grid
    final int FIELD_HEIGHT = PANEL_HEIGHT - HEADER_HEIGHT - 1;

    //********************** SNAKE BODY **********************
    public int bodyParts = 3;
    public int xBodyPart [] = new int[FIELD_WIDTH * FIELD_HEIGHT / UNIT_SIZE];//you can actually use GAME_UNIT but..
    public int yBodyPart [] = new int[FIELD_WIDTH * FIELD_HEIGHT / UNIT_SIZE];
    Color headColorOfSnake = new Color(0x386641);
    Color bodyColorOfSnake = new Color(0xa7c957);
    int eyeSize = UNIT_SIZE / 7;
    Color eyesColorOfSnake = new Color(0xdad7cd);
    int thongWidth =  UNIT_SIZE / 4;
    int thongHeight =  thongWidth / 2 ;
    Color thongColorOfSnake = new Color(0xD4A373);

    //************************* APPLE ************************
    int appleEaten = 0;
    int XApple ;
    int YApple ;
    Color appleColor = new Color(0xD90429);

    File appleEatenSoundFile = new File("Music/appleIsEaten.wav");
    AudioInputStream appleEatenSoundAIS;
    Clip appleEatenSound;
    // ********************** START BUTTON ********************
    JButton startButton;
    int strtWeidth = UNIT_SIZE  * 2;
    int strtHeight = UNIT_SIZE ;

    int strtX = PANEL_WIDTH - strtWeidth  - (UNIT_SIZE / 2);//this formula make y coordinate exactly in proper place
    int strtY = (HEADER_HEIGHT / 2) - (strtHeight / 2) ;//this formula make y coordinate exactly in middle
    //of HEADER

    Font strtFont = new Font("Berlin Sans FB Demi", Font.BOLD, 32);
    Color startForeColor = new Color(0xD4DC64);
    File buttonSoundFile = new File("Music/click buttons.wav");
    AudioInputStream buttonSoundAIS;
    Clip buttonSound;
    // ********************** DEFAULT INFO ********************
    int GAME_UNIT =  (FIELD_WIDTH * FIELD_HEIGHT) / UNIT_SIZE;
    Font headerFontOfSnakeName = new Font("Berlin Sans FB Demi", Font.PLAIN, 60);

    Color gameOverColor = new Color(0xBD1E1E);
    Font gameOverFont = new Font("Berlin Sans FB Demi", Font.PLAIN, 80);



    GamePanel(){
        random = new Random();

        timer =new Timer(delay,this);
        timer.start();

        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setFocusable(true);
        this.setBackground(panelColor);

        this.setLayout(null);/**  it is truly essential to add components like start button*/

        this.setVisible(true);
        this.addKeyListener(new MoveSnake());//we add the move and keyListeners in panel not frame
        System.out.println("GamePanel : done!!!");

        startButCreation();
        startGame();
    }

    private void audioCreationForGame() {
    }

    public void startGame(){
        direction = 'R';
        appleEaten = 0;
        bodyParts = 3;
        for (int i = 0 ; i <= bodyParts ; i++){
            xBodyPart[i] = UNIT_SIZE * (5 - i);
            yBodyPart[i] = UNIT_SIZE * 2 + HEADER_HEIGHT;
        }


        newApple();
        System.out.println("startGame method!!!");
    }

    public boolean isCollisionHappened(){

        //collision checking
        if (xBodyPart[0] < 0 ){// OK !!!
            System.out.println("collision in 1th condition in isColl.. ");
            running = false;
            return false;
        }
        if (yBodyPart[0] < HEADER_HEIGHT){// OK !!!
            System.out.println("collision in 2th condition in isColl.. ");
            running = false;
            return false;
        }
        if (xBodyPart[0] > FIELD_WIDTH - 1){// OK !!! -1 is because se add 1 pixel og having grid
            System.out.println("collision in 3th condition in isColl.. ");
            running = false;
            return false;
        }
        if(yBodyPart[0] > FIELD_HEIGHT + HEADER_HEIGHT - 1 ){// OK !!! -1 is because se add 1 pixel og having grid
            System.out.println("collision in 4th condition in isColl.. ");
            running = false;
            return false;
        }
        for (int i = 1 ; i <= bodyParts ; i++){//
            if (xBodyPart[0] == xBodyPart[i] && yBodyPart[0] == yBodyPart[i]){// OK !!!
                running = false;
                System.out.println("collision in for condition in isColl.. ");
                System.out.println("head crash the [" + i + "] of the body");
                return false;
            }
        }
        return true;
    }

    public void startButCreation(){
        startButton = new JButton();
        startButton.setText("STOP");
        startButton.setSize(new Dimension(strtWeidth,strtHeight));
        startButton.setLocation(strtX ,strtY);

        startButton.setFont(strtFont);
        startButton.setForeground(gameOverColor);


        startButton.setBorder(BorderFactory.createBevelBorder(5));
        startButton.setFocusable(false);
        startButton.setBackground(panelColor);

        startButton.addActionListener(this);

        this.add(startButton);
        System.out.println("start button created");
    }

    public void move(){
        for (int i = bodyParts ;i > 0 ; i--){
            xBodyPart[i] = xBodyPart[i - 1];
            yBodyPart[i] = yBodyPart[i - 1];
        }

        switch(direction){
            case 'R' :xBodyPart[0] += UNIT_SIZE;
                break;
            case 'L' :xBodyPart[0] -= UNIT_SIZE;
                break;
            case 'U' :yBodyPart[0] -= UNIT_SIZE;
                break;
            case 'D' :yBodyPart[0] += UNIT_SIZE;
                break;

        }

    }
    public void newApple() {
        //this while loop is here because we don't want to have apple spawn under the snake ,but it sucks
        boolean isAppleInAppropriatePlace = false;
        while (!isAppleInAppropriatePlace){
            XApple = random.nextInt(FIELD_WIDTH) / UNIT_SIZE;//the output number is base on squares not pixel
            YApple = random.nextInt(FIELD_HEIGHT) / UNIT_SIZE;//mathematical calculation

            // now we get the UNIT of square we should have spatial pixel coordinates
            XApple *= UNIT_SIZE;
            YApple *= UNIT_SIZE;
            YApple += HEADER_HEIGHT;///////////////////////
            for (int i = 0 ; i < bodyParts ; i++){
                if (XApple == xBodyPart[i] && YApple == yBodyPart[i]){
                    isAppleInAppropriatePlace = false;//it is default false but because of
                    //this sout show us status and history of game manipulating and if checking
                    System.out.println("\n\n["+ i +"] : FOUNDED!! :");
                    break;

                }
                else{
                    isAppleInAppropriatePlace = true;
                    //this sout show us status and history of game manipulating and if checking
                    System.out.println("\n["+ i +"] : NO coincidence between apple and body found!!!");
                }
            }
        }
        System.out.println("X square of apple : " + XApple);
        System.out.println("Y square of apple : " + YApple);
        System.out.println("X pixel of apple  : " + XApple);
        System.out.println("Y pixel of apple  : " + YApple + "\n");
        System.out.println("newApple execute applesEaten : " + appleEaten);

    }
    public void eatApple() throws Exception{
        if(xBodyPart[0] == XApple && yBodyPart[0] == YApple){

            //playing sound whenever apple is eaten
            this.appleEatenSoundAIS = AudioSystem.getAudioInputStream(this.appleEatenSoundFile.toURI().toURL());
            this.appleEatenSound = AudioSystem.getClip();
            this.appleEatenSound.open(appleEatenSoundAIS);
            this.appleEatenSound.start();

            appleEaten++;
            bodyParts++;
            newApple();
        }
    }

    @Override//if you don't add this program take it as self-made method not override method
    public void paint(Graphics g) {
        super.paint(g);

        //grid creation
        if (gridMode) {
            for (int i = 0; i <= FIELD_WIDTH / UNIT_SIZE; i++) {
                //for horizontal
                g.setColor(GridColor);
                g.drawLine(0, (i * UNIT_SIZE) + HEADER_HEIGHT, FIELD_WIDTH, (i * UNIT_SIZE) + HEADER_HEIGHT);

                //for vertical
                g.setColor(GridColor);
                g.drawLine(i * UNIT_SIZE, HEADER_HEIGHT, i * UNIT_SIZE, FIELD_HEIGHT + HEADER_HEIGHT);
            }
            g.setFont(headerFontOfSnakeName);
            g.setColor(new Color(0xFFFFFF));
            g.drawString("SNAKE", PANEL_WIDTH / 2 - 75, 60);//change x of it base on size created by formula
        }

        drawAppleObject(g);
        drawSnake(g);

        if(!running){
            drawGameOver(g);
        }
    }
    public void drawSnake(Graphics g){
         //snake creating
         for (int i = 0 ;i <= bodyParts ; i++){
             //if we are drawing head
             if (i == 0){
                 g.setColor(headColorOfSnake);
                 g.fillRect(xBodyPart[i]  ,yBodyPart[i] ,UNIT_SIZE ,UNIT_SIZE );
                 switch (direction){
                     //if the snake heading to the Right:
                     case 'R' :
                         //upper eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 2  ,
                                 yBodyPart[i]  + (UNIT_SIZE / 3 ) * 1 - (eyeSize/2),eyeSize ,eyeSize );
                         //down eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 2  ,
                                 yBodyPart[i] + ((UNIT_SIZE / 3 ) * 2) ,eyeSize ,eyeSize );
                         //thong
                         g.setColor(thongColorOfSnake);
                         g.fillRect(xBodyPart[i] + UNIT_SIZE,
                                 yBodyPart[i] + ((UNIT_SIZE / 2) - (thongHeight / 2)),
                                 thongWidth ,thongHeight );

                         break;


                     case 'L' :
                         //upper eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 1 - eyeSize ,
                                 yBodyPart[i] + (UNIT_SIZE / 3 ) * 1 - (eyeSize/2),eyeSize ,eyeSize );
                         //down eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 1 - eyeSize,
                                 yBodyPart[i] + (UNIT_SIZE / 3 ) * 2 ,eyeSize ,eyeSize );
                         //thong
                         g.setColor(thongColorOfSnake);
                         g.fillRect(xBodyPart[i] - thongWidth,
                                 yBodyPart[i] + ((UNIT_SIZE / 2) - (thongHeight / 2)),
                                 thongWidth ,thongHeight );
                         break;


                     case 'U' :
                         //left eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 1 - (eyeSize/2)  ,
                                 yBodyPart[i] + (UNIT_SIZE / 3 ) * 1 - eyeSize,eyeSize ,eyeSize );
                         //right eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 2  ,
                                 yBodyPart[i] + (UNIT_SIZE / 3 ) * 1 - eyeSize ,eyeSize ,eyeSize );
                         //thong
                         g.setColor(thongColorOfSnake);
                         g.fillRect(xBodyPart[i] + ((UNIT_SIZE / 2) - (thongHeight / 2)),
                                 yBodyPart[i] - thongWidth,
                                 thongHeight , thongWidth  );
                         break;

                     case 'D' :
                         //left eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 1 - (eyeSize/2)   ,
                                 yBodyPart[i] + (UNIT_SIZE / 3 ) * 2 ,eyeSize ,eyeSize );
                         //right eye
                         g.setColor(eyesColorOfSnake);
                         g.fillOval(xBodyPart[i] + (UNIT_SIZE / 3 ) * 2  ,
                                 yBodyPart[i] + (UNIT_SIZE / 3 ) * 2 ,eyeSize ,eyeSize );
                         //thong
                         g.setColor(thongColorOfSnake);
                         g.fillRect(xBodyPart[i] + ((UNIT_SIZE / 2) - (thongHeight / 2)),
                                 yBodyPart[i] + ((UNIT_SIZE )),
                                 thongHeight,thongWidth  );
                         break;
                 }
             }//the rest the body
             else {
                 g.setColor(bodyColorOfSnake);
                 g.fillRect(xBodyPart[i]  ,yBodyPart[i] ,UNIT_SIZE ,UNIT_SIZE );
             }
         }
     }
     public void drawAppleObject(Graphics g){
        //apple eaten info:
         g.setFont(new Font(headerFontOfSnakeName.getName(), Font.PLAIN, 30));//we use getName() because of independence
         g.setColor(new Color(0xFFFFFF));
         g.drawString("apples : " + appleEaten, 10, 50);

        //creating apples in field
         g.setColor(appleColor);
         g.fillRect(XApple +1 ,YApple + 1,UNIT_SIZE - 1,UNIT_SIZE - 1);
         //-1 and +1 is because we want to have rectangle to fit in grid and have grid around it


     }
    public void drawGameOver(Graphics g){
        //this method has special calculation to set and program and magnitude base on
        //basic panel height and width

        //game over background:
        g.setFont(gameOverFont);
        g.setColor(new Color(0x000000));
        g.fillRect(UNIT_SIZE  + UNIT_SIZE / 3,//stater point
                HEADER_HEIGHT + ( 1 * UNIT_SIZE),//stater point
                FIELD_WIDTH  -  (UNIT_SIZE  + UNIT_SIZE / 3) * 2,//*2 is because left gap and right gap
                gameOverFont.getSize());//font size is about height of the text not wiedth

        //game over info:
        g.setFont(gameOverFont);
        g.setColor(gameOverColor);
        g.drawString("GAME OVER", (FIELD_WIDTH / 4 ) -  gameOverFont.getSize() + 5,FIELD_HEIGHT / 3 );
        /** if you get knowledge about FontMatrix fix this calculation */

    }
     /**
     BE CAREFUL
      if you want to have actionPerformed execute
      you should declare

      timer =new Timer(delay,this);
      timer.start();

      and execute it
      and delay is how long should it take to execute it again
     * */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            //checking real and correct status of startbut in its appearance it has appropriate function
            startButton.setText("STOP");
            startButton.setForeground(gameOverColor);


            /** do not change this ordinationb because it would amke graphic bug in top left of frame*/
            try{
                eatApple();
            }catch (Exception E){
                System.out.println("file didn't found");
            }
            move();
            //now here we check our condition is every thing ok or not
            isCollisionHappened();

            repaint();
            /*********************************/
        }else{
            //checking real and correct status of startbut in its appearance it has appropriate function
            startButton.setText("START!");
            startButton.setForeground(startForeColor);
        }
        if (e.getSource() == startButton){
            if (running == false){
                try{
                    startGameByButton();
                }catch (Exception E){
                    System.out.println("file didn't found");
                }
            } else if ( running == true) {
                try{
                    StopGameByButton();
                }catch (Exception E){
                    System.out.println("file didn't found");
                }
            }
        }
    }

    public void startGameByButton() throws Exception{
        //playing sound in start\stop button
        this.buttonSoundAIS = AudioSystem.getAudioInputStream(this.buttonSoundFile.toURI().toURL());
        this.buttonSound = AudioSystem.getClip();
        this.buttonSound.open(buttonSoundAIS);
        this.buttonSound.start();
        startButton.setText("STOP");
        startButton.setForeground(gameOverColor);

        running = true;
        startGame();

        System.out.println("START!!! CLICKED");
    }
    public void StopGameByButton() throws Exception{
        //playing sound in start\stop button
        this.buttonSoundAIS = AudioSystem.getAudioInputStream(this.buttonSoundFile.toURI().toURL());
        this.buttonSound = AudioSystem.getClip();
        this.buttonSound.open(buttonSoundAIS);
        this.buttonSound.start();

        startButton.setText("START!");
        startButton.setForeground(startForeColor);

        running = false;

        System.out.println("STOP CLICKED");
    }
}

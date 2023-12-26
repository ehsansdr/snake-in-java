import lombok.Data;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.Random;

@Data
public class GamePanel extends JPanel implements ActionListener {
    //********************** DEFAULT INFO ********************
    boolean running = true;
    boolean gridMode = true;//if you want to have grid like separated square
    Color GridColor = new Color(0x797979);
    int UNIT_SIZE = 50;
    final int HEADER_HEIGHT = 80;
    Random random ;
    Timer timer;
    int delay = 2000;


    //************************* PANEL ************************
    final int PANEL_WIDTH = 600 + 1;
    final int PANEL_HEIGHT = 600 + HEADER_HEIGHT + 1;//+1  is because of difference  of line of grid
    Color panelColor = new Color(0x04395e);


    //************************* FIELD ************************
    //it is not actually the extra panel that we want to add just want to
    //have separation between header and game map
    final int FIELD_WIDTH = PANEL_WIDTH - 1;//-1 is because of difference of  line of grid
    final int FIELD_HEIGHT = PANEL_HEIGHT - HEADER_HEIGHT;

    //********************** SNAKE BODY **********************

    //************************* APPLE ************************
    int appleEaten = 0;
    int XApple ;
    int YApple ;
    Color appleColor = new Color(0xD90429);

    // ********************** DEFAULT INFO ********************
    int GAME_UNIT =  (FIELD_WIDTH * FIELD_HEIGHT) / UNIT_SIZE;
    Font headerFontOfSnakeName = new Font("Berlin Sans FB Demi", Font.PLAIN, 60);



    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setFocusable(true);
        this.setBackground(panelColor);
        this.setVisible(true);

        this.addKeyListener(new MoveSnake());//we add the move and keyListeners in panel not frame
        System.out.println("GamePanel : done!!!");


        startGame();
    }
    public void startGame(){
        timer =new Timer(delay,this);
        timer.start();
        newApple();
        System.out.println("startGame method!!!");
    }
    public void newApple() {
        XApple = random.nextInt(FIELD_WIDTH) / UNIT_SIZE;
        YApple = random.nextInt(FIELD_HEIGHT) / UNIT_SIZE ;//mathematical calculation
        System.out.println("X square of apple" + XApple);
        System.out.println("Y square of apple" + YApple);

        //now we get the UNIT of square we should have spatial pixel coordinates
        XApple *= UNIT_SIZE;
        YApple *= UNIT_SIZE;
        YApple += HEADER_HEIGHT;///////////////////////

        System.out.println("X pixel of apple" + XApple);
        System.out.println("Y pixel of apple" + YApple);
        System.out.println("newApple execute applesEaten : " + appleEaten);

    }

    @Override//if you don't add this program take it as self-made method not override method
    public void paint(Graphics g) {
        super.paint(g);
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
        drawInfo(g);
    }
     public void drawInfo(Graphics g){
         g.setFont(new Font(headerFontOfSnakeName.getName(), Font.PLAIN, 30));//we use getName() because of independence
         g.setColor(new Color(0xFFFFFF));
         g.drawString("apples : " + appleEaten, 10, 50);

        //creating apples in field
         g.setColor(appleColor);
         g.fillRect(XApple +1 ,YApple + 1,UNIT_SIZE - 1,UNIT_SIZE - 1);
         //-1 and +1 is because we want to have rectangle to fit in grid and have grid around it


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
            startGame();
            repaint();
            System.out.println("now actionPerformed running");
        }
        repaint();
    }
}

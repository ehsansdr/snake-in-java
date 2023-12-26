import com.sun.prism.Graphics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MoveSnake extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
            //this class help us to move snake in panel

        switch (e.getKeyChar()){
            case 'd' :
                System.out.println("you are pressing d");
                GamePanel.direction = 'R';
                break;
            case 's' :
                System.out.println("you are pressing s");
                GamePanel.direction = 'D';
                break;
             case 'a' :
                System.out.println("you are pressing a");
                 GamePanel.direction = 'L';
                break;
            case 'w' :
                System.out.println("you are pressing w");
                GamePanel.direction = 'U';
                break;
        }

        switch (e.getKeyChar()){
            case 'e' :
                System.out.println("you are pressing e");
                break;
            case 'r' :
                System.out.println("you are pressing r");
                break;
             case 't' :
                System.out.println("you are pressing t");
                break;
            case 'y' :
                System.out.println("you are pressing y");
                break;
        }
    }
}

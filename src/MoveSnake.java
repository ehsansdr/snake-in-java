import com.sun.prism.Graphics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MoveSnake extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
            //this class help us to move snake in panel

        switch (e.getKeyChar()){
            case 'd' :
                if (GamePanel.direction != 'L'){
                    System.out.println("you are pressing d");
                    GamePanel.direction = 'R';
                }
                break;
            case 'a' :
                if (GamePanel.direction != 'R'){
                    System.out.println("you are pressing a");
                    GamePanel.direction = 'L';
                    }
                break;
            case 'w' :
                if (GamePanel.direction != 'D'){
                    System.out.println("you are pressing w");
                    GamePanel.direction = 'U';
                }
                break;
            case 's' :
                if (GamePanel.direction != 'U'){
                    System.out.println("you are pressing s");
                    GamePanel.direction = 'D';
                }
                break;
        }

        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT :
                if (GamePanel.direction != 'L'){
                    System.out.println("you are pressing right arrow");
                    GamePanel.direction = 'R';
                }
                break;
            case KeyEvent.VK_LEFT :
                if (GamePanel.direction != 'R'){
                    System.out.println("you are pressing left arrow");
                    GamePanel.direction = 'L';
                }
                break;
            case KeyEvent.VK_UP :
                if (GamePanel.direction != 'D'){
                    System.out.println("you are pressing up arrow");
                    GamePanel.direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN :
                if (GamePanel.direction != 'U'){
                    System.out.println("you are pressing y");
                    GamePanel.direction = 'D';
                }
                break;
        }
    }
}

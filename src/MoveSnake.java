import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MoveSnake extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
            //this class help us to move snake in panel

        switch (e.getKeyChar()){
            case 'd' :
                System.out.println("you are pressing d");
                break;
            case 's' :
                System.out.println("you are pressing s");
                break;
             case 'a' :
                System.out.println("you are pressing a");
                break;
            case 'w' :
                System.out.println("you are pressing w");
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

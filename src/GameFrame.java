import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.setResizable(false);
        this.setLocation(1200,150);
        this.setTitle("snake");
        this.pack();
        this.setVisible(true);
        System.out.println("GameFrame : done!!!");
    }
}

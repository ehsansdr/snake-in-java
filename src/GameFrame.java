import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.setResizable(true);
        this.setLocation(1200,150);
        this.setTitle("snake");
        this.add(new GamePanel());
        this.pack();/** it is important to have this after of add() methods */
        this.setVisible(true);
        System.out.println("GameFrame : done!!!");
    }
}

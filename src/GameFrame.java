import javax.swing.*;

public class GameFrame extends JFrame {
    ImageIcon snakeIcon = new ImageIcon("pics/snakeIcon2.png");
    GameFrame() throws Exception{

        this.setResizable(true);
        this.setLocation(1200,150);
        this.setTitle("snake");
        this.setIconImage(snakeIcon.getImage());
        this.add(new GamePanel());
        this.pack();/** it is important to have this after of add() methods */
        this.setVisible(true);
        System.out.println("GameFrame : done!!!");
    }
}

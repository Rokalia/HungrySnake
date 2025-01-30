import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("Hungry Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(400, 400);
        setSize(Constants.GAME_SIZE+16,Constants.GAME_SIZE+45);
        setResizable(false);
        var mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        var game = new GameField();
        var restart = game.getRestart();
        mainPanel.add(restart, BorderLayout.NORTH);
        mainPanel.add(game, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
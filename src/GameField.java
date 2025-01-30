import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel  implements ActionListener{
    int [][] cells;
    private int dots;
    private int headX;
    private int headY;
    private Timer timer;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean inGame;

    private Button restart;

    public GameField() {
        setBackground(Color.black);
        restart = new Button("Restart");
        restart.setSize(320, 25);
        restart.addActionListener(e -> this.initGame());
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        setSize(Constants.GAME_SIZE, Constants.GAME_SIZE);
    }

    public Button getRestart() {
        return restart;
    }

    public void initGame(){
        restart.setEnabled(false);
        dots = 3;
        cells = new int[20][20];
        left = false;
        right = true;
        up = false;
        down = false;
        inGame = true;
        headX = 2;
        headY = 10;
        for (int i = 0; i < dots; i++)
            cells[headX - i][headY] = dots - i;
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        while (true)
        {
            int appleX = new Random().nextInt(20);
            int appleY = new Random().nextInt(19);
            if(cells[appleX][appleY] == 0)
            {
                cells[appleX][appleY]--;
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        if(inGame){
            for (int i = 0; i < 20; i++)
                for (int j = 0; j < 20; j++)
                    if(cells[i][j] != 0)
                    {
                        int w = Constants.DOT_SIZE;
                        int x = i * w;
                        int y = j * w;
                        g.fillRect(x, y, w, w);
                    }
        } else{
            String str = "Game Over";
            g.drawString(str,125,Constants.GAME_SIZE/2);
        }
        int score = dots - 3;
        g.drawString(String.valueOf(score),Constants.DOT_SIZE*19,Constants.DOT_SIZE);
    }

    public int move(){
        int nextX = headX;
        int nextY = headY;
        if(left)
            nextX--;
        if(right)
            nextX++;
        if(up)
            nextY--;
        if(down)
            nextY++;
        if(nextX < 0 || nextX >= 20 || nextY < 0 || nextY >= 20)
            return -1;
        headX = nextX;
        headY = nextY;
        if (cells[headX][headY] > 0)
            return -1;
        boolean apple = cells[headX][headY] < 0;
        if (apple){
            cells[headX][headY]++;
            dots++;
        }
        updateSnake(apple);
        return apple ? 1 : 0;
    }

    public void updateSnake(boolean apple)
    {
        if(!apple)
        {
            for (int i = 0; i < 20; i++)
                for (int j = 0; j < 20; j++)
                    if(cells[i][j] > 0)
                        cells[i][j]--;
        }
        cells[headX][headY] = dots;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            int res = move();
            if (res == -1)
            {
                inGame = false;
                restart.setEnabled(true);
                timer.stop();
            }
            if (res == 1)
                createApple();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}

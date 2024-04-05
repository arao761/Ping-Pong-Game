import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class PingPongAnimation extends JPanel implements ActionListener {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 1000;
    private final int PADDLE_WIDTH = 20;
    private final int PADDLE_HEIGHT = 120;
    private final int BALL_SIZE = 20;
    private final int PADDLE_SPEED = 50;
    private final Timer timer;
    
    private PingPong game;

    public PingPongAnimation() {
        game = new PingPong(WINDOW_WIDTH, WINDOW_HEIGHT, BALL_SIZE, PADDLE_HEIGHT);

        setBackground(Color.RED);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setFocusable(true);
        timer = new Timer(1000 / 60, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    game.movePaddle(0, game.getLeftPaddleY() - PADDLE_SPEED);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    game.movePaddle(0, game.getLeftPaddleY() + PADDLE_SPEED);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    game.movePaddle(1, game.getRightPaddleY() - PADDLE_SPEED);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.movePaddle(1, game.getRightPaddleY() + PADDLE_SPEED);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillRect(40, game.getLeftPaddleY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WINDOW_WIDTH - 60, game.getRightPaddleY(), PADDLE_WIDTH, PADDLE_HEIGHT);

        for (Ball ball : game.getBalls()) {
            g.setColor(Color.BLACK);
            g.fillOval(ball.getX(), ball.getY(), BALL_SIZE, BALL_SIZE);
        }
        
        g.setColor(Color.BLUE);
        g.setFont(new Font("Courier", Font.BOLD, 24));
        g.drawString("Left Player: " + game.getLeftScore() + "    Right Player: " + game.getRightScore(),
                     (WINDOW_WIDTH / 2) - 170, 30);
    }

    public void actionPerformed(ActionEvent e) {
        game.moveBall();
        repaint();
    }

    public void playMusic() {
        File soundFile;
        soundFile = new File("c:\\Users\\1009197\\Downloads\\01-Private-Landing-_Ft.-Justin-Bieber-_-Future_.wav");
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Audio error" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong Game");
        PingPongAnimation game = new PingPongAnimation();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.playMusic();
    }
}

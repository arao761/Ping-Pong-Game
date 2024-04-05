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
    private PingPong pingPong;

    public PingPongAnimation() {
        pingPong = new PingPong(WINDOW_WIDTH, WINDOW_HEIGHT, BALL_SIZE, PADDLE_HEIGHT);

        setBackground(Color.RED);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setFocusable(true);
        timer = new Timer(1000 / 60, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    pingPong.movePaddle(0, pingPong.getLeftPaddleY() - PADDLE_SPEED);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    pingPong.movePaddle(0, pingPong.getLeftPaddleY() + PADDLE_SPEED);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    pingPong.movePaddle(1, pingPong.getRightPaddleY() - PADDLE_SPEED);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pingPong.movePaddle(1, pingPong.getRightPaddleY() + PADDLE_SPEED);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillRect(40, pingPong.getLeftPaddleY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WINDOW_WIDTH - 60, pingPong.getRightPaddleY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        
        for (Ball ball : pingPong.getBalls()) {
            g.setColor(Color.ORANGE);
           
            //Since the ball is drawn from the top-left corner, we need to subtract half of the ball size
            int x = (int) ball.getX() - BALL_SIZE / 2;
            int y = (int) ball.getY() - BALL_SIZE / 2;
            g.fillOval(x, y, BALL_SIZE, BALL_SIZE);
        }
        
        g.setColor(Color.BLUE);
        g.setFont(new Font("Courier", Font.BOLD, 24));
        g.drawString("Left Player: " + pingPong.getLeftScore() + "    Right Player: " + pingPong.getRightScore(),
                     (WINDOW_WIDTH / 2) - 170, 30);
    }

    public void actionPerformed(ActionEvent e) {
        pingPong.moveBall();
        repaint();
    }

    public void playMusic() {
        File soundFile;
        soundFile = new File("/Users/ankitrao/Downloads/01-Private-Landing-_Ft.-Justin-Bieber-_-Future_.wav");
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

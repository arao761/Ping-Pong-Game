import java.util.ArrayList; 

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class PingPong extends JPanel implements ActionListener {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 1000;
    private final int PADDLE_WIDTH = 20;
    private final int PADDLE_HEIGHT = 120;
    private final int BALL_SIZE = 20;
    private final int PADDLE_SPEED = 50;
    private final Timer timer;

    private int leftPaddleY = WINDOW_HEIGHT / 2;
    private int rightPaddleY = WINDOW_HEIGHT / 2;
    
   /*  private int ballX = WINDOW_WIDTH / 2;
    private int ballY = WINDOW_HEIGHT / 2;
    private int ballXSpeed = (int) Math.random() * 15 + 7;
    private int ballYSpeed = (int) Math.random() * 15 + 7; 
   */

    private Ball[] balls;

    private int rightScore = 0;
    private int leftScore = 0; 

    
   //Ping Pong constructor along with Key Listener for key input
    public PingPong() {
        
        balls = new Ball[10]; // for example, if you want 10 balls
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, (int) Math.random() * 15 + 7, (int) Math.random() * 15 + 7);
        }

        setBackground(Color.RED);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setFocusable(true);
        timer = new Timer(1000 / 60, this);
        timer.start();
        playMusic();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    leftPaddleY = Math.max(leftPaddleY - PADDLE_SPEED, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    leftPaddleY = Math.min(leftPaddleY + PADDLE_SPEED, getHeight() - PADDLE_HEIGHT);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rightPaddleY = Math.max(rightPaddleY - PADDLE_SPEED, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    rightPaddleY = Math.min(rightPaddleY + PADDLE_SPEED, getHeight() - PADDLE_HEIGHT);
                }
            }
        });
    }

    //Background screen as well as the paddles and ball 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillRect(40, leftPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WINDOW_WIDTH - 60, rightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);

        g.setColor(Color.BLACK);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Courier", Font.BOLD, 24));
        g.drawString("Left Player: " + leftScore + "    Right Player: " + rightScore,
                     (WINDOW_WIDTH / 2) - 170, 30);
    }

    public void actionPerformed(ActionEvent e) {
        moveBall();
        repaint();
    }

    private void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        //Collision for the ball whenever it bounces off the top and bottom of the screen
        if (ballY <= 0 || ballY >= WINDOW_HEIGHT - BALL_SIZE) {
            ballYSpeed = -ballYSpeed;
        }

        if (ballX <= 0 || ballX >= WINDOW_WIDTH - BALL_SIZE) {
            ballXSpeed = -ballXSpeed;
        }

        //Collision for the ball whenever it hits the paddles
        if (ballX <= 60 && ballY >= leftPaddleY && ballY <= leftPaddleY + PADDLE_HEIGHT) {
            ballXSpeed = Math.abs(ballXSpeed);
        } else if (ballX >= WINDOW_WIDTH - 80 && ballY >= rightPaddleY && ballY <= rightPaddleY + PADDLE_HEIGHT) {
            ballXSpeed = -Math.abs(ballXSpeed);
        }
        
        //Checks scores and increments it whenever player gains a point
        if (ballX <= 0) {
            rightScore++;
            resetBall();
        } else if (ballX >= WINDOW_WIDTH - BALL_SIZE) {
            leftScore++;
            resetBall();
        }
    }

    //Resets ball in the middle screen each time a point is scored
    private void resetBall() {
        ballX = WINDOW_WIDTH / 2;
        ballY = WINDOW_HEIGHT / 2;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong Game");
        PingPong game = new PingPong();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //Plays background music (Private Landing by Don Toliver)
    public void playMusic() {
        File soundFile;
        soundFile = new File("c:\\Users\\1009197\\Downloads\\01-Private-Landing-_Ft.-Justin-Bieber-_-Future_.wav");
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception e) {
            System.out.println("Audio error" + e.getMessage());
            e.printStackTrace();
        }
    }
}

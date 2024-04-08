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
import java.awt.geom.Ellipse2D;

public class PingPongAnimation extends JPanel implements ActionListener {
    private final int ballSize = 25;
    private final int paddleSpeed = 50;
    private final int PADDLE_WIDTH = 20;
    private final Timer timer;
    private PingPong pingPong;

    public PingPongAnimation() {
        pingPong = new PingPong(1000, 1000, 20, 120);

        setBackground(Color.RED);
        setPreferredSize(new Dimension(1000, 1000));
        setFocusable(true);
        timer = new Timer(1000 / 60, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    pingPong.movePaddle(0, pingPong.getLeftPaddleY() - paddleSpeed);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    pingPong.movePaddle(0, pingPong.getLeftPaddleY() + paddleSpeed);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    pingPong.movePaddle(1, pingPong.getRightPaddleY() - paddleSpeed);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pingPong.movePaddle(1, pingPong.getRightPaddleY() + paddleSpeed);
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Gradient background
        Paint gradient = new GradientPaint(0, 0, Color.ORANGE, getWidth(), getHeight(), Color.RED);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //Paddles
        g2d.setColor(Color.BLUE);
        g2d.fillRoundRect(20, pingPong.getLeftPaddleY(), PADDLE_WIDTH, pingPong.getPaddleHeight(), 10, 10);
        g2d.fillRoundRect(getWidth() - PADDLE_WIDTH - 20, pingPong.getRightPaddleY(), PADDLE_WIDTH, pingPong.getPaddleHeight(), 10, 10);

        //Balls
        for (Ball ball : pingPong.getBalls()) {
            g2d.setColor(Color.WHITE);
            Ellipse2D.Double ballShape = new Ellipse2D.Double(ball.getX() - ballSize / 2, ball.getY() - ballSize / 2, ballSize, ballSize);
            g2d.fill(ballShape);

            g2d.setColor(Color.GRAY);
            g2d.draw(ballShape);
        }

        //Draws a dotted line in the center to distinguish the two sides of the game
        g2d.setColor(Color.WHITE);
        float[] dashPattern = {10, 10};
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        g2d.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());

        // Score display
        g.setColor(Color.BLUE);
        String scoreText = "Left Player: " + pingPong.getLeftScore() + "    Right Player: " + pingPong.getRightScore();
        Font scoreFont = new Font("Courier", Font.BOLD, 24);
        g2d.setFont(scoreFont);
        FontMetrics metrics = g2d.getFontMetrics(scoreFont);
        int textWidth = metrics.stringWidth(scoreText);
        int xPosition = (getWidth() - textWidth) / 2; // Centers the text
        int yPosition = 30; // Vertical position remains unchanged
        g2d.drawString(scoreText, xPosition, yPosition);
    }

    public void actionPerformed(ActionEvent e) {
        pingPong.setWindowWidth(getWidth());
        pingPong.setWindowHeight(getHeight());
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

}

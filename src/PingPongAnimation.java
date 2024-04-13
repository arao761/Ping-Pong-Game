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
    private final Timer timer;
    private PingPong pingPong;
    private Clip musicClip;

    public PingPongAnimation() {
        pingPong = new PingPong(1000, 1000, ballSize, 120);
        setBackground(Color.RED);
        setPreferredSize(new Dimension(1000, 1000));
        setFocusable(true);
        requestFocusInWindow();  // Request focus

        timer = new Timer(1000 / 60, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvents(e);
            }
        });

        pingPong.setGameOverListener(winnerMessage -> SwingUtilities.invokeLater(() -> {
            displayGameOverMessage(winnerMessage);
            stopGameActivities();  // Stops all game activities including music
        }));

       musicClip = playMusic();

    }


    private void stopGameActivities() {
        pingPong.stopGame();  // Stops the game, will stop ball movements
        timer.stop();
        if (musicClip != null) {
            musicClip.stop();  // Stop playing music
        }
    }

    private void handleKeyEvents(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                pingPong.getLeftPaddle().moveUp();
                break;
            case KeyEvent.VK_S:
                pingPong.getLeftPaddle().moveDown();
                break;
            case KeyEvent.VK_UP:
                pingPong.getRightPaddle().moveUp();
                break;
            case KeyEvent.VK_DOWN:
                pingPong.getRightPaddle().moveDown();
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Gradient background
        Paint gradient = new GradientPaint(0, 0, Color.ORANGE, getWidth(), getHeight(), Color.RED);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw paddles
        g2d.setColor(Color.BLUE);
        g2d.fillRoundRect(20, pingPong.getLeftPaddle().getYPosition(), 20, pingPong.getPaddleHeight(), 10, 10);
        g2d.fillRoundRect(getWidth() - 40, pingPong.getRightPaddle().getYPosition(), 20, pingPong.getPaddleHeight(), 10, 10);

        // Draw balls
        for (Ball ball : pingPong.getBalls()) {
            g2d.setColor(Color.WHITE);
            Ellipse2D.Double ballShape = new Ellipse2D.Double(ball.getX() - ballSize / 2, ball.getY() - ballSize / 2, ballSize, ballSize);
            g2d.fill(ballShape);
        }

        // Dotted line in the center
        g2d.setColor(Color.WHITE);
        float[] dashPattern = {10, 10};
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        g2d.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());

        // Score display
        Font scoreFont = new Font("Courier", Font.BOLD, 30);
        g2d.setFont(scoreFont);
        FontMetrics metrics = g2d.getFontMetrics(scoreFont);

        String leftScoreText = "Left Player: " + pingPong.getLeftScore();
        String rightScoreText = "Right Player: " + pingPong.getLeftScore();

        int leftTextWidth = metrics.stringWidth(leftScoreText);
        int rightTextWidth = metrics.stringWidth(rightScoreText);

        g2d.drawString(leftScoreText, 30,30);
        g2d.drawString(rightScoreText, getWidth() - rightTextWidth - 30, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pingPong.setWindowWidth(getWidth());
        pingPong.setWindowHeight(getHeight());
        if (!pingPong.isGameOver()) {
            pingPong.moveBall();
            repaint();
        }
        repaint();
    }

    public Clip playMusic() {
        // Defines the path to the audio file
        String filePath = "c:\\Users\\1009197\\Downloads\\you belong with me.wav";
        File soundFile = new File(filePath);
        
        try {
            // Sets up an AudioInputStream and Clip
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Plays the music continuously
            return clip;
        } catch (Exception e) {
            //Prints error message to consol incase music doesn't play
            System.out.println("Audio error: " + e.getMessage());
            e.printStackTrace(); 
        }

        return null; 
    }
    


   
    private void displayGameOverMessage(String message) {
        JDialog gameOverDialog = new JDialog();
        gameOverDialog.setSize(400, 200);
        gameOverDialog.setLayout(new BorderLayout());
        gameOverDialog.setLocationRelativeTo(this);
        gameOverDialog.setModal(true);

        JPanel messagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, getWidth(), getHeight(), Color.CYAN);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        messagePanel.setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel.setForeground(Color.WHITE);
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        gameOverDialog.add(messagePanel);
        gameOverDialog.setVisible(true);
    }
}
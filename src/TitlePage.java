import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitlePage extends JFrame {

    public TitlePage() {
        setTitle("Ping Pong Extravaganza");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Title label
        JLabel titleLabel = new JLabel("Ping Pong Extravaganza", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        // Start game button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.BOLD, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Layout
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }

    private void startGame() {
        this.setVisible(false); // Hides the title page
        JFrame gameFrame = new JFrame("Ping Pong Game");
        PingPongAnimation game = new PingPongAnimation();
        gameFrame.add(game);
        gameFrame.pack();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        game.playMusic();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TitlePage tp = new TitlePage();
            tp.setVisible(true);
        });
    }
}

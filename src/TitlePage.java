import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TitlePage extends JFrame {

    public TitlePage() {
        setTitle("Ping Pong Extravaganza");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        prepareUI();
    }

    private void prepareUI() {
        JPanel contentPane = createGradientPanel();
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Ping Pong Extravaganza", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Designed by Ankit Rao", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 30));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 50));
        startButton.addActionListener(this::startGame);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(200));
        verticalBox.add(titleLabel);
        verticalBox.add(Box.createVerticalStrut(50));
        verticalBox.add(subtitleLabel);
        verticalBox.add(Box.createVerticalStrut(100));
        verticalBox.add(startButton);
        add(verticalBox, BorderLayout.CENTER);
    }

    private JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180), 0, getHeight(), new Color(135, 206, 235));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private void startGame(ActionEvent e) {
        setVisible(false); // Hide the title page
        JFrame gameFrame = new JFrame("Ping Pong Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PingPongAnimation game = new PingPongAnimation();
        gameFrame.add(game);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null); // Center the game window
        gameFrame.setVisible(true);
        game.requestFocusInWindow(); // Ensure the game panel requests focus
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new TitlePage().setVisible(true);
        });
    }
}

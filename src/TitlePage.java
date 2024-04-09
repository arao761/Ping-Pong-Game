import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitlePage extends JFrame {

    public TitlePage() {
        setTitle("Ping Pong Extravaganza");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Gradient background
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(70, 130, 180); // Steel Blue
                Color color2 = new Color(135, 206, 235); // Sky Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        setContentPane(contentPane);
        setLayout(new BorderLayout());

    // Title label
    JLabel titleLabel = new JLabel("Ping Pong Extravaganza", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
    titleLabel.setForeground(Color.WHITE); // Set color to white for visibility against gradient
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

    // Subtitle label
    JLabel subtitleLabel = new JLabel("Designed by Ankit Rao", SwingConstants.CENTER);
    subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 30));
    subtitleLabel.setForeground(Color.WHITE); // Set color to white for visibility against gradient
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

    // Start game button
    JButton startButton = new JButton("Start Game");
    startButton.setFont(new Font("Arial", Font.BOLD, 50));
    startButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            startGame();
        }
    });
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

    // Layout
    Box verticalBox = Box.createVerticalBox();
    verticalBox.add(Box.createVerticalStrut(200)); // Add empty space
    verticalBox.add(titleLabel);
    verticalBox.add(Box.createVerticalStrut(50)); // Add empty space
    verticalBox.add(subtitleLabel);
    verticalBox.add(Box.createVerticalStrut(100)); // Add empty space
    verticalBox.add(startButton);
    add(verticalBox, BorderLayout.CENTER);

    }


    private void startGame() {
        this.setVisible(false); // Hides the title page to transition to the actual game
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
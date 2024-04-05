import java.awt.*;

public class PingPong {
    private int WINDOW_HEIGHT, WINDOW_WIDTH, BALL_SIZE;
    private int leftPaddleY, rightPaddleY, PADDLE_HEIGHT;
    private int rightScore;
    private int leftScore;
    private Ball[] balls;

    public int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }
    
    public void setWINDOW_HEIGHT(int WINDOW_HEIGHT) {
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }
    
    public int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }
    
    public void setWINDOW_WIDTH(int WINDOW_WIDTH) {
        this.WINDOW_WIDTH = WINDOW_WIDTH;
    }
    
    public int getBALL_SIZE() {
        return BALL_SIZE;
    }
    
    public void setBALL_SIZE(int BALL_SIZE) {
        this.BALL_SIZE = BALL_SIZE;
    }
    
    public int getLeftPaddleY() {
        return leftPaddleY;
    }
    
    public void setLeftPaddleY(int leftPaddleY) {
        this.leftPaddleY = leftPaddleY;
    }
    
    public int getRightPaddleY() {
        return rightPaddleY;
    }
    
    public void setRightPaddleY(int rightPaddleY) {
        this.rightPaddleY = rightPaddleY;
    }
    
    public int getPADDLE_HEIGHT() {
        return PADDLE_HEIGHT;
    }
    
    public void setPADDLE_HEIGHT(int PADDLE_HEIGHT) {
        this.PADDLE_HEIGHT = PADDLE_HEIGHT;
    }
    
    public int getRightScore() {
        return rightScore;
    }
    
    public void setRightScore(int rightScore) {
        this.rightScore = rightScore;
    }
    
    public int getLeftScore() {
        return leftScore;
    }
    
    public void setLeftScore(int leftScore) {
        this.leftScore = leftScore;
    }
    
    public Ball[] getBalls() {
        return balls;
    }
    
    public void setBalls(Ball[] balls) {
        this.balls = balls;
    }

    public PingPong(int windowWidth, int windowHeight, int ballSize, int paddleHeight) {
        this.WINDOW_WIDTH = windowWidth;
        this.WINDOW_HEIGHT = windowHeight;
        this.BALL_SIZE = ballSize;
        this.PADDLE_HEIGHT = paddleHeight;

        // Initialize balls array with 10 balls
        balls = new Ball[10]; 
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, BALL_SIZE, Color.BLACK);
        }
    }

    public void resetBall(Ball ball) {
        ball.setX(WINDOW_WIDTH / 2);
        ball.setY(WINDOW_HEIGHT / 2);
        ball.setXSpeed(0);
        ball.setYSpeed(0);
    }

    public void moveBall() {
        for (Ball ball : balls) {
            ball.move(WINDOW_WIDTH, WINDOW_HEIGHT);

            //Checks score and increments when a point is gained  
            if (ball.getX() - ball.getRadius() <= 0) {
                rightScore++;
                resetBall(ball);
            } else if (ball.getX() + ball.getRadius() >= WINDOW_WIDTH) {
                leftScore++;
                resetBall(ball);
            }
        }
    }

    public void movePaddle(int paddle, int y) {
        if (y < 0) {
            y = 0;
        } else if (y > WINDOW_HEIGHT - PADDLE_HEIGHT) {
            y = WINDOW_HEIGHT - PADDLE_HEIGHT;
        }
    
        if (paddle == 0) {
            leftPaddleY = y;
        } else if (paddle == 1) {
            rightPaddleY = y;
        }
    }
}
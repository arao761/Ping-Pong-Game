import java.awt.*;

public class PingPong {
    private int windowHeight;
    private int windowWidth;
    private int ballSize;
    private int leftPaddleY;
    private int rightPaddleY;
    private int paddleHeight;
    private int rightScore;
    private int leftScore;
    private Ball[] balls;
    private final int paddleWidth = 20;

    public int getWindowHeight() {
        return windowHeight;
    }
    
    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
    
    public int getWindowWidth() {
        return windowWidth;
    }
    
    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }
    
    public int getBallSize() {
        return ballSize;
    }
    
    public void setBallSize(int ballSize) {
        this.ballSize = ballSize;
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
    
    public int getPaddleHeight() {
        return paddleHeight;
    }
    
    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
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
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.ballSize = ballSize;
        this.paddleHeight = paddleHeight;

        this.leftPaddleY = (windowHeight/2) - (paddleHeight/2);
        this.rightPaddleY = (windowHeight/2) - (paddleHeight/2);
    
        // Initialize balls array with 8 balls
        balls = new Ball[8];
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(windowWidth / 2, windowHeight / 2, ballSize, Color.ORANGE);
            // Assigns a random initial speed to each ball
            balls[i].setRandomSpeed(8); 
        }
    }

    public void resetBall(Ball ball) {
        ball.setX(windowWidth / 2);
        ball.setY(windowHeight / 2);
        ball.setRandomSpeed(8);
    }

    public void checkCollisionWithPaddles() {
        for (Ball ball : balls) {
            // Checks collision with the left paddle
            if (ball.getX() - ball.getRadius() <= paddleWidth + 40 && 
                ball.getY() + ball.getRadius() >= leftPaddleY && 
                ball.getY() - ball.getRadius() <= leftPaddleY + paddleHeight) {
                ball.setXSpeed(Math.abs(ball.getXSpeed())); // Reverses speed to make it positive
            }
    
            // Checks collision with the right paddle
            if (ball.getX() + ball.getRadius() >= windowWidth - paddleWidth - 60 && 
                ball.getY() + ball.getRadius() >= rightPaddleY && 
                ball.getY() - ball.getRadius() <= rightPaddleY + paddleHeight) {
                ball.setXSpeed(-Math.abs(ball.getXSpeed())); // Reverses speed to make it negative
            }
        }
    }

    public void moveBall() {
        for (Ball ball : balls) {
            ball.move(windowWidth, windowHeight);
    
            //Calls the checkCollisionWithPaddles method
            checkCollisionWithPaddles();
    
            // Checks for scoring
            if (ball.getX() - ball.getRadius() <= 0) {
                rightScore++;
                resetBall(ball); // Resets the ball to the center
            } else if (ball.getX() + ball.getRadius() >= windowWidth) {
                leftScore++;
                resetBall(ball); // Resets the ball to the center
            }
        }
    }
    
    //Makes sure the paddles dont go off the screen 
    public void movePaddle(int paddle, int y) {
        if (y < 0) {
            y = 0;
        }

        else if (y > windowHeight - paddleHeight) {
            y = windowHeight - paddleHeight;
        }
    
        if (paddle == 0) {
            leftPaddleY = y;
        } else if (paddle == 1) {
            rightPaddleY = y;
        }
    }
    
}

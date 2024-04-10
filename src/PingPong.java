import java.awt.*;

public class PingPong {
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private int windowHeight;
    private int windowWidth;
    private int ballSize;
    private int paddleHeight;
    private int rightScore;
    private int leftScore;
    private Ball[] balls;
    private final int paddleWidth = 20;
    private final int winningScore = 15;
    private GameOverListener gameOverListener;
    private boolean isGameOver = false;

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

    // Getter methods for Paddle objects
    public Paddle getLeftPaddle() {
        return leftPaddle;
    }

    public Paddle getRightPaddle() {
        return rightPaddle;
    }

    public PingPong(int windowWidth, int windowHeight, int ballSize, int paddleHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.ballSize = ballSize;
        this.paddleHeight = paddleHeight;

        this.leftPaddle = new Paddle(windowHeight, paddleHeight, 50);
        this.rightPaddle = new Paddle(windowHeight, paddleHeight, 50);

        this.balls = new Ball[7]; // Initializes the array of 7 balls
        for (int i = 0; i < this.balls.length; i++) {
            this.balls[i] = new Ball(windowWidth / 2, windowHeight / 2, this.ballSize, Color.ORANGE);
            this.balls[i].setRandomSpeed(9); 
        }
    }


    public void resetBall(Ball ball) {
        ball.setX(windowWidth / 2);
        ball.setY(windowHeight / 2);
        ball.setRandomSpeed(14);
    }

    public void checkCollisionWithPaddles() {
        for (Ball ball : balls) {
            // Left paddle collision detection
            if (ball.getX() - ball.getRadius() <= paddleWidth && 
                ball.getY() >= leftPaddle.getYPosition() &&
                ball.getY() <= leftPaddle.getYPosition() + paddleHeight) {
                ball.setXSpeed(Math.abs(ball.getXSpeed()));
            }

            // Right paddle collision detection
            if (ball.getX() + ball.getRadius() >= windowWidth - paddleWidth &&
                ball.getY() >= rightPaddle.getYPosition() &&
                ball.getY() <= rightPaddle.getYPosition() + paddleHeight) {
                ball.setXSpeed(-Math.abs(ball.getXSpeed()));
            }
        }
    }

    public void moveBall() {

        if(isGameOver){
            return;
        }

        for (Ball ball : balls) {
            ball.move(windowWidth, windowHeight);
            checkCollisionWithPaddles();

            // Checks for scoring
            if (ball.getX() - ball.getRadius() <= 0) {
                rightScore++;
                if (rightScore >= winningScore) {
                    if (gameOverListener != null) {
                        gameOverListener.onGameOver("Right Player Wins!");
                    }
                    return; 
                }
                resetBall(ball);
            } else if (ball.getX() + ball.getRadius() >= windowWidth) {
                leftScore++;
                if (leftScore >= winningScore) {
                    if (gameOverListener != null) {
                        gameOverListener.onGameOver("Left Player Wins!");
                    }
                    return;
                }
                resetBall(ball);
            }
        }
    }
    
    //Method to move the paddles up and down
    public void movePaddle(int paddle, boolean moveUp) {
        if (paddle == 0) { // Left paddle
            if (moveUp) {
                leftPaddle.moveUp();
            } else {
                leftPaddle.moveDown();
            }
        } else if (paddle == 1) { // Right paddle
            if (moveUp) {
                rightPaddle.moveUp();
            } else {
                rightPaddle.moveDown();
            }
        }
    }



    //Method to stop the game once the winner has been decided 
    public void stopGame(){
        this.isGameOver = true;
    }

    //Interface to listen for the game over event and to connect the game over event to the main class
    public interface GameOverListener {
        void onGameOver(String winnerMessage);
    }

    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

 } 


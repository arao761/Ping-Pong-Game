public class Paddle {
    private int yPosition;
    private final int windowHeight;
    private final int paddleHeight;
    private final int speed;

    public Paddle(int windowHeight, int paddleHeight, int speed) {
        this.windowHeight = windowHeight;
        this.paddleHeight = paddleHeight;
        this.speed = speed;
        this.yPosition = windowHeight / 2 - paddleHeight / 2;
    }


    //Move up and down methods while making sure the paddles don't go off the screen 
    public void moveUp() {
        yPosition = Math.max(yPosition - speed, 0);
    }

    public void moveDown() {
        yPosition = Math.min(yPosition + speed, windowHeight - paddleHeight);
    }

    public int getYPosition() {
        return yPosition;
    }
}

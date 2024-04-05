    import java.awt.Color;
    import java.awt.Graphics;

    public class Ball {

    // 1. Declaration of Variables

    private double x; //x-coordinate of the center of the ball
    private double y; //y-coordinate of the center of the ball
    private double diameter; //diameter of the ball
    private double radius; //radius of the ball
    private Color color; //color of the ball
    private double xSpeed; //x-speed = change in x-position
    private double ySpeed; //y-speed = change in y-position


    // 2. Create a default constructor
    /**
    * Default Constructor
    * Creates a red ball at (0, 0) with a diameter of 25.  
    * The default speed is 0.
    */
    public Ball() {
    this.x = 0.0;
    this.y = 0.0;
    this.diameter = 25.0;
    this.radius = 12.5;
    this.color = Color.RED;
    this.xSpeed = 0.0;
    this.ySpeed = 0.0;
    }

    // 3. Write a constructor that allows the user to input the parameters (x, y, diameter, Color)
    // and sets the x and y-speed = 0.
    public Ball(double x, double y, double diameter, Color color) {
    this.x = x;
    this.y = y;
    this.diameter = diameter;
    this.radius = this.diameter/2;
    this.color = color;
    this.xSpeed = 0.0;
    this.ySpeed = 0.0;
    }

    // 4. Create getters and setters for all private variables
    public double getX() {
    return x;
    }

    public double getY() {
    return y;
    }

    public double getDiameter() {
    return diameter;
    }

    public double getRadius() {
    return radius;
    }

    public Color getColor() {
    return color;
    }

    public double getXSpeed() {
    return xSpeed;
    }

    public double getYSpeed() {
    return ySpeed;
    }

    public void setX(double x) {
    this.x = x;
    }

    public void setY(double y) {
    this.y = y;
    }

    public void setDiameter(double diameter) {
    this.diameter = diameter;
    this.radius = diameter/2;
    }

    public void setRadius(double radius) {
    this.radius = radius;
    this.diameter = radius * 2;
    }

    public void setColor(Color color) {
    this.color = color;
    }

    public void setXSpeed(double xSpeed) {
    this.xSpeed = xSpeed;
    }

    public void setYSpeed(double ySpeed) {
    this.ySpeed = ySpeed;
    }

    // 5. Finish the following methods
    // 6. Test using BouncingBallTester.java

    public void draw(Graphics g) {
    g.setColor(color);
    g.fillOval((int) (x - radius), (int) (y - radius), (int) diameter, (int) diameter);

    }

    // 7. Sets the center location of the ball
    //    This "teleports" the ball to the given x/y location

    public void setLocation(double xLoc, double yLoc) {
    this.x = xLoc;
    this.y = yLoc;
    }

    // 8. Sets the xSpeed and ySpeed to a value between
    // negative maxSpeed and positive maxSpeed
    public void setRandomSpeed(double maxSpeed) {
    this.xSpeed = (Math.random() * (2 * maxSpeed + 1) -maxSpeed);
    this.ySpeed = (Math.random() * (2 * maxSpeed + 1) -maxSpeed);
    }

    // 9. Write the move method to make the ball move around the screen
    // and bounce of the edges.
    public void move(int rightEdge, int bottomEdge) {
    this.x += xSpeed;
    this.y += ySpeed;

    if(x + radius > rightEdge) {
    x = rightEdge - radius;
    xSpeed = xSpeed * -1;
    }

    if(x - radius < 0) {
    x = radius;
    xSpeed = xSpeed * -1;
    }

    if(y + radius > bottomEdge) {
    y = bottomEdge - radius;
    ySpeed = ySpeed * -1;
    }

    if(y - radius < 0) {
    y = radius;
    ySpeed = ySpeed * -1;
    }

    }
    }
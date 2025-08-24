package Entity;


import java.awt.*;
import java.util.Vector;

public class Snake {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    Vector<Point> body;
    Direction direction;

    public Vector<Point> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setBody(Vector<Point> body) {
        this.body = body;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Snake(){
        this.body = new Vector<>();
        this.body.add(new Point(5,5));
        this.body.add(new Point(4,5));
        this.direction = Direction.RIGHT;
    }

    public Point redirect(int cols, int rows){
        Point head = this.body.firstElement();
        Point nextHead = new Point(head);
        if (direction == Direction.UP){
            nextHead.y -= 1;
        } else if (direction == Direction.DOWN) {
            nextHead.y += 1;
        } else if (direction == Direction.RIGHT) {
            nextHead.x += 1;
        } else if (direction == Direction.LEFT) {
            nextHead.x -= 1;
        }
        if (nextHead.x < 0) nextHead.x = cols - 1;
        if (nextHead.x >= cols) nextHead.x = 0;
        if (nextHead.y < 0) nextHead.y = rows - 1;
        if (nextHead.y >= rows) nextHead.y = 0;

        return nextHead;
    }

    public  void move(int cols, int rows){
        Point nextHead = redirect(cols, rows);
        this.body.addFirst(nextHead);
        this.body.removeLast();
    }

    public void grow(int cols, int rows){
        Point nextHead = redirect(cols, rows);
        this.body.addFirst(nextHead);
    }

    public Point getHead(){
        return this.body.firstElement();
    }
}

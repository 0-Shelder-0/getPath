import java.awt.*;

public class PointLinkedList {
    public Point value;
    public PointLinkedList previous;

    public PointLinkedList(Point value) {
        this.value = value;
    }

    public PointLinkedList(Point value, PointLinkedList previous){
        this.value = value;
        this.previous = previous;
    }
}

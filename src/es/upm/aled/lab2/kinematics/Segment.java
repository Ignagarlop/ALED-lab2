package es.upm.aled.lab2.kinematics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
// TODO: Implemente la clase

public class Segment {
	private double length;          
    private double angle;           
    private final List<Segment> children;

    
    public Segment(double length, double angle) {
        this.length = length;
        this.angle = angle;
        this.children = new ArrayList<>();
    }

    public Segment() {
        this(0.0, 0.0);
    }

    
    public double getLength() { return length; }
    public double getAngle()  { return angle;  }
    public List<Segment> getChildren() { return children; }

    // Setters
    public void setLength(double length) { this.length = length; }
    public void setAngle(double angle)   { this.angle = angle;   }

    
    public void addChild(Segment child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    @Override public String toString() {
        return "Segment{length=" + length + ", angle=" + angle +
               ", children=" + children.size() + "}";
    }

}

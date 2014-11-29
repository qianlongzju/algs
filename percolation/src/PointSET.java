public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p))
            points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points)
            p.draw();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> result = new Stack<Point2D>();
        for (Point2D p : points)
            if (rect.distanceSquaredTo(p) == 0)
                result.push(p);
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        double maxDistance = Double.MAX_VALUE;
        Point2D nearestNeighbor = null;
        for (Point2D q : points)
            if (q.distanceSquaredTo(p) < maxDistance) {
                maxDistance = q.distanceSquaredTo(p);
                nearestNeighbor = q;
            }
        return nearestNeighbor;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
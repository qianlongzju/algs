import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;
    private double minDistance;
    private Point2D nearestNeighbor;
    private ArrayList<Point2D> result;
    private RectHV rect;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    private static class Node {
        private Node left, right;
        private Point2D p;
        private boolean isX;
        private RectHV rect;

        public Node(Point2D p, boolean isX, double xmin, double ymin,
                double xmax, double ymax) {
            this.p = p;
            this.isX = isX;
            this.rect = new RectHV(xmin, ymin, xmax, ymax);
            left = null;
            right = null;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (contains(p))
            return;
        size++;
        if (root == null) {
            root = new Node(p, true, 0.0, 0.0, 1.0, 1.0);
            return;
        }
        Node node = root;
        while (true) {
            if ((node.isX && p.x() < node.p.x())) {
                if (node.left != null)
                    node = node.left;
                else {
                    node.left = new Node(p, !node.isX, node.rect.xmin(),
                            node.rect.ymin(), node.p.x(), node.rect.ymax());
                    return;
                }
            } else if (!node.isX && p.y() < node.p.y()) {
                if (node.left != null)
                    node = node.left;
                else {
                    node.left = new Node(p, !node.isX, node.rect.xmin(),
                            node.rect.ymin(), node.rect.xmax(), node.p.y());
                    return;
                }
            } else if ((node.isX && p.x() >= node.p.x())) {
                if (node.right != null)
                    node = node.right;
                else {
                    node.right = new Node(p, !node.isX, node.p.x(),
                            node.rect.ymin(), node.rect.xmax(),
                            node.rect.ymax());
                    return;
                }
            } else {
                if (node.right != null)
                    node = node.right;
                else {
                    node.right = new Node(p, !node.isX, node.rect.xmin(),
                            node.p.y(), node.rect.xmax(), node.rect.ymax());
                    return;
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node node = root;
        while (node != null) {
            if (node.p.equals(p))
                return true;
            if ((node.isX && p.x() < node.p.x())
                    || (!node.isX && p.y() < node.p.y()))
                node = node.left;
            else
                node = node.right;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null)
            return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.p.draw();

        StdDraw.setPenRadius(0.01);
        if (node.isX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(),
                    node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(),
                    node.p.y());
        }
        draw(node.left);
        draw(node.right);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        result = new ArrayList<Point2D>();
        this.rect = rect;
        range(root);
        return result;
    }

    private void range(Node node) {
        if (node == null)
            return;
        if (rect.distanceSquaredTo(node.p) == 0) {
            result.add(node.p);
        }
        if (node.isX) {
            double x = node.p.x();
            if (rect.xmax() < x) {
                range(node.left);
            } else if (rect.xmin() > x) {
                range(node.right);
            } else {
                range(node.left);
                range(node.right);
            }
        } else {
            double y = node.p.y();
            if (rect.ymax() < y) {
                range(node.left);
            } else if (rect.ymin() > y) {
                range(node.right);
            } else {
                range(node.left);
                range(node.right);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        minDistance = Double.MAX_VALUE;
        nearest(p, root);
        return nearestNeighbor;
    }

    private void nearest(Point2D p, Node node) {
        if (node == null)
            return;
        if (node.rect.distanceSquaredTo(p) > minDistance)
            return;
        double distance = node.p.distanceSquaredTo(p);
        if (distance < minDistance) {
            minDistance = distance;
            nearestNeighbor = node.p;
        }
        if ((node.isX && p.x() < node.p.x())
                || (!node.isX && p.y() < node.p.y())) {
            nearest(p, node.left);
            nearest(p, node.right);
        } else {
            nearest(p, node.right);
            nearest(p, node.left);
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}

public class Test {
    public static void main(String[] args) {
        double a = 0.0;
        double b = -0.0;
        double c = Double.NaN;
        double d = Double.NaN;
        Double x = a;
        Double y = b;
        Double z = c;
        Double w = d;
        System.out.println(a == b);
        System.out.println(x.equals(y));
        System.out.println(c == d);
        System.out.println(z.equals(w));
        // Find values such that (a == b) is true but x.equals(y) is false.  0.0, -0.0
        // Find values such that (a == b) is false but x.equals(y) is true. NaN, NaN
    }
}

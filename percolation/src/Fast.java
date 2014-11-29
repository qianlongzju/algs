import java.util.Arrays;
import java.util.HashSet;

public class Fast {
   public static void main(String[] args)
   {
       In in = new In(args[0]);
       int N = in.readInt();
       Point[] points = new Point[N];
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (int i = 0; i < N; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
           points[i].draw();
       }
       Arrays.sort(points);
       HashSet<HashSet<Point>> S = new HashSet<HashSet<Point>>();
       for (int i = 0; i < N; i++) {
           Point[] temp = points.clone();
           Arrays.sort(temp, points[i].SLOPE_ORDER);
           //StdOut.println("index: " + i + ", origin:" + points[i]);
           //for (int j = 0; j < N; j++)
             //  StdOut.println(temp[j]);
           int count = 1;
           for (int j = 1; j < N; j++) {
               if (points[i].slopeTo(temp[j-1]) == points[i].slopeTo(temp[j])) {
                   count++;
                   //StdOut.println("count:" + count);
               } else {
                   if (count >= 3) {
                       HashSet<Point> s = new HashSet<Point>();
                       s.add(points[i]);
                       for (int k = j-count; k < j; k++)
                           s.add(temp[k]);
                       S.add(s);
                   }
                   count = 1;
               }
           }
           if (count >= 3) {
               HashSet<Point> s = new HashSet<Point>();
               s.add(points[i]);
               for (int k = N-count; k < N; k++)
                   s.add(temp[k]);
               S.add(s);
           }
       }
       for (HashSet<Point> s: S) {
           Point[] temp = new Point[s.size()];
           s.toArray(temp);
           Arrays.sort(temp);
           StdOut.print(temp[0]);
           for (int i = 1; i < temp.length; i++) {
               StdOut.print(" -> " + temp[i]);
           }
           StdOut.println();
           temp[0].drawTo(temp[temp.length-1]);
       }
   }
}
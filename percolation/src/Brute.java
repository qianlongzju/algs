import java.util.Arrays;

public class Brute {
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
       for (int p = 0; p < N; p++) {
           for (int q = p + 1; q < N; q++) {
               double slopePQ = points[p].slopeTo(points[q]);
               for (int r = q + 1; r < N; r++) {
                   double slopePR = points[p].slopeTo(points[r]);
                   if (slopePQ != slopePR)
                       continue;
                   for (int s = r + 1; s < N; s++) {
                       double slopePS = points[p].slopeTo(points[s]);
                       if (slopePR == slopePS) {
                           StdOut.println(points[p] + " -> " + points[q] + " -> " 
                       + points[r] + " -> " + points[s]);
                           points[p].drawTo(points[s]);
                       }
                   }
               }
           }
       }
   }
}
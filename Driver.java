public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = { 6, 5 };
        int[] e1 = { 0, 3 };
        Polynomial p1 = new Polynomial(e1, c1);
        double[] c2 = { -2, -9 };
        int[] e2 = { 1, 4 };
        Polynomial p2 = new Polynomial(e2, c2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        Polynomial t = p1.multiply(p2);
        System.out.println("t(0.0)" + t.evaluate(0.0d));
    }
}
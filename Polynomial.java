/**
 * Polynomial
 */
public class Polynomial {

    double[] polynomial;

    public Polynomial() {
        this.polynomial = new double[] { 0.0 };
    }

    public Polynomial(double[] args) {
        this.polynomial = args;
    }

    public Polynomial add(Polynomial poly) {
        int mxlen = Math.max(poly.polynomial.length, this.polynomial.length);
        double[] tmp = new double[mxlen];
        for (int i = 0; i < mxlen; i++) {
            double a = 0.0d, b = 0.0d;
            if (i < this.polynomial.length)
                a = this.polynomial[i];
            if (i < poly.polynomial.length)
                b = poly.polynomial[i];
            tmp[i] = a + b;
        }
        int len = 0;
        for (int i = 0; i < mxlen; i++) {
            if (tmp[i] != 0.0d)
                len = i + 1;
        }
        double[] res = new double[len];
        for (int i = 0; i < len; i++)
            res[i] = tmp[i];
        Polynomial ret = new Polynomial(res);
        return ret;
    }

    public double evaluate(double var) {
        double sum = 0.0d;
        double tmp = 1.0;
        for (int i = 0; i < this.polynomial.length; i++) {
            sum += this.polynomial[i] * tmp;
            tmp *= var;
        }
        return sum;
    }

    public boolean hasRoot(double var){
        return evaluate(var) == 0.0d;
    }
}
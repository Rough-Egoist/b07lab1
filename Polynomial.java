
/**
 * Polynomial
 */

import java.io.File;
// import java.io.Writer;
import java.lang.String;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
// import java.io.OutputStreamWriter;

public class Polynomial {

    int[] exp; // exponential
    double[] coef; // coefficient

    public Polynomial() {
        this.exp = new int[] { 0 };
        this.coef = new double[] { 0.0 };
    }

    public Polynomial(int[] exp, double[] coef) {
        this.exp = exp;
        this.coef = coef;
    }

    public Polynomial(File file) {
        Scanner sc = new Scanner(file.getName());
        if (sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.charAt(0) != '-' && s.charAt(0) != '+')
                s = s.concat("+");
            int cnt = s.split("+|-").length - 1;
            this.exp = new int[cnt];
            this.coef = new double[cnt];
            cnt = 0;
            for (String mono : s.split("+|-")) { // for monomial in the polynomial
                if (mono.isEmpty())
                    continue;
                String sign = Character.toString(s.charAt(s.indexOf(mono) - 1));
                mono.concat(sign);
                if (mono.contains("x")) {
                    String[] tmp = mono.split("x");
                    this.exp[cnt] = Integer.parseInt(tmp[0]);
                    this.coef[cnt] = Double.parseDouble(tmp[1]);
                } else { // constant
                    this.exp[cnt] = 0;
                    this.coef[cnt] = Double.parseDouble(mono);
                }
                cnt++;
            }
        }
    }

    public Polynomial add(Polynomial poly) {
        int mxlen = poly.exp.length + this.exp.length;
        double[] tmpc = new double[mxlen];
        int[] tmpe = new int[mxlen];
        int cnt = 0, j = 0;
        for (int i = 0; i < this.exp.length; i++) {
            while (poly.exp[j] < this.exp[i]) {
                tmpe[cnt] = poly.exp[j];
                tmpc[cnt] = poly.coef[j];
                cnt++;
                j++;
            }
            if (poly.exp[j] > this.exp[i]) {
                tmpe[cnt] = this.exp[i];
                tmpc[cnt] = this.coef[i];
                cnt++;
            } else if (poly.exp[j] == this.exp[i]) {
                if (poly.coef[j] + this.coef[i] != 0.0d) {
                    tmpe[cnt] = poly.exp[j];
                    tmpc[cnt] = poly.coef[j] + this.coef[i];
                    cnt++;
                }
                j++;
            }
        }
        double[] resc = new double[cnt];
        int[] rese = new int[cnt];
        for (int i = 0; i < cnt; i++) {
            resc[i] = tmpc[i];
            rese[i] = tmpe[i];
        }
        Polynomial res = new Polynomial(rese, resc);
        return res;
    }

    public Polynomial multiply(Polynomial poly) {
        ArrayList<Integer> tmpe = new ArrayList<Integer>();
        for (int i : this.exp) {
            for (int j : poly.exp) {
                if (!tmpe.contains(i + j))
                    tmpe.add(i + j);
            }
        }
        Collections.sort(tmpe);
        double[] tmpc = new double[tmpe.size()];
        for (int i = 0; i < this.coef.length; i++) {
            for (int j = 0; j < poly.coef.length; j++) {
                tmpc[tmpe.indexOf(this.exp[i] + poly.exp[j])] += this.coef[i] * poly.coef[j];
            }
        }
        int cnt = tmpc.length;
        for (double i : tmpc) {
            if (i == 0.0d)
                cnt--;
        }
        int[] rese = new int[cnt];
        double[] resc = new double[cnt];
        cnt = 0;
        for (int i = 0; i < tmpc.length; i++) {
            if (tmpc[i] != 0.0d) {
                rese[cnt] = tmpe.get(i);
                resc[cnt] = tmpc[i];
                cnt++;
            }
        }
        Polynomial res = new Polynomial(rese, resc);
        return res;
    }

    public double evaluate(double var) {
        double sum = 0.0d;
        for (int i = 0; i < this.exp.length; i++)
            sum += this.coef[i] * Math.pow(var, exp[i]);
        return sum;
    }

    public boolean hasRoot(double var) {
        return evaluate(var) == 0.0d;
    }

    void SaveToFile(String name) throws IOException {
        File file = new File(name);
        if (file.exists())
            file.delete();
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        for (int i = 0; i < this.exp.length; i++) {
            if (i > 0) {
                if (this.coef[i] >= 0.0d)
                    writer.write("+");
                else
                    writer.write("-");
            }
            writer.write(String.valueOf(this.coef[i]));
            if (this.exp[i] > 0) {
                writer.write("x");
                if (this.exp[i] > 1)
                    writer.write(String.valueOf(this.exp[i]));
            }
        }
        writer.flush();
        writer.close();
    }
}

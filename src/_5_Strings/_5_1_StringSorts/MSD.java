package _5_Strings._5_1_StringSorts;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
/******************************************************************************
 *  Compilation: javac MSD.java
 *  Execution:   java MSD < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/51radix/words3.txt
 *                https://algs4.cs.princeton.edu/51radix/shells.txt
 *
 *  Sort an array of strings or integers using MSD radix sort.
 *
 *  % java MSD < shells.txt
 *  are
 *  by
 *  sea
 *  seashells
 *  seashells
 *  sells
 *  sells
 *  she
 *  she
 *  shells
 *  shore
 *  surely
 *  the
 *  the
 *
 ******************************************************************************/
public class MSD {

    private static final int R = 256;
    private static final int CUTOFF = 15;

    private MSD() {}

    /**
     * 返回字符串指定下标字符的key
     *
     * @param s 字符串
     */
    private static int charAt(String s, int d) {
        if(d < 0 || d > s.length()) throw new IllegalArgumentException("The method's second argument is error.");
        if(d == s.length()) return -1;
        return s.charAt(d);
    }

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, 0, a.length - 1, 0, aux);
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        // 小范围内使用插入排序
        if(hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int[] count = new int[R + 2];
        for(int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;

        for(int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        for(int i = lo; i <= hi; i++)
            aux[count[charAt(a[i], d) + 1]++] = a[i];

        for(int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        for(int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
    }

    //插入排序
    private static void insertion(String[] a, int lo, int hi, int d) {
        for(int i = lo; i <= hi; i++) {
            for(int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
        }
    }

    //比较: 字符串v是否比字符串w小
    private static boolean less(String v, String w, int d) {
        for(int i = d; i < Math.min(v.length(), w.length()); i++) {
            if(v.charAt(i) < w.charAt(i)) return true;
            else if(v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    //交换数组中两个元素
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] a = in.readAllStrings();

        sort(a);
        for(String s : a)
            StdOut.println(s);
    }

}

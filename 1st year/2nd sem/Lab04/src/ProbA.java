import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class ProbA {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbA().run();
    }

    public char nextToken() {
        try {
            int c = br.read();
            while (c >= 0 && c <= 32) c = br.read();
            if (c < 0) {
                eof = true;
                return '\0';
            }
            int ret = c;
            while (c > 32) {
                c = br.read();
            }
            return (char) ret;
        } catch (IOException e) {
            return '\0';
        }
    }

    public int nextInt() {
        try {
            int c = br.read();
            while (c <= 32) c = br.read();
            boolean sign = false;
            if (c == '-') {
                sign = true;
                c = br.read();
            }
            int ret = 0;
            while (c > 32) {
                ret = ret * 10 + c - '0';
                c = br.read();
            }
            return sign ? -ret : ret;
        } catch (IOException e) {
            return -1;
        }
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "set";
        try {
            File f = new File(name + ".in");
            if (f.exists() && f.canRead()) {
                input = new FileInputStream(f);
                output = new PrintStream(name + ".out");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        br = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output);
        solve();
        br.close();
        out.close();
    }

    void solve() {
        long start = System.currentTimeMillis();
        HashSet<Integer> set = new HashSet<>(1);
        System.err.println(System.currentTimeMillis() - start);
        for (char s = nextToken(); !eof; s = nextToken()) {
            int x = nextInt();
            if (s == 'i') {
                set.add(x);
            } else if (s == 'd') {
                set.delete(x);
            } else if (s == 'e') {
                out.println(set.contains(x));
            }
        }
        System.err.println(System.currentTimeMillis() - start);
    }

    static class HashSet<K> extends HashMap<K, Void> {
        public HashSet(int n) {
            super(n);
        }

        public void add(K key) {
            put(key, null);
        }
    }

    static class HashMap<K, V> {
        static final Random RND = new Random();

        class Pair {
            K key;
            V value;

            Pair(K x, V y) {
                key = x;
                value = y;
            }
        }

        private static final long PPP = BigInteger.probablePrime(17, RND).longValue();
        private static final long MOD = BigInteger.probablePrime(30, RND).longValue();
        private ArrayList<Pair>[] a;
        private int size = 0;

        public HashMap(int n) {
            a = new ArrayList[Integer.highestOneBit(n) << 1];
            for (int i = 0; i < a.length; ++i) {
                a[i] = new ArrayList<>(2);
            }
        }

        private int getHash(K key) {
            long res = 0;
            if (key instanceof String) {
                String s = (String) key;
                for (int i = 0; i < s.length(); ++i) {
                    res = (res * PPP + s.charAt(i)) % MOD;
                }
            } else {
                res = key.hashCode() * PPP % MOD;
            }
            return (int) res & a.length - 1;
        }

        private void resize() {
            ArrayList<Pair>[] b;
            if (size * 4 / 3 > a.length) {
                b = a;
                a = new ArrayList[a.length << 1];
            } else if (size < a.length / 4 && a.length > 16) {
                b = a;
                a = new ArrayList[a.length >> 1];
            } else return;
            for (int i = 0; i < a.length; ++i) {
                a[i] = new ArrayList<>(2);
            }
            size = 0;
            for (int i = 0, bLength = b.length; i < bLength; i++) {
                ArrayList<Pair> list = b[i];
                for (int i1 = 0, listSize = list.size(); i1 < listSize; i1++) {
                    Pair p = list.get(i1);
                    privatePut(p.key, p.value);
                }
            }
        }

        public void put(K key, V value) {
            resize();
            privatePut(key, value);
        }

        private void privatePut(K key, V value) {
            int hash = getHash(key);
            for (int i = 0; i < a[hash].size(); ++i) {
                if (a[hash].get(i).key.equals(key)) {
                    a[hash].set(i, new Pair(key, value));
                    return;
                }
            }
            a[hash].add(new Pair(key, value));
            ++size;
        }

        public boolean contains(K key) {
            int hash = getHash(key);
            ArrayList<Pair> pairs = a[hash];
            for (int i = 0, pairsSize = pairs.size(); i < pairsSize; i++) {
                Pair p = pairs.get(i);
                if (p.key.equals(key)) {
                    return true;
                }
            }
            return false;
        }


        public V get(K key) {
            int hash = getHash(key);
            ArrayList<Pair> pairs = a[hash];
            for (int i = 0, pairsSize = pairs.size(); i < pairsSize; i++) {
                Pair p = pairs.get(i);
                if (p.key.equals(key)) {
                    return p.value;
                }
            }
            return null;
        }

        public boolean delete(K key) {
            resize();
            int hash = getHash(key);
            for (int i = 0; i < a[hash].size(); ++i) {
                if (a[hash].get(i).key.equals(key)) {
                    if (i + 1 < a[hash].size()) {
                        a[hash].set(i, a[hash].remove(a[hash].size() - 1));
                    } else {
                        a[hash].remove(i);
                    }
                    --size;
                    return true;
                }
            }
            return false;
        }


        public ArrayList<K> retainAll() {
            resize();
            ArrayList<K> res = new ArrayList<>();
            for (int i = 0, aLength = a.length; i < aLength; i++) {
                ArrayList<Pair> list = a[i];
                for (int i1 = 0, listSize = list.size(); i1 < listSize; i1++) {
                    Pair p = list.get(i1);
                    res.add(p.key);
                }
            }
            return res;
        }
    }
}
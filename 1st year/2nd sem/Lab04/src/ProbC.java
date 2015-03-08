import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class ProbC {
    boolean eof;
    BufferedReader br;
    StringTokenizer st;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        new ProbC().run();
    }

    public String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return "-1";
            }
        }
        return st.nextToken();
    }

    void run() throws IOException {
        InputStream input = System.in;
        PrintStream output = System.out;
        String name = "linkedmap";
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
        LinkedHashMap<String, String> map = new LinkedHashMap<>(100000);
        for (String s = nextToken(); !eof; s = nextToken()) {
            if (s.equals("put")) {
                map.put(nextToken(), nextToken());
            } else if (s.equals("delete")) {
                map.delete(nextToken());
            } else {
                String res;
                if (s.equals("get")) {
                    res = map.get(nextToken());
                } else if (s.equals("next")) {
                    res = map.next(nextToken());
                } else {
                    res = map.prev(nextToken());
                }
                out.println(res == null ? "none" : res);
            }
        }
    }

    static class LinkedHashMap<K, V> {
        static final Random RND = new Random();
        private Pair<K, V> last = Pair.NULL;

        static class Pair<K, V> {
            static final Pair NULL = new Pair<>();
            K key;
            V value;
            Pair<K, V> next = NULL, prev = NULL;

            Pair() {

            }

            Pair(K x, V y, Pair p) {
                prev = p;
                key = x;
                value = y;
            }

            Pair(K x, V y) {
                key = x;
                value = y;
            }
        }

        private static final long PPP = BigInteger.probablePrime(17, RND).longValue();
        private static final long MOD = BigInteger.probablePrime(30, RND).longValue();
        private ArrayList<Pair<K, V>>[] a;
        private int size = 0;

        public LinkedHashMap(int n) {
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
            ArrayList<Pair<K, V>>[] b;
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
            last = Pair.NULL;
            for (int i = 0, bLength = b.length; i < bLength; i++) {
                ArrayList<Pair<K, V>> list = b[i];
                for (int i1 = 0, listSize = list.size(); i1 < listSize; i1++) {
                    Pair<K, V> p = list.get(i1);
                    privatePut(p.key, p.value);
                }
            }
        }

        public void put(K key, V value) {
            //resize();
            privatePut(key, value);
        }

        private void privatePut(K key, V value) {
            int hash = getHash(key);
            Pair<K, V> p = new Pair(key, value, last);
            for (int i = 0; i < a[hash].size(); ++i) {
                if (a[hash].get(i).key.equals(key)) {
                    a[hash].get(i).value = value;
                    return;
                }
            }
            a[hash].add(p);
            ++size;
            if (last != Pair.NULL) {
                last.next = p;
            }
            last = p;
        }

        public V get(K key) {
            int hash = getHash(key);
            ArrayList<Pair<K, V>> pairs = a[hash];
            for (int i = 0, pairsSize = pairs.size(); i < pairsSize; i++) {
                Pair<K, V> p = pairs.get(i);
                if (p.key.equals(key)) {
                    return p.value;
                }
            }
            return null;
        }

        public boolean delete(K key) {
            //resize();
            int hash = getHash(key);
            for (int i = 0; i < a[hash].size(); ++i) {
                if (a[hash].get(i).key.equals(key)) {
                    Pair<K, V> cur = a[hash].get(i);
                    if (cur.prev != Pair.NULL) {
                        cur.prev.next = cur.next;
                    }
                    if (cur.next != Pair.NULL) {
                        cur.next.prev = cur.prev;
                    }
                    if (last.key.equals(key)){
                        last = last.prev;
                    }
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

        public V prev(K key) {
            int hash = getHash(key);
            ArrayList<Pair<K, V>> pairs = a[hash];
            for (int i = 0, pairsSize = pairs.size(); i < pairsSize; i++) {
                Pair<K, V> p = pairs.get(i);
                if (p.key.equals(key)) {
                    return p.prev.value;
                }
            }
            return null;
        }

        public V next(K key) {
            int hash = getHash(key);
            ArrayList<Pair<K, V>> pairs = a[hash];
            for (int i = 0, pairsSize = pairs.size(); i < pairsSize; i++) {
                Pair<K, V> p = pairs.get(i);
                if (p.key.equals(key)) {
                    return p.next.value;
                }
            }
            return null;
        }
    }
}
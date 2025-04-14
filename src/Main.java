import java.util.Arrays;
import java.util.Random;

class Main {

    private static void countingSort(int[] data, int exp) {
        int n = data.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; ++i)
            count[(data[i] / exp) % 10]++;

        for (int i = 1; i < 10; ++i)
            count[i] += count[i - 1];

        for (int i = n - 1; i >= 0; --i) {
            output[count[(data[i] / exp) % 10] - 1] = data[i];
            --count[(data[i] / exp) % 10];
        }

        System.arraycopy(output, 0, data, 0, n);
    }

    public static void sort(int[] data, int n) {
        int maxElement = Arrays.stream(data).max().getAsInt();
        for (int i = 0; i < n; i++)
            for (int exp = 1; maxElement / exp > 0; exp *= 10)
                countingSort(data, exp);
    }

    public static void main(String[] args) {
        int n = (int)1e2;
        int size = (int)1e6;
        int[] data = new int[size];

        // Generate a random array
        for (int i = 0; i < size; ++i)
            data[i] = new Random().nextInt(999);

        // Sort the array using radix sort
        long start = System.nanoTime();
        sort(data, n);
        long end = System.nanoTime();

        double ms = ((end - start) / 1e6) / n; // to milliseconds
        System.out.printf("Array sortiert in: %.4f ms", ms);
    }
}

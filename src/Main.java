import java.util.Arrays;
import java.util.Random;

class Main {

    public static void sort(int[] array) {
        int maxElement = Arrays.stream(array).max().getAsInt();

        for (int exp = 1; maxElement / exp > 0; exp *= 10)
            countingSort(array, exp);
    }

    private static void countingSort(int[] array, int exp) {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; ++i)
            count[(array[i] / exp) % 10]++;

        for (int i = 1; i < 10; ++i)
            count[i] += count[i - 1];

        for (int i = n - 1; i >= 0; --i) {
            output[count[(array[i] / exp) % 10] - 1] = array[i];
            --count[(array[i] / exp) % 10];
        }

        System.arraycopy(output, 0, array, 0, n);
    }

    public static void main(String[] args) {
        int size = 100 * 100 * 100;
        int[] randomArr = new int[size];

        // Generate a random array
        for (int i = 0; i < size; ++i)
            randomArr[i] = new Random().nextInt(999);

        // Sort the array using radix sort
        long start = System.nanoTime() / 1_000_000L; // to milliseconds
        Main.sort(randomArr);
        long end = System.nanoTime() / 1_000_000L;

        double ms = (end - start); // to milliseconds
        System.out.println("Array sortiert in: " + ms + " ms");
    }
}

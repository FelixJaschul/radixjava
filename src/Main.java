import java.util.Arrays;
import java.util.Random;

interface SortAlgorithm {
    void sort(int[] arr);
    String getName();
}

class RadixSort implements SortAlgorithm {
    private int[] data;
    private int size;

    public RadixSort(int[] arr) {
        this.data = arr;
        this.size = arr.length;
    }

    @Override
    public void sort(int[] arr) {
        data = arr;
        size = arr.length;
        sort_();
    }

    @Override
    public String getName() {
        return "Radix Sort";
    }

    private void countingSort(int log) {
        int[] out = new int[size];
        int[] count = {0, 0};

        // Count the occurrences
        for (int i = 0; i < size; ++i)
            count[(data[i] >> log) % 2]++;

        count[1] = count[0]; // Ones start after zeros
        count[0] = 0;        // Zeros start at index 0

        for (int i = 0; i < size; ++i)
            out[count[(data[i] >> log) % 2]++] = data[i];

        System.arraycopy(out, 0, data, 0, size); // Copy the array back
    }

    private void sort_() {
        int m = data[0];
        for (int i = 1; i < size; ++i)
            if (data[i] > m)
                m = data[i];

        int l = (int)(Math.log(m) / Math.log(2)) + 1;

        for (int i = 0; i < l; ++i)
            countingSort(i);
    }
}

class Test {
    public double sortInMs(int[] data, int repetitions) {
        double total = 0.0;

        for (int i = 0; i < repetitions; i++) {
            int[] temp = Arrays.copyOf(data, data.length); // Copy the array

            RadixSort radixSort = new RadixSort(temp);

            long start = System.nanoTime();
            radixSort.sort(temp);
            long end = System.nanoTime();

            total += (end - start) / 1e6; // Convert to milliseconds
        }

        return total / repetitions;
    }

    public int[] randomArrGenerator(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000); // 0-999
        }
        return arr;
    }
}

public class Main {
    public static void main(String[] args) {
        Test test = new Test();

        int size = 100;
        int[] randomArr = test.randomArrGenerator(size);

        double ms = test.sortInMs(randomArr, 100);

        System.out.printf("Array sorted in: %.4f ms\n", ms);
    }
}

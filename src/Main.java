import java.util.Arrays;

abstract class SortAlgorithm {
    public abstract void sort(int[] arr);
    public abstract String getName();
}

class RadixSort extends SortAlgorithm {
    private int[] data;
    private int size;

    public RadixSort(int[] data) {
        this.data = data;
        this.size = (data != null) ? data.length : 0;
    }

    @Override
    public void sort(int[] arr) {
        this.data = arr;
        this.size = arr.length;
        sort_();
    }

    @Override
    public String getName() {
        return "Radix Sort";
    }

    private void countingSort(int log) {
        int[] out = new int[size];
        int[] count = new int[2];

        // Schauen wie viele einer es gibt
        for (int i = 0; i < size; ++i)
            count[(data[i] >> log) % 2]++;

        count[1] = count[0]; // Einsen fangen nach den Nullen an
        count[0] = 0;        // Nullen fangen bei 0 an

        for (int i = 0; i < size; ++i)
            out[count[(data[i] >> log) % 2]++] = data[i];

        System.arraycopy(out, 0, data, 0, size);
    }

    private void sort_() {
        int m = Arrays.stream(data).max().orElse(0);
        int l = (int)(Math.log(m) / Math.log(2)) + 1;

        for (int i = 0; i < l; ++i)
            countingSort(i);
    }
}

public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 3, 9, 1, 6};

        double ms = sortInMs(arr, 1000);

        System.out.print("Array: ");
        for (int num : arr)
            System.out.print(num + " ");
        System.out.printf("sortiert in: " + "%.4f" + " ms%n", ms);
    }

    private static double sortInMs(int[] data, int n) {
        double total = 0;
        RadixSort radixSort = new RadixSort(data);

        for (int i = 0; i < n; ++i) {
            int[] temp = new int[data.length];
            System.arraycopy(data, 0, temp, 0, data.length);

            // Zeitmessung
            long start = System.nanoTime();
            radixSort.sort(data);
            long end = System.nanoTime();
            total += (end - start) / 1e6;
        }

        return total / n;
    }
}

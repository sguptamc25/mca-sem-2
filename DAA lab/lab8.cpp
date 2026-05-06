#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;

// Global counters to track overall performance
int total_comparisons = 0;
int total_swaps = 0;

// Function to swap two elements and increment swap counter
void swap(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;
    total_swaps++;
}

// Partition function using the last element as pivot (after randomization)
int partition(vector<int>& arr, int low, int high) {
    int pivot = arr[high]; 
    int i = (low - 1); 
    int local_comparisons = 0;
    int local_swaps = 0;

    int start_swaps = total_swaps;

    for (int j = low; j <= high - 1; j++) {
        local_comparisons++;
        total_comparisons++;
        if (arr[j] < pivot) {
            i++;
            swap(arr[i], arr[j]);
        }
    }
    swap(arr[i + 1], arr[high]);
    
    local_swaps = total_swaps - start_swaps;

    // Displaying stats for this specific partition call
    cout << "Partitioning sub-array [" << low << " to " << high << "] | "
         << "Pivot: " << pivot << " | "
         << "Comparisons: " << local_comparisons << " | "
         << "Swaps: " << local_swaps << endl;

    return (i + 1);
}

// Randomized Partition to prevent O(n^2) worst-case
int partition_r(vector<int>& arr, int low, int high) {
    // Generate a random number between low and high
    srand(time(NULL));
    int random = low + rand() % (high - low);

    // Swap random pivot with the last element to reuse standard partition logic
    swap(arr[random], arr[high]);

    return partition(arr, low, high);
}

void quickSort(vector<int>& arr, int low, int high) {
    if (low < high) {
        // pi is partitioning index
        int pi = partition_r(arr, low, high);

        // Recursively sort elements before and after partition
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

void printArray(const vector<int>& arr) {
    for (int i : arr) cout << i << " ";
    cout << endl;
}

int main() {
    vector<int> data = {12, 7, 14, 9, 10, 11, 6};
    int n = data.size();

    cout << "Original array: ";
    printArray(data);
    cout << "--------------------------------------------------" << endl;

    quickSort(data, 0, n - 1);

    cout << "--------------------------------------------------" << endl;
    cout << "Sorted array: ";
    printArray(data);
    cout << "Total Comparisons: " << total_comparisons << endl;
    cout << "Total Swaps: " << total_swaps << endl;

    return 0;
}
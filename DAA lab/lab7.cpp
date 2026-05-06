#include <iostream>
#include <vector>
#include <chrono>
#include <iomanip>
#include <random>
#include <numeric>
#include <string>
#include <algorithm>

using namespace std;
using namespace std::chrono;

// Merge step of Merge Sort
void merge(vector<int>& arr, int left, int mid, int right, long long& comparisons, long long& swaps) {
    int n1 = mid - left + 1;
    int n2 = right - mid;

    // Create temporary vectors
    vector<int> L(n1), R(n2);

    // Copy data to temp vectors L[] and R[]
    for (int i = 0; i < n1; i++)
        L[i] = arr[left + i];
    for (int j = 0; j < n2; j++)
        R[j] = arr[mid + 1 + j];

    int i = 0, j = 0, k = left;

    // Merge the temp vectors back into arr[left..right]
    while (i < n1 && j < n2) {
        comparisons++; // Increment comparison count
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        } else {
            arr[k] = R[j];
            j++;
        }
        swaps++; // Counting the write to the main array as a "swap/move"
        k++;
    }

    // Copy the remaining elements of L[], if there are any
    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
        swaps++; // Count array write
    }

    // Copy the remaining elements of R[], if there are any
    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
        swaps++; // Count array write
    }
}

// Recursive Merge Sort function
void mergeSort(vector<int>& arr, int left, int right, long long& comparisons, long long& swaps) {
    if (left >= right) {
        return; // Base case: 1 or 0 elements
    }
    
    int mid = left + (right - left) / 2;
    
    // Recursively divide the array into two halves
    mergeSort(arr, left, mid, comparisons, swaps);
    mergeSort(arr, mid + 1, right, comparisons, swaps);
    
    // Merge the sorted halves
    merge(arr, left, mid, right, comparisons, swaps);
}

// Helper function to format and print the table rows
void printTableRow(const string& inputType, const string& execTime, long long comparisons, long long swaps) {
    cout << left << setw(20) << inputType
         << "|| " << right << setw(15) << execTime << " || "
         << right << setw(17) << comparisons << " || "
         << right << setw(15) << swaps << " ||" << endl;
    cout << "--------------------------------------------------------------------------------" << endl;
}

// Test runner function
void runTestCase(const string& testName, vector<int> arr) {
    long long comparisons = 0;
    long long swaps = 0;
    int n = arr.size();

    // Start high-resolution timer
    auto start = high_resolution_clock::now();
    
    // Call the recursive merge sort
    mergeSort(arr, 0, n - 1, comparisons, swaps);
    
    // Stop timer
    auto stop = high_resolution_clock::now();
    
    // Calculate duration in nanoseconds
    auto duration = duration_cast<nanoseconds>(stop - start);
    string timeStr = to_string(duration.count()) + " ns";

    // Print the results in the table
    printTableRow(testName, timeStr, comparisons, swaps);
}

// Helper to generate a random vector of a specific size
vector<int> generateRandomVector(int size) {
    vector<int> arr(size);
    iota(arr.begin(), arr.end(), 1); // Fill with 1, 2, ..., size
    
    // Randomly shuffle the elements
    random_device rd;
    mt19937 g(rd());
    shuffle(arr.begin(), arr.end(), g);
    
    return arr;
}

int main() {
    // Define the test cases
    vector<int> tc_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    vector<int> tc_reverse = {25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    vector<int> tc_random = {16, 1, 4, 2, 12, 9, 10, 3, 5, 24, 14, 20, 6, 23, 7, 25, 19, 18, 8, 22, 11, 17, 13, 15, 21};
    vector<int> tc_nearly = {24, 25, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 1, 2};
    vector<int> tc_single = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 1};
    
    // Generate large inputs
    vector<int> tc_large1 = generateRandomVector(60);  // Large Input 1 (>50)
    vector<int> tc_large2 = generateRandomVector(250); // Large Input 2 (>200)

    // Print table header
    cout << "Merge sort:\n";
    cout << left << setw(20) << "Input Type"
         << "|| " << right << setw(15) << "Execution time" << " || "
         << right << setw(17) << "Total comparisons" << " || "
         << right << setw(15) << "Total swaps" << " ||" << endl;
    cout << "--------------------------------------------------------------------------------" << endl;

    // Execute test cases
    runTestCase("Already Sorted", tc_sorted);
    runTestCase("Reverse Sorted", tc_reverse);
    runTestCase("Random Order", tc_random);
    runTestCase("Nearly Sorted", tc_nearly);
    runTestCase("Single Unsorted", tc_single);
    runTestCase("Large Input1(60)", tc_large1);
    runTestCase("Large Input2(250)", tc_large2);

    return 0;
}
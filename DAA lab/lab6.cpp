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

// Iterative Insertion Sort function
void iterativeInsertionSort(vector<int>& arr, long long& comparisons, long long& swaps) {
    int n = arr.size();
    
    for (int i = 1; i < n; i++) {
        int j = i;
        
        // Bubble the element down to its correct position in the sorted sub-array
        while (j > 0) {
            comparisons++; // Count the comparison
            if (arr[j - 1] > arr[j]) {
                swap(arr[j], arr[j - 1]);
                swaps++; // Count the swap
                j--;
            } else {
                // If the element is greater than or equal to the previous, 
                // it is in the correct position relative to the sorted part
                break;
            }
        }
    }
}

// Helper function to format and print the table rows
void printTableRow(const string& inputType, const string& execTime, long long comparisons, long long swaps) {
    cout << left << setw(20) << inputType
         << "|| " << right << setw(15) << execTime << " || "
         << right << setw(17) << comparisons << " || "
         << right << setw(15) << swaps << " ||" << endl;
    cout << "--------------------------------------------------------------------------" << endl;
}

// Test runner function
void runTestCase(const string& testName, vector<int> arr) {
    long long comparisons = 0;
    long long swaps = 0;

    // Start high-resolution timer
    auto start = high_resolution_clock::now();
    
    // Call the iterative sort
    iterativeInsertionSort(arr, comparisons, swaps);
    
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
    cout << "Iterative insertion sort:\n";
    cout << left << setw(20) << "Input Type"
         << "|| " << right << setw(15) << "Execution time" << " || "
         << right << setw(17) << "Total comparisons" << " || "
         << right << setw(15) << "Total swaps" << " ||" << endl;
    cout << "--------------------------------------------------------------------------" << endl;

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
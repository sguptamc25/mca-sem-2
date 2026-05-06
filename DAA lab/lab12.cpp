#include <iostream>
#include <vector>
#include <limits>

using namespace std;

// A constant representing infinity
const int INF = numeric_limits<int>::max();

// Structure to represent a directed edge
struct Edge {
    int u, v, weight;
};

void bellmanFord(int V, const vector<Edge>& edges, int startNode) {
    // Distance vector initialized to infinity
    vector<int> dist(V, INF);
    dist[startNode] = 0;

    // Step 1: Relax all edges V - 1 times
    // A simple shortest path from src to any other vertex can have at most V - 1 edges
    for (int i = 1; i <= V - 1; ++i) {
        for (const auto& edge : edges) {
            int u = edge.u;
            int v = edge.v;
            int weight = edge.weight;
            
            // Relaxation step
            if (dist[u] != INF && dist[u] + weight < dist[v]) {
                dist[v] = dist[u] + weight;
            }
        }
    }

    // Step 2: Check for negative-weight cycles
    // If we can still relax an edge, then a negative cycle exists
    for (const auto& edge : edges) {
        int u = edge.u;
        int v = edge.v;
        int weight = edge.weight;
        
        if (dist[u] != INF && dist[u] + weight < dist[v]) {
            cout << "\nGraph contains a negative-weight cycle! Shortest paths cannot be defined." << endl;
            return;
        }
    }

    // Step 3: Output the shortest distances
    cout << "\n--- Shortest Distances from Node " << startNode << " ---" << endl;
    cout << "Node\tDistance" << endl;
    for (int i = 0; i < V; ++i) {
        if (dist[i] == INF) {
            cout << i << "\tUnreachable" << endl;
        } else {
            cout << i << "\t" << dist[i] << endl;
        }
    }
}

int main() {
    int V, E;
    cout << "Enter the total number of vertices (nodes 0 to V-1): ";
    cin >> V;

    if (V <= 0) {
        cout << "Graph must have at least 1 vertex." << endl;
        return 0;
    }

    cout << "Enter the number of edges: ";
    cin >> E;

    int isDirectedInput;
    cout << "Is the graph directed? (1 for Yes, 0 for No): ";
    cin >> isDirectedInput;
    bool isDirected = (isDirectedInput == 1);

    // List to store all edges
    vector<Edge> edges;

    if (E > 0) {
        cout << "Enter the edges (format: u v weight) where u and v are between 0 and " << V - 1 << ":" << endl;
        for (int i = 0; i < E; ++i) {
            int u, v, weight;
            cin >> u >> v >> weight;
            
            // Validate user input bounds
            if (u >= 0 && u < V && v >= 0 && v < V) {
                edges.push_back({u, v, weight});
                if (!isDirected) {
                    // Note: An undirected negative edge inherently creates a negative cycle (u -> v -> u)
                    edges.push_back({v, u, weight}); 
                }
            } else {
                cout << "Invalid edge (" << u << ", " << v << "). Vertices must be between 0 and " << V - 1 << ". Try again.\n";
                i--; // Retry this edge
            }
        }
    }

    int startNode;
    cout << "Enter the source node for Bellman-Ford Algorithm (0 to " << V - 1 << "): ";
    cin >> startNode;

    if (startNode < 0 || startNode >= V) {
        cout << "Invalid starting node!" << endl;
        return 1;
    }

    // Run Bellman-Ford Algorithm
    bellmanFord(V, edges, startNode);

    return 0;
}
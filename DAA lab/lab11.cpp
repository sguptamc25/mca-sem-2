#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <utility>

using namespace std;

// A constant representing infinity
const int INF = numeric_limits<int>::max();

void dijkstra(int startNode, int V, const vector<vector<pair<int, int>>>& adj) {
    // Priority queue to store {distance, vertex}. 
    // greater<> ensures it acts as a min-heap (smallest distance on top).
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    
    // Distance vector initialized to infinity
    vector<int> dist(V, INF);

    // Initialize the start node
    pq.push({0, startNode});
    dist[startNode] = 0;

    while (!pq.empty()) {
        int currentDist = pq.top().first;
        int currentNode = pq.top().second;
        pq.pop();

        // If we find a longer path in the queue, skip it (optimization)
        if (currentDist > dist[currentNode]) continue;

        // Traverse all adjacent nodes
        for (const auto& edge : adj[currentNode]) {
            int neighbor = edge.first;
            int weight = edge.second;

            // Relaxation step: if a shorter path to neighbor is found
            if (dist[currentNode] + weight < dist[neighbor]) {
                dist[neighbor] = dist[currentNode] + weight;
                pq.push({dist[neighbor], neighbor});
            }
        }
    }

    // Output the shortest distances
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

    // Adjacency list representation: adj[u] contains pairs of {v, weight}
    vector<vector<pair<int, int>>> adj(V);

    if (E > 0) {
        cout << "Enter the edges (format: u v weight) where u and v are between 0 and " << V - 1 << ":" << endl;
        for (int i = 0; i < E; ++i) {
            int u, v, weight;
            cin >> u >> v >> weight;
            
            if (weight < 0) {
                cout << "Dijkstra's algorithm does not support negative weights. Please try again." << endl;
                i--; // Retry this edge
                continue;
            }

            // Validate user input bounds
            if (u >= 0 && u < V && v >= 0 && v < V) {
                adj[u].push_back({v, weight});
                if (!isDirected) {
                    adj[v].push_back({u, weight}); // Add reverse edge if undirected
                }
            } else {
                cout << "Invalid edge (" << u << ", " << v << "). Vertices must be between 0 and " << V - 1 << ". Try again.\n";
                i--; // Retry this edge
            }
        }
    }

    int startNode;
    cout << "Enter the source node for Dijkstra's Algorithm (0 to " << V - 1 << "): ";
    cin >> startNode;

    if (startNode < 0 || startNode >= V) {
        cout << "Invalid starting node!" << endl;
        return 1;
    }

    // Run Dijkstra's Algorithm
    dijkstra(startNode, V, adj);

    return 0;
}
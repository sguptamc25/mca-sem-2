#include <iostream>
#include <vector>

using namespace std;

// Recursive function to perform DFS for a single connected component
void dfs(int currentNode, const vector<vector<int>>& adj, vector<bool>& visited, int& nodesVisitedInComponent) {
    // Mark the current node as visited and print it
    visited[currentNode] = true;
    cout << currentNode << " ";
    nodesVisitedInComponent++;

    // Visit all unvisited neighbors recursively
    for (int neighbor : adj[currentNode]) {
        if (!visited[neighbor]) {
            dfs(neighbor, adj, visited, nodesVisitedInComponent);
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

    // Initialize adjacency list
    vector<vector<int>> adj(V);

    if (E > 0) {
        cout << "Enter the edges (format: u v) where u and v are between 0 and " << V - 1 << ":" << endl;
        for (int i = 0; i < E; ++i) {
            int u, v;
            cin >> u >> v;
            
            // Validate user input bounds
            if (u >= 0 && u < V && v >= 0 && v < V) {
                adj[u].push_back(v);
                if (!isDirected) {
                    adj[v].push_back(u); // Add reverse edge if undirected
                }
            } else {
                cout << "Invalid edge (" << u << ", " << v << "). Vertices must be between 0 and " << V - 1 << ". Try again.\n";
                i--; // Retry this edge
            }
        }
    }

    int startNode;
    cout << "Enter the starting node for DFS (0 to " << V - 1 << "): ";
    cin >> startNode;

    if (startNode < 0 || startNode >= V) {
        cout << "Invalid starting node!" << endl;
        return 1;
    }

    // Keep track of visited nodes globally
    vector<bool> visited(V, false);
    int totalNodesVisited = 0;
    int componentCount = 1;

    cout << "\n--- DFS Traversal Order ---" << endl;
    
    // 1. Traverse the primary component from the user's starting node
    int nodesInCurrentComponent = 0;
    cout << "Component " << componentCount << " (Starting from " << startNode << "): ";
    dfs(startNode, adj, visited, nodesInCurrentComponent);
    totalNodesVisited += nodesInCurrentComponent;
    cout << endl;

    // 2. Handle disconnected components
    for (int i = 0; i < V; ++i) {
        if (!visited[i]) {
            componentCount++;
            nodesInCurrentComponent = 0; // Reset for the new component
            cout << "Component " << componentCount << " (Disconnected, starting from " << i << "): ";
            dfs(i, adj, visited, nodesInCurrentComponent);
            totalNodesVisited += nodesInCurrentComponent;
            cout << endl;
        }
    }

    cout << "\n--- Summary ---" << endl;
    cout << "Total number of components found: " << componentCount << endl;
    cout << "Total number of nodes visited: " << totalNodesVisited << endl;

    return 0;
}
#include <iostream>
#include <vector>
#include <queue>

using namespace std;

// Function to perform BFS for a single connected component
int bfs(int startNode, const vector<vector<int>>& adj, vector<bool>& visited) {
    queue<int> q;
    q.push(startNode);
    
    // Mark the starting node as visited
    visited[startNode] = true;

    int nodesVisitedInComponent = 0;

    while (!q.empty()) {
        int currentNode = q.front();
        q.pop();

        // Process the current node
        cout << currentNode << " ";
        nodesVisitedInComponent++;

        // Visit all unvisited neighbors
        for (int neighbor : adj[currentNode]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true; // Mark as visited when pushing to queue
                q.push(neighbor);
            }
        }
    }
    return nodesVisitedInComponent;
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
    cout << "Enter the starting node for BFS (0 to " << V - 1 << "): ";
    cin >> startNode;

    if (startNode < 0 || startNode >= V) {
        cout << "Invalid starting node!" << endl;
        return 1;
    }

    // Keep track of visited nodes globally
    vector<bool> visited(V, false);
    int totalNodesVisited = 0;
    int componentCount = 1;

    cout << "\n--- BFS Traversal Order ---" << endl;
    
    // 1. Traverse the primary component from the user's starting node
    cout << "Component " << componentCount << " (Starting from " << startNode << "): ";
    totalNodesVisited += bfs(startNode, adj, visited);
    cout << endl;

    // 2. Handle disconnected components
    for (int i = 0; i < V; ++i) {
        if (!visited[i]) {
            componentCount++;
            cout << "Component " << componentCount << " (Disconnected, starting from " << i << "): ";
            totalNodesVisited += bfs(i, adj, visited);
            cout << endl;
        }
    }

    cout << "\n--- Summary ---" << endl;
    cout << "Total number of components found: " << componentCount << endl;
    cout << "Total number of nodes visited: " << totalNodesVisited << endl;

    return 0;
}
import java.util.*;

class ShortestPath {
    static final int V = 10;

    int minDistance(int dist[], Boolean sptSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    void printSolution(int dist[], String src, String dest, int parent[], Map<Integer, String> stopNames) {
        System.out.println("Shortest path from " + src + " to " + dest + ":");
        System.out.println("Distinaion \t\t Distance from Source");
        for (Map.Entry<Integer, String> entry : stopNames.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(dest)) {
                System.out.println(entry.getValue() + " \t\t\t " + dist[entry.getKey()] + "km");
                break;
            }
        }

        System.out.println("Path:");
        System.out.print(src);
        printPath(parent, src, stopNames, dest);
    }

    void printPath(int parent[], String src, Map<Integer, String> stopNames, String j) {
        if (!stopNames.containsValue(j))
            return;

        int index = getKeyFromValue(stopNames, j);
        if (parent[index] == -1)
            return;

        printPath(parent, src, stopNames, stopNames.get(parent[index]));

        System.out.print("--> " + j);
    }

    int getKeyFromValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    void dijkstra(int graph[][], String src, String dest, Map<String, Integer> stopIndices,
            Map<Integer, String> stopNames) {
        int dist[] = new int[V];
        Boolean sptSet[] = new Boolean[V];
        int parent[] = new int[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
    
        dist[stopIndices.get(src)] = 0;
        parent[stopIndices.get(src)] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;
            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
        }

        printSolution(dist, src, dest, parent, stopNames);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int graph[][] = new int[][] { { 0, 2, 0, 0, 0, 0, 0, 0, 6, 0 },
                { 2, 0, 3, 0, 8, 0, 0, 0, 0, 0 },
                { 0, 3, 0, 5, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 5, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 8, 0, 1, 3, 0, 5, 0, 0, 0 },
                { 0, 0, 0, 0, 3, 0, 5, 6, 8, 0 },
                { 0, 0, 0, 0, 0, 5, 0, 6, 0, 0 },
                { 0, 0, 0, 0, 5, 6, 6, 0, 0, 0 },
                { 6, 0, 0, 0, 0, 8, 0, 0, 0, 7 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 7, 0 } };
        ShortestPath t = new ShortestPath();
        Map<String, Integer> stopIndices = new HashMap<>();
        Map<Integer, String> stopNames = new HashMap<>();
        stopIndices.put("Shivranjani", 0);
        stopIndices.put("Nehrunagar", 1);
        stopIndices.put("Anjali", 2);
        stopIndices.put("Kankaria Lake", 3);
        stopIndices.put("Geeta Mandir", 4);
        stopIndices.put("Kalupur Railway Station", 5);
        stopIndices.put("Memko Cross Road", 6);
        stopIndices.put("Soni Ni Chali", 7);
        stopIndices.put("Akhabarnagar", 8);
        stopIndices.put("Chandkheda", 9);
        stopNames.put(0, "Shivranjani");
        stopNames.put(1, "Nehrunagar");
        stopNames.put(2, "Anjali");
        stopNames.put(3, "Kankaria Lake");
        stopNames.put(4, "Geeta Mandir");
        stopNames.put(5, "Kalupur Railway Station");
        stopNames.put(6, "Memko Cross Road");
        stopNames.put(7, "Soni Ni Chali");
        stopNames.put(8, "Akhabarnagar");        
        stopNames.put(9,"Chandkheda");

        System.out.println("Enter source bus stop name: ");
        String src = scanner.nextLine();

        System.out.println("Enter destination bus stop name: ");
        String dest = scanner.nextLine();

        t.dijkstra(graph, src, dest, stopIndices, stopNames);
        scanner.close();
    }
}
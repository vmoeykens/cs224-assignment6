import java.util.LinkedList;

class PrimMST {

    public PrimMST() {
    }

    public static void main(String[] args) {
        Graph<Character> g = new Graph<Character>();

        g.addEdge('a', 'b', 9);
        g.addEdge('a', 'f', 14);
        g.addEdge('a', 'g', 15);
        g.addEdge('b', 'c', 24);
        g.addEdge('c', 'f', 18);
        g.addEdge('c', 'e', 2);
        g.addEdge('c', 'd', 6);
        g.addEdge('c', 'h', 19);
        g.addEdge('d', 'e', 11);
        g.addEdge('d', 'h', 6);
        g.addEdge('e', 'f', 30);
        g.addEdge('e', 'g', 20);
        g.addEdge('e', 'h', 16);
        g.addEdge('f', 'g', 30);
        g.addEdge('g', 'h', 44);

        System.out.println("Graph represented by adjacency list:\n" + g.toString());
    }

}

class Graph<T> {
    LinkedList<Vertex<T>> vertices;


    public Graph() {
        this.vertices = new LinkedList<Vertex<T>>();
    }

    public void addEdge(T from, T to, int weight) {
        Edge<T> toEdge = new Edge<T>(to, weight);
        Edge<T> fromEdge = new Edge<T>(from, weight);

        if (vertexExists(from)) {
            this.vertices.get(getVertexById(from)).addEdge(toEdge);
        } else {
            Vertex<T> tempVertex = new Vertex<T>(from);
            tempVertex.addEdge(toEdge);
            this.vertices.add(tempVertex);
        }
        if (vertexExists(to)) {
            this.vertices.get(getVertexById(to)).addEdge(fromEdge);
        } else {
            Vertex<T> tempVertex = new Vertex<T>(to);
            tempVertex.addEdge(fromEdge);
            this.vertices.add(tempVertex);
        }
    }

    public boolean vertexExists(T id) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int getVertexById(T id) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String outputString = "";
        for (int i = 0; i < this.vertices.size(); i++) {
            outputString += this.vertices.get(i).toString() + "\n";
        }
        return outputString;
    }

}

class Vertex<T> {
    T id;
    LinkedList<Edge<T>> edges;


    public Vertex(T id) {
        this.id = id;
        this.edges = new LinkedList<Edge<T>>();
    }

    public boolean addEdge(Edge<T> edge) {
        if (this.edges.contains(edge)) {
            return false;
        } else {
            this.edges.add(edge);
            return true;
        }
    }

    public T getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String outputString = this.id + ": ";
        for (int i = 0; i < this.edges.size(); i++) {
            outputString += this.edges.get(i).toString() + ", "; 
        }
        return outputString;
    }

}

class Edge<T> {
    T to;
    int weight;

    public Edge(T to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    public T getTo() {
        return this.to;
    }

    public int getWeight() {
        return this.weight;
    }


    @Override
    public String toString() {
        return "(" + getTo() + "," + getWeight() + ")";
    }

}
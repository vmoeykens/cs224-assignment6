import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

class PrimMST {

    public static void main(String[] args) {
        Graph<Character> g = makeGraph();
        System.out.println("Graph represented by adjacency list:\n" + g.toString() + "\n");
        System.out.print("Enter character for the start vertex: ");
        Scanner scanner = new Scanner(System.in); 
        char startVertex = scanner.next().charAt(0);
        scanner.close();
        runPrimMST(g, new Vertex<Character>(startVertex));

    }
    
    public static void runPrimMST(Graph<Character> g, Vertex<Character> startVertex) {
        ArrayList<Character> S = new ArrayList<Character>();
        ArrayList<TreeEdge> T = new ArrayList<TreeEdge>();
        g.changeKey(startVertex, 0);
        PriorityQueue<Vertex<Character>> q = BuildPQ(g);
        displayState(S, T, q);
        
        while (q.peek() != null) {
            Vertex<Character> v = q.poll();
            S.add(v.getId());
            if (v.getId() != startVertex.getId()) {
                T.add(new TreeEdge(v.getPredecessor(), v.getId()));
                // add the edge (pred(v), v) to T where pred is predecessor
            }
            LinkedList<Edge<Character>> edges = v.getEdges();
            for (int i = 0; i < edges.size(); i++) {
                Edge<Character> edge = edges.get(i);
                if (!S.contains(edge.getTo())) {
                    if (edge.weight < g.getVertices().get(g.getVertexById(edge.getTo())).getKeyVal()) {
                        g.changeKey(g.getVertices().get(g.getVertexById(edge.getTo())), edge.weight);
                        g.changePred(g.getVertices().get(g.getVertexById(edge.getTo())), v);
                    }
                }
            }
            displayState(S, T, q);
        }
    }

    public static void displayState(ArrayList<Character> S, ArrayList<TreeEdge> T, PriorityQueue<Vertex<Character>> q) {
        PriorityQueue<Vertex<Character>> copyQ = new PriorityQueue<Vertex<Character>>(q); 
        System.out.print("S = {");
        for (int i = 0; i < S.size(); i++) {
            System.out.print(S.get(i) + ", ");
        }
        System.out.print("}\n");

        System.out.print("T = {");
        for (int i = 0; i < T.size(); i++) {
            System.out.print(T.get(i) + ", ");
        }
        System.out.print("}\n");
        
        System.out.print("Q = {");
        while (copyQ.peek() != null) {
            System.out.print(copyQ.poll().printShort());
        }
        System.out.print("}\n\n");
    }

    public static PriorityQueue<Vertex<Character>> BuildPQ(Graph<Character> g) {
        PriorityQueue<Vertex<Character>> q = new PriorityQueue<Vertex<Character>>();
        LinkedList<Vertex<Character>> vertices = g.getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            q.add(vertices.get(i));
        }
        return q;
    }

    public static Graph<Character> makeGraph() {
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

        return g;
    }

}

class TreeEdge {
    Character from;
    Character to;
    
    public TreeEdge(Character from, Character to) {
        this.from = from;
        this.to = to;
    }

    public Character getFrom() {
        return this.from;
    }

    public Character getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return "(" + getFrom() + ", " + getTo() + ")";
    }

}

class Graph<T> {
    LinkedList<Vertex<T>> vertices;


    public Graph() {
        this.vertices = new LinkedList<Vertex<T>>();
    }

    public void changeKey(Vertex<T> vertex, int newKey) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getId() == vertex.getId()){
                this.vertices.get(i).changeKey(newKey);;
            }
        }
    }

    public void changePred(Vertex<T> vertex, Vertex<T> newPred) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getId() == vertex.getId()){
                this.vertices.get(i).setPredecessor(newPred);;
            }
        }
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

    public LinkedList<Vertex<T>> getVertices() {
        return this.vertices;
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

class Vertex<T> implements Comparable<Vertex<T>>{
    T id;
    int keyVal;
    LinkedList<Edge<T>> edges;
    T pred;


    public Vertex(T id) {
        this.id = id;
        this.edges = new LinkedList<Edge<T>>();
        this.keyVal = Integer.MAX_VALUE;
    }

    public boolean addEdge(Edge<T> edge) {
        if (this.edges.contains(edge)) {
            return false;
        } else {
            this.edges.add(edge);
            this.pred = edge.getTo();
            return true;
        }
    }

    public void setPredecessor(Vertex<T> pred) {
        this.pred = pred.id;
    }

    public T getPredecessor() {
        return this.pred;
    }

    public T getId() {
        return this.id;
    }

    public int getKeyVal() {
        return this.keyVal;
    }

    public LinkedList<Edge<T>> getEdges() { 
        return this.edges;
    }

    public void changeKey(int newKey) {
        this.keyVal = newKey;
    }

    @Override
    public String toString() {
        String outputString = this.id + ": ";
        for (int i = 0; i < this.edges.size(); i++) {
            outputString += this.edges.get(i).toString() + ", "; 
        }
        return outputString;
    }

    public String printShort() {
        return "(" + this.getId() + ", " + this.getKeyVal() + "), ";
    }

    @Override
    public int compareTo(Vertex<T> otherVertex) {
        return this.getKeyVal() - otherVertex.getKeyVal();
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
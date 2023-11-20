
public class Node {
    int x;
    int y;
    int snakeNum = -1;
    boolean isObs = false;

    double gScore;  // Cost from the start node to this node
    double hScore;  // Heuristic estimate of the cost to the goal node
    double fScore;  // Total estimated cost (gScore + hScore)
    boolean isVisited;

    Node cameFrom;   // The node that came before this node in the path

    Node(int x, int y){
        this.x = x;
        this.y = y;
        gScore = Double.POSITIVE_INFINITY;  // Initialize to positive infinity
        hScore = 0;  // Initialize to 0
        fScore = Double.POSITIVE_INFINITY;  // Initialize to positive infinity
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", snakeNum=" + snakeNum +
                ", isObs=" + isObs +
                ", gScore=" + gScore +
                ", hScore=" + hScore +
                ", fScore=" + fScore +
                ", isVisited=" + isVisited +
                ", cameFrom=" + (cameFrom != null ? "(" + cameFrom.x + "," + cameFrom.y + ")" : "null") +
                '}';
    }

    public void setfScore() {
        this.fScore = this.gScore + this.hScore;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node otherNode = (Node) obj;
        return x == otherNode.x && y == otherNode.y;
    }
}
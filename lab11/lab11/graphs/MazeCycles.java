package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean _stop = false;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1, 1);
        t = maze.xyTo1D(maze.N(), maze.N());
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    @Override
    public void solve() {
        dfsSearchCircle(s);
    }

    // Helper methods go here
    private void dfsSearchCircle(int v) {
        marked[v] = true;
        announce();

        if (v == t) {
            _stop = true;
        }

        if (_stop) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                dfsSearchCircle(w);
                if (_stop) {
                    return;
                }
            } else {
                if (edgeTo[v] != w) {
                    edgeTo[w] = v;
                    announce();
                    _stop = true;
                    break;
                }
            }
        }
    }
}


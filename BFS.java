import java.util.*;

public class BFS implements Algorithm{

    public Coordinate find(int[][] map, int limit, Coordinate begin,Coordinate end){
        int[] directionX = {0, 1, -1, 0, 1, 1, -1, -1};
        int[] directionY = {1, 0, 0, -1, 1, -1, 1, -1};
        Queue<Coordinate> queue = new LinkedList<>();
        queue.offer(begin);
        Set<Coordinate> visited = new HashSet<>();
        visited.add(begin);
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                Coordinate cur = queue.poll();
                if (cur.equals(end)) {
                    return cur;
                }
                for (int i = 0; i < 8; i++) {
                    Coordinate adj = new Coordinate(cur.x + directionX[i], cur.y + directionY[i]);
                    if (!inBound(adj, map))
                        continue;
                    if (visited.contains(adj))
                        continue;
                    if (isSteep(cur, adj, limit, map))
                        continue;
                    adj.prev = cur;
                    visited.add(adj);
                    queue.offer(adj);
                }
            }
        }
        return null;
    }

    public Map<Coordinate,String> findAll(int[][] map, int limit, Coordinate begin,Set<Coordinate> end){
        int[] directionX = {0, 1, -1, 0, 1, 1, -1, -1};
        int[] directionY = {1, 0, 0, -1, 1, -1, 1, -1};
        Map<Coordinate,String> res = new HashMap<>();
        Queue<Coordinate> queue = new LinkedList<>();
        queue.offer(begin);
        Set<Coordinate> visited = new HashSet<>();
        visited.add(begin);
        while(!queue.isEmpty() && !end.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                Coordinate cur = queue.poll();
                if (end.contains(cur)) {
                    res.put(new Coordinate(cur),Coordinate.back(cur,begin));
                    end.remove(cur);
                    if(end.isEmpty())
                        return res;
                }
                for (int i = 0; i < 8; i++) {
                    Coordinate adj = new Coordinate(cur.x + directionX[i], cur.y + directionY[i]);
                    if (!inBound(adj, map))
                        continue;
                    if (visited.contains(adj))
                        continue;
                    if (isSteep(cur, adj, limit, map))
                        continue;
                    adj.prev = cur;
                    visited.add(adj);
                    queue.offer(adj);
                }
            }
        }
        return res;
    }

    public boolean inBound(Coordinate coor, int[][] map) {
        int n = map.length;
        int m = map[0].length;

        return coor.x >= 0 && coor.x < m && coor.y >= 0 && coor.y < n;
    }

    public boolean isSteep(Coordinate cur, Coordinate next, int limit, int[][] map){
        int len = Math.abs(map[cur.y][cur.x] - map[next.y][next.x]);
        return len > limit;
    }
}

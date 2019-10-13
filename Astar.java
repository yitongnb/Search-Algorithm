import java.util.*;

public class Astar implements Algorithm{

    public Coordinate find(int[][] map, int limit, Coordinate begin, Coordinate end){
        int[] directionX = {0, 1, -1, 0, 1, 1, -1, -1};
        int[] directionY = {1, 0, 0, -1, 1, -1, 1, -1};
        int[] distance = {10,10,10,10,14,14,14,14};
        int[][] heur = heuristic(map,end);
        PriorityQueue<Coordinate> queue = new PriorityQueue<Coordinate>(new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                if (o1.disAndHeur<o2.disAndHeur)
                    return -1;
                else if (o1.disAndHeur==o2.disAndHeur)
                    return 0;
                else
                    return 1;
            }
        });
        Map<Coordinate,Integer> closed = new HashMap<>();
        begin.disAndHeur = heur[begin.y][begin.x];
        queue.offer(begin);
        while(!queue.isEmpty()){
            Coordinate cur = queue.poll();
            if (cur.equals(end)) {
                System.out.println(cur.distance);
                return cur;
            }
            for(int i = 0; i<8; i++){
                Coordinate adj = new Coordinate(cur.x + directionX[i], cur.y + directionY[i]);
                if(!inBound(adj,map))
                    continue;
                if(isSteep(cur,adj,limit,map))
                    continue;
                adj.distance = cur.distance+distance[i]+Math.abs(map[cur.y][cur.x] - map[adj.y][adj.x]);
                adj.disAndHeur = adj.distance + heur[adj.y][adj.x];
                if(!queue.isEmpty() && queue.contains(adj)){ //is in the open
                    for(Coordinate c : queue){ // if this coordinates already in queue, compare the distance, remove larger one
                        if(c.equals(adj)){// equals to be override methods here
                            if(c.disAndHeur > adj.disAndHeur){
                                queue.remove(c);
                                c.distance = adj.distance;
                                c.disAndHeur = adj.disAndHeur;
                                c.prev = cur;
                                queue.offer(c);
                            }
                            break;
                        }
                    }
                }else if(!closed.isEmpty() && closed.containsKey(adj)){ //is in the close
                    if(adj.disAndHeur < closed.get(adj) ){
                        closed.remove(adj);
                        adj.prev = cur;
                        queue.offer(adj);
                    }
                }else{
                    adj.prev = cur;
                    queue.offer(adj);
                }
            }
            closed.put(cur,cur.disAndHeur);
        }
        return null;
    }


    public Map<Coordinate, String> findAll(int[][] map, int limit, Coordinate begin, Set<Coordinate> end) {
        return null;
    }

    private int[][] heuristic(int map[][], Coordinate destin){
        int n = map.length;
        int m = map[0].length;
        int[][] res = new int[n][m];
        int x = destin.x;
        int y = destin.y;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<m; j++){
                res[i][j] = cal(Math.abs(y-i),Math.abs(x-j)) + Math.abs(map[y][x] - map[i][j]);
            }
        }
        return res;
    }

    public boolean inBound(Coordinate coor, int[][] map) {
        int n = map.length; //height
        int m = map[0].length; //width

        return coor.x >= 0 && coor.x < m && coor.y >= 0 && coor.y < n;
    }

    public boolean isSteep(Coordinate cur, Coordinate next, int limit, int[][] map){
        int len = Math.abs(map[cur.y][cur.x] - map[next.y][next.x]);
        return len > limit;
    }

    private int cal(int x, int y){
        return Math.min(x,y)*14 + Math.abs(x-y)*10;
    }
}

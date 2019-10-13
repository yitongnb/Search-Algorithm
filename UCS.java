import java.util.*;

public class UCS implements Algorithm{

    public Coordinate find(int[][] map, int limit, Coordinate begin, Coordinate end){
        int[] directionX = {0, 1, -1, 0, 1, 1, -1, -1};
        int[] directionY = {1, 0, 0, -1, 1, -1, 1, -1};
        int[] distance = {10,10,10,10,14,14,14,14};
        PriorityQueue<Coordinate> queue = new PriorityQueue<Coordinate>(new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                if (o1.distance<o2.distance)
                    return -1;
                else if (o1.distance==o2.distance)
                    return 0;
                else
                    return 1;
            }
        });
        Map<Coordinate,Integer> closed = new HashMap<>();
        queue.offer(begin);
        while(!queue.isEmpty()){
            Coordinate cur = queue.poll();
            if (cur.equals(end)) {
                System.out.println(cur.distance);
                return cur;
            }
            for(int i = 0; i<8; i++){
                Coordinate adj = new Coordinate(cur.x + directionX[i], cur.y + directionY[i], cur.distance+distance[i]);
                if(!inBound(adj,map))
                    continue;
                if(isSteep(cur,adj,limit,map))
                    continue;
                if(!queue.isEmpty() && queue.contains(adj)){
                    for(Coordinate c : queue){ // if this coordinates already in queue, compare the distance, remove larger one
                        if(c.equals(adj)) { // equals to be override methods here
                            if(c.distance > adj.distance){
                                queue.remove(c);
                                c.distance = adj.distance;
                                c.prev = cur;
                                queue.offer(c);
                            }
                            break;
                        }
                    }
                }else if(!closed.isEmpty() && closed.containsKey(adj)){
                    if(adj.distance < closed.get(adj) ){
                        closed.remove(adj);
                        adj.prev = cur;
                        queue.offer(adj);
                    }
                }
                else{
                    adj.prev = cur;
                    queue.offer(adj);
                }
            }
            closed.put(cur,cur.distance);
        }
        return null;
    }

    public Map<Coordinate,String> findAll(int[][] map, int limit, Coordinate begin, Set<Coordinate> end){
        int[] directionX = {0, 1, -1, 0, 1, 1, -1, -1};
        int[] directionY = {1, 0, 0, -1, 1, -1, 1, -1};
        int[] distance = {10,10,10,10,14,14,14,14};
        Map<Coordinate,String> res = new HashMap<>();
        PriorityQueue<Coordinate> queue = new PriorityQueue<Coordinate>(new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                if (o1.distance<o2.distance)
                    return -1;
                else if (o1.distance==o2.distance)
                    return 0;
                else
                    return 1;
            }
        });
        Map<Coordinate,Integer> closed = new HashMap<>();
        queue.offer(begin);
        while(!queue.isEmpty() && !end.isEmpty()){
            Coordinate cur = queue.poll();
            if (end.contains(cur)) {
                res.put(new Coordinate(cur),Coordinate.back(cur,begin));
                end.remove(cur);
                if(end.isEmpty())
                    return res;
            }
            for(int i = 0; i<8; i++){
                Coordinate adj = new Coordinate(cur.x + directionX[i], cur.y + directionY[i], cur.distance+distance[i]);
                if(!inBound(adj,map))
                    continue;
                if(isSteep(cur,adj,limit,map))
                    continue;
                if(!queue.isEmpty() && queue.contains(adj)){
                    for(Coordinate c : queue){ // if this coordinates already in queue, compare the distance, remove larger one
                        if(c.equals(adj)) { // equals to be override methods here
                            if(c.distance > adj.distance){
                                queue.remove(c);
                                c.distance = adj.distance;
                                c.prev = cur;
                                queue.offer(c);
                            }
                            break;
                        }
                    }
                }else if(!closed.isEmpty() && closed.containsKey(adj)){
                    if(adj.distance < closed.get(adj) ){
                        closed.remove(adj);
                        adj.prev = cur;
                        queue.offer(adj);
                    }
                }
                else{
                    adj.prev = cur;
                    queue.offer(adj);
                }
            }
            closed.put(cur,cur.distance);
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
}

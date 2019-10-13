import java.util.Map;
import java.util.Set;

public interface Algorithm {

    Coordinate find(int[][] map, int limit, Coordinate begin,Coordinate end);

    Map<Coordinate,String> findAll(int[][] map, int limit, Coordinate begin, Set<Coordinate> end);

    boolean inBound(Coordinate coor, int[][] map);

    boolean isSteep(Coordinate cur, Coordinate next, int limit, int[][] map);
}


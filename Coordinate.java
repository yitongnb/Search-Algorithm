

public class Coordinate {
    int x,y;

    Coordinate prev;

    int distance;

    int disAndHeur;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public Coordinate(Coordinate c){
        this.x = c.x;
        this.y = c.y;
        this.disAndHeur = c.disAndHeur;
        this.distance = c.distance;
        this.prev = c.prev;
    }

    public static String back(Coordinate end, Coordinate begin){
        if(end == null){
            return "FAIL";
        }
        StringBuilder sb = new StringBuilder();
        Coordinate c = end;
        String res = "";
        while(!c.equals(begin)){
            res = " " + c.x +","+ c.y +res;
            c = c.prev;
        }
        res = c.x+","+c.y+res;
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }

        if(obj == null){
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }

        if(obj instanceof Coordinate){
            Coordinate other = (Coordinate) obj;
            //需要比较的字段相等，则这两个对象相等
            if(this.x == other.x && this.y == other.y){
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31*result + this.x;
        result = 31*result +this.y;
        return result;
    }
}

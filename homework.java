import java.io.*;
import java.util.*;

public class homework {


    public static void main(String args[]) throws IOException{


        File f=new File("src/input.txt");
        BufferedReader reader=new BufferedReader(new FileReader(f));
        String temp=null;
        String type = null;
        int line = 1;
        int num = 0;
        int width=0,height=0;
        int limit=0;
        int[][] map = {{}};
        Coordinate begin = null;
        List<Coordinate> endList = new ArrayList<>();
        Set<Coordinate> endSet = new HashSet<>();
        while((temp=reader.readLine())!=null){
            if(line == 1){
                type = temp;
            }else if(line == 2){
                String[] strs = temp.split(" ");
                width = Integer.parseInt(strs[0]);
                height = Integer.parseInt(strs[1]);
                map = new int[height][width];
            }else if(line == 3){
                String[] strs = temp.split(" ");
                begin = new Coordinate(Integer.parseInt(strs[0]),Integer.parseInt(strs[1]));
            }else if(line == 4){
                limit = Integer.parseInt(temp);
            }else if(line == 5){
                num = Integer.parseInt(temp);
            }else if(line<=num+5 && line>5){
                String[] strs = temp.split(" ");
                endList.add(new Coordinate(Integer.parseInt(strs[0]),Integer.parseInt(strs[1])));
                endSet.add(new Coordinate(Integer.parseInt(strs[0]),Integer.parseInt(strs[1])));
            }else{
                String[] strs = temp.split(" ");
                for(int i = 0; i<width; i++){
                    map[line-num-6][i] = Integer.parseInt(strs[i]);
                }
            }
            line++;
        }
        reader.close();

        Algorithm algorithm;
        if(type.equals("BFS")){
            algorithm = new BFS();
        }else if(type.equals("UCS")){
            algorithm = new UCS();
        }else{
            algorithm = new Astar();
        }
        List<String> res = new ArrayList<>();

        long startTime=System.currentTimeMillis();
        if(type.equals("BFS") || type.equals("UCS")){
            Map<Coordinate,String> findT = new HashMap<>(algorithm.findAll(map,limit,begin,endSet));
            for(Coordinate end : endList){
                res.add(findT.getOrDefault(end,"FAIL"));
            }
        }else{
            for(Coordinate end : endList){
                String str = Coordinate.back(algorithm.find(map,limit,begin,end),begin);
                res.add(str);
            }
        }
        for(Coordinate end : endList){
            String str = Coordinate.back(algorithm.find(map,limit,begin,end),begin);
            res.add(str);
        }

        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

        String path = "src/output.txt";
        File file = new File(path);
        if(file.exists()){
            file.delete();
            file.createNewFile();
        }else{
            file.createNewFile();
        }
        // write
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0; i<res.size(); i++){
            if(i != 0)
                bw.newLine();
            bw.write(res.get(i));
        }
        bw.flush();
        bw.close();
        fw.close();

    }
}

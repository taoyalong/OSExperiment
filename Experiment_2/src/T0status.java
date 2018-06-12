import java.util.ArrayList;
import java.util.List;

public class T0status {
    protected int[] source;
    protected int[][] max;
    protected int[][] allocation;
    protected int[][] need;
    protected int[] available;
    protected List<PCB> process = new ArrayList<PCB>();

    T0status(){
        this.source = new int[]{10,5,7};
        this.max = new int[][]{
                { 7, 5, 3 },
                { 3, 2, 2 },
                { 9, 0, 2 },
                { 2, 2, 2 },
                { 4, 3, 3 }, };
        this.allocation = new int[][] {
                { 0, 1, 0 },
                { 2, 0, 0 },
                { 3, 0, 2 },
                { 2, 1, 1 },
                { 0, 0, 2 }, };
        this.need = new int[max.length][source.length];
        this.available = new int[source.length];
        for (int i = 0; i < 3; i++){
            available[i] = source[i] - allocation[0][i] - allocation[1][i] - allocation[2][i] - allocation[3][i] - allocation[4][i];
        }
        this.need = new int[][]{
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 }, };
        // for (int i = 0; i < 5; i++){
        //     for (int j = 0; j < 3; j++){
        //         need[i][j] = max[i][j] - allocation[i][j];
        //     }
        // }
        for(int i = 0;i < 5;i++){
            process.add(new PCB("P"+String.valueOf(i),'w',max[i],allocation[i]));
        }
    }

}

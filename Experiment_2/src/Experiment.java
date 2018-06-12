import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Experiment {
    private system sys = new system("example");
    private int algorithm;

    public void choice(){
        sys.show();
        System.out.println("是否采用银行家算法？【yes/no】:");
        Scanner in = new Scanner(System.in);
        while(true){
            String choose = in.nextLine();
            if (choose.equals("y") || choose.equals("yes") ){
                this.algorithm = 1;
                return;
            }else if (choose.equals("n") || choose.equals("no")){
                this.algorithm = 0;
                return;
            }
            else {
                System.out.println("请输入yes（y）或no（n）");
            }
        }
    }

    public void apply(){
        try {//安全读取输入
            while(true) {
                System.out.println("请输入有多少个进程要申请资源（最高为5，输入其他字符退出）：");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String str = in.readLine();
                switch (str) {
                    case "1": case "2": case "3": case "4": case "5":
                        if (inputrequest(Integer.parseInt(str))) return;
                        else continue;
                    default:
                        System.out.println("退出");
                        System.exit(0);
                }
            }
        }catch (NumberFormatException e){
            System.out.println("异常退出");
            System.exit(0);
        }catch (IOException ie){
            System.out.println("异常退出");
            System.exit(0);
        }
    }

    public boolean inputrequest(int n){
        System.out.println("当前有"+n+"个进程要申请资源");
        int[] curr = new int[n];
        int[][] asource = new int[n][3];
        for(int i = 0;i < n;i++){
            System.out.println("请指定第"+(i+1)+"个要申请资源的进程名：（输入0-4）");
            Scanner in = new Scanner(System.in);
            curr[i] = in.nextInt();
            System.out.println("请依次输入进程"+(curr[i])+"要申请的各类资源数量：");
            System.out.println("A类资源：");
            asource[i][0] = in.nextInt();
            System.out.println("B类资源：");
            asource[i][1] = in.nextInt();
            System.out.println("C类资源：");
            asource[i][2] = in.nextInt();
        }
        int count[] = new int[3];//统计对三类资源的总申请量
        for(int i = 0;i<3;i++){
            for(int j = 0;j<n;j++){
                count[i] = asource[j][i];
            }
        }
        if(sys.check(count)){
            for(int i = 0;i<curr.length;i++){
                sys.opwPCB(1,"P"+String.valueOf(curr[i]),asource[i]);
                sys.opwPCB("P"+String.valueOf(curr[i]),'r');
            }
            return true;
        }else {
            System.out.println("输入数据有错，请重新输入（超过系统可用资源上限）");
            return false;
        }
    }



    public void exec(){
        if(this.algorithm == 1){
            System.out.println("正在使用银行家算法...");
            if(sys.BankerAlgorithm()){
                System.out.println("成功执行银行家算法，未发生死锁");
            }else {
                System.out.println("未检测到安全序列，不能申请");
            }
        }else {
            System.out.println("正在使用随机分配算法...");
            if(sys.RandomAlgorithm()){
                System.out.println("随机分配算法没有产生死锁");
            }else{
                System.out.println("随机分配算法产生死锁");
            }

        }
    }


    // public static void Show(int p) {
    //     if (p == -1) {

    //         }
    //     }else {
    //         if (flag == 0) {
    //             System.out.println("---------------------请求资源后分配情况--------------------------");
    //             System.out.println("         Work          Need       Allocation  Work+Allocation  Finish");
    //             System.out.println("       A   B   C     A   B   C     A   B   C     A   B   C");
    //             flag = 1;
    //         }
    //         if (flag == 1) {
    //             System.out.print("P["+p+"]   ");
    //             for (int j = 0; j < 3; j++)
    //                 System.out.print(_Work[p][j] + "   ");
    //             System.out.print("  ");
    //             for (int j = 0; j < 3; j++)
    //                 System.out.print(Need[p][j] + "   ");
    //             System.out.print("  ");
    //             for (int j = 0; j < 3; j++)
    //                 System.out.print(Allocation[p][j] + "   ");
    //             System.out.print("  ");
    //             for (int j = 0; j < 3; j++)
    //                 System.out.print((_Work[p][j]+Allocation[p][j]) + "   ");
    //             System.out.print("    "+Finish[p]);
    //             System.out.println();
    //         }
    //     }
    // }


    public static void main(String[] args){
        System.out.println("--------------------------");
        System.out.println("------实验二：资源分配------");
        System.out.println("---------软信1603----------");
        System.out.println("----------薛晨阳-----------");
        System.out.println("---------20163754---------");
        System.out.println("--------------------------\n");

        Experiment exp = new Experiment();
        exp.apply();
        exp.choice();
        exp.exec();

        System.out.println("实验结束");
    }
}

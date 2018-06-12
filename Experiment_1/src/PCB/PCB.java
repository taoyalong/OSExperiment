package PCB;

import java.util.Scanner;

public class PCB {
    private String name = new String();//进程名
    private int ntime = 0;//要求运行时间
    private int atime = 0;//已运行时间
    private char status = 'w';//状态


    public PCB(String n){//初始化进程信息
        this.name = n;
        Scanner in = new Scanner(System.in);
        System.out.println("请输入要求运行时间：");
        this.ntime = in.nextInt();
    }

    public void setStatus(char stat){//设置状态
        this.status = stat;
    }

    public String getName(){//获取进程名
        return this.name;
    }

    public int getNtime(){//获取要求运行时间
        return this.ntime;
    }

    public int getAtime(){//获取已运行时间
        return this.atime;
    }

    public char getStatus(){//获取状态
        return this.status;
    }

    public void changeAtime(){//已运行时间加1
        this.atime++;
    }

    public void show(){//展示PCB信息
        System.out.print(this.getName()+"\t\t");
        System.out.print(this.getStatus()+"\t\t");
        System.out.print(this.getNtime()+"\t\t");
        System.out.print(this.getAtime()+"\t\t");
    }
}
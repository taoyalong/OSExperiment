package simpleroundrobin;

import PCB.PCB;
import method.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class simpleroundrobin extends method{
    private List<PCB> pc = new ArrayList<PCB>();//数组列表容器：用来存储所有的PCB，先来的占小序号
    private List<PCB> l = new ArrayList<PCB>();//临时保存已结束进程
    private List<PCB> ll = new ArrayList<PCB>();//临时保存当前执行进程
    private PCB currentp;//当前进程

    public void setPc(PCB p){//提供向数组列表容器添加进程的接口
        this.pc.add(p);
    }

    @Override
    public void show(){//展示当前所有进程信息
        if(!ll.isEmpty()) {
            System.out.println("当前执行进程：");
            System.out.println("进程名   状态  需运行   已运行");
            currentp.show();
            System.out.println();
        }

        if(!pc.isEmpty()){
            System.out.println("当前就绪进程：");
            System.out.println("进程名   状态  需运行   已运行");
            for(Iterator it = pc.iterator();it.hasNext();){//利用迭代器依次输出储存的所有对象
                PCB o = (PCB)it.next();
                o.show();
                System.out.println();

            }
        }

        if(!l.isEmpty()){
            System.out.println("当前已结束进程：");
            System.out.println("进程名  状态  需运行   已运行");
            for(Iterator it = l.iterator();it.hasNext();){//利用迭代器依次输出储存的所有对象
                PCB o = (PCB)it.next();
                o.show();
                System.out.println();

            }
        }

        this.goon();
    }

    public void simpleroundrobinalgorithm(){//简单轮转法算法
        System.out.println("----------初始状态----------");
        show();

        while (!pc.isEmpty()){//如果列表不为空
            currentp = pc.get(0);//取第一个进程
            pc.remove(0);//从列表中移除第一个进程
            currentp.setStatus('r');//置取出进程的状态为“运行”
            ll.add(currentp);//加入当前执行进程
            currentp.changeAtime();//已运行加1
            show();//展示当前所有进程状态
            ll.clear();//清除当前执行进程
            if(currentp.getNtime()==currentp.getAtime()) {//如果已运行等于需运行
                currentp.setStatus('e');//状态置为“结束”
                l.add(currentp);//加入当前已结束进程
                continue;//直接处理下一个进程
            }
            currentp.setStatus('w');//如果不是则置取出进程的状态为“等待”
            pc.add(pc.size(),currentp);//将取出进程加到列表尾部
        }
        show();//最终状态
    }
}


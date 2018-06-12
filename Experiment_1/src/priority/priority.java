package priority;

import PCB.pPCB;
import method.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class priority extends method {
    private Queue<pPCB> pc = new PriorityQueue<pPCB>();//优先队列容器：用来存储所有带优先级的PCB，永远是降序排列的
    private List<pPCB> l = new ArrayList<pPCB>();//临时保存已结束进程
    private List<pPCB> ll = new ArrayList<pPCB>();//临时保存当前执行进程
    private pPCB currentp;//当前进程


    public void setPc(pPCB p) {//提供向优先队列容器添加进程的接口
        this.pc.add(p);
    }

    @Override
    public void show(){//展示当前所有进程信息

        if(ll.size() != 0){
            System.out.println("当前执行进程：");
            System.out.println("进程名   状态  需运行   已运行   优先级");
            currentp.show();
        }

        if(pc.size() != 0){
            System.out.println("当前就绪进程：");
            System.out.println("进程名   状态  需运行   已运行   优先级");
            for(Iterator it = pc.iterator();it.hasNext();){//利用迭代器依次输出储存的所有对象
                pPCB o = (pPCB)it.next();
                o.show();
            }
        }

        if(l.size() != 0){
            System.out.println("当前已结束进程：");
            System.out.println("进程名   状态  需运行   已运行   优先级");
            for(Iterator it = l.iterator();it.hasNext();){//利用迭代器依次输出储存的所有对象
                pPCB o = (pPCB)it.next();
                o.show();
            }
        }
        this.goon();

    }

    public void priorityalgorithm(){//最高优先级优先算法
        System.out.println("----------初始状态----------");
        show();

        while (pc.size() !=0 ){//当优先队列不为空时
            currentp = pc.poll();//将队首进程取出
            currentp.setStatus('r');//状态置为“运行”
            ll.add(currentp);//加入当前执行进程
            currentp.changeAtime();//已运行加1
            if(currentp.getPriority() != 0) {//若优先级不为0则减1
                currentp.changePriority();
            }
            show();//展示当前所有进程状态
            if(currentp.getNtime() == currentp.getAtime()){//如果已运行等于需运行
                currentp.setStatus('e');//状态置为“结束”
                l.add(currentp);//加入当前已结束进程
            }else{
                currentp.setStatus('w');//状态置为“就绪”
                pc.add(currentp);//重新送入优先队列
            }
            ll.clear();//清除当前执行进程
        }

        show();//最终状态
    }
}

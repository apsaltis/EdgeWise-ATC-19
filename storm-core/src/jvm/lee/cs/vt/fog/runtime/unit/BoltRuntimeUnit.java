package lee.cs.vt.fog.runtime.unit;

import lee.cs.vt.fog.runtime.misc.BoltReceiveDisruptorQueue;
import lee.cs.vt.fog.runtime.misc.ExecutorCallback;
import org.apache.storm.utils.DisruptorQueue;

public class BoltRuntimeUnit extends RuntimeUnit{
    private final String componentId;
    private final BoltReceiveDisruptorQueue queue;

    public BoltRuntimeUnit(String componentId,
                           BoltReceiveDisruptorQueue queue,
                           ExecutorCallback callback) {
        super(callback);
        assert(callback.getType() == ExecutorCallback.ExecutorType.bolt);

        this.componentId = componentId;
        this.queue = queue;
    }

    public String getComponentId() {
        return componentId;
    }

    public String getName() {
        return componentId + "," + queue.getName();
    }

    @Override
    public String toString() {
        return getName();
    }

    public long getNumInQ() {
        DisruptorQueue.QueueMetrics m = queue.getMetrics();
        return m.population();
    }

    public void setWaitStartTime() {
        queue.setWaitStartTime();
    }

    public void addWaitTime() {
        queue.addWaitTime();
    }

    public void setEmptyStartTime() {
        queue.setEmptyStartTime();
    }

    public String printAverageWaitTime() {
        long totalTupleConsumed = queue.getTotalTupleConsumed();
        long totalWaitTime = queue.getTotalWaitTime();
        double averageWaitTime = totalWaitTime / (double) totalTupleConsumed;

        String ret = "";
        ret += componentId + ",";
        ret += queue.getName() + ",";
        ret += totalTupleConsumed + ",";
        ret += totalWaitTime + ",";
        ret += averageWaitTime + "\n";
        return ret;
    }

    public String printTotalEmptyTime() {
        long totalTupleConsumed = queue.getTotalTupleConsumed();
        long totalEmptyTime = queue.getTotalEmptyTime();
        double averageEmptyTime = totalEmptyTime / (double) totalTupleConsumed;

        String ret = "";
        ret += componentId + ",";
        ret += queue.getName() + ",";
        ret += totalTupleConsumed + ",";
        ret += totalEmptyTime + ",";
        ret += averageEmptyTime + "\n";
        return ret;
    }

    public void print() {
        System.out.println("BoltRuntimeUnit componentId:" + componentId + " queue:" + queue.getName());
    }
}

import java.util.PriorityQueue;

import Model.Direction;
import Model.Location;
import Model.Request;

/**
 * Elevator
 */
public class Elevator {
    int currentFloor;
    Direction direction;
    PriorityQueue<Request> upQueue;
    PriorityQueue<Request> downQueue;

    public Elevator(int currentFloor) {
        this.currentFloor = currentFloor;
        this.direction = Direction.IDLE;

        upQueue = new PriorityQueue<>((a, b) -> a.desiredFloor - b.desiredFloor);
        downQueue = new PriorityQueue<>((a, b) -> b.desiredFloor - a.desiredFloor);
    }

    public void sendUpRequest(Request upRequest) {
        if (upRequest.location == Location.OUTSIDE_ELEVATOR) {
            upQueue.offer(new Request(upRequest.currentFloor, upRequest.currentFloor, Direction.UP, Location.OUTSIDE_ELEVATOR));
            System.out.println("Append up request going to floor " + upRequest.currentFloor + ".");
        }
        upQueue.offer(upRequest);
    }

    public void sendDownRequest(Request downRequest) {
        if (downRequest.location == Location.OUTSIDE_ELEVATOR) {
            downQueue.offer(new Request(downRequest.currentFloor, downRequest.currentFloor, Direction.DOWN, Location.OUTSIDE_ELEVATOR));
            System.out.println("Append down request going to floor " + downRequest.currentFloor + ".");
        }
        downQueue.offer(downRequest);
    }

    public void run() {
        while (!upQueue.isEmpty() || !downQueue.isEmpty()) {
            processRequests();
        }
        System.out.println("Finished all requests. Bye!");
        this.direction = Direction.IDLE;
    }

    private void processRequests() {
        if (this.direction == Direction.UP || this.direction == Direction.IDLE) {
            processUpRequest();
            processDownRequest();
        } else {
            processDownRequest();
            processUpRequest();
        }
    }

    private void processUpRequest() {
        while (!upQueue.isEmpty()) {
            Request upRequest = upQueue.poll();
            this.currentFloor = upRequest.desiredFloor;
            System.out.println("Processing up requests. Elevator stopped at floor " + this.currentFloor + ".");
        }
        if (!downQueue.isEmpty())
            this.direction = Direction.DOWN;
        else
            this.direction = Direction.IDLE;
    }

    private void processDownRequest() {
        while (!downQueue.isEmpty()) {
            Request downRequest = downQueue.poll();
            this.currentFloor = downRequest.desiredFloor;
            System.out.println("Processing up requests. Elevator stopped at floor " + this.currentFloor + ".");
        }
        if (!upQueue.isEmpty())
            this.direction = Direction.UP;
        else
            this.direction = Direction.IDLE;
    }
}
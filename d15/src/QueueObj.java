public class QueueObj {
    int distance;
    Pointt firstMove;
    Pointt currentPoint;

    QueueObj(Pointt currentPoint, int distance, Pointt firstMove) {
        this.currentPoint = currentPoint;
        this.distance = distance;
        this.firstMove = firstMove;
    }
}

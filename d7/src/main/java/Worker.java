class Worker {
    private char currentTask;
    private int  secondsLeft;

    Worker() {
        currentTask = 0;
        secondsLeft = 0;
    }

    void startWork(char task) {
        secondsLeft = task - 64 + 60;
        setTask(task);
        //a second when he takes task also counts
        secondsLeft--;
    }

    private void setTask(char task) {
        currentTask = task;
    }

    void work() {
        secondsLeft--;
    }

    void stopWork() {
        currentTask = 0;
        secondsLeft = 0;
    }

    char getCurrentTask() {
        return currentTask;
    }

    int getSecondsLeft() {
        return secondsLeft;
    }
}

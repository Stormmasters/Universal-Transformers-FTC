package pedroPathing.utils.runnables;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.PathChain;

import pedroPathing.utils.functions.Logger;

public class AutonChassisRunnable implements Runnable{
    private Follower follower;
    private PathChain path, newPath;
    private boolean stopThread = false, newPathCreated;
    public AutonChassisRunnable(Follower follower, PathChain path){
        this.follower = follower;
        this.path = path;
    }
    public void run(){
        newPath = path;
        while (!Thread.currentThread().isInterrupted() && !stopThread){
            if (path != newPath || newPathCreated){
                path = newPath;
                follower.followPath(path);
                newPathCreated = false;
            }
            follower.update();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Logger.warn("Interrupted during PID loop.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    public void updatePath(PathChain path){
        this.newPath = path;
        this.newPathCreated = true;
    }
    public void stop(){
        stopThread = true;
    }
}
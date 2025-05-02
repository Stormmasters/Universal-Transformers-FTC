package pedroPathing.code;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import pedroPathing.utils.functions.ChassisController;
import pedroPathing.utils.runnables.AutonChassisRunnable;
import pedroPathing.utils.runnables.SlidesRunnable;

@Autonomous(name = "FraserAuton")
public class FraserAuton extends LinearOpMode {
    private Follower follower;
    private AutonChassisRunnable pPathPID;
    private Thread pPathThread;
    private PathChain circle;
    private double RADIUS = 10;
    @Override
    public void runOpMode(){
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        // Using path copied from pP/examples
        circle = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(0,0, Point.CARTESIAN), new Point(RADIUS,0, Point.CARTESIAN), new Point(RADIUS, RADIUS, Point.CARTESIAN)))
                .addPath(new BezierCurve(new Point(RADIUS, RADIUS, Point.CARTESIAN), new Point(RADIUS,2*RADIUS, Point.CARTESIAN), new Point(0,2*RADIUS, Point.CARTESIAN)))
                .addPath(new BezierCurve(new Point(0,2*RADIUS, Point.CARTESIAN), new Point(-RADIUS,2*RADIUS, Point.CARTESIAN), new Point(-RADIUS, RADIUS, Point.CARTESIAN)))
                .addPath(new BezierCurve(new Point(-RADIUS, RADIUS, Point.CARTESIAN), new Point(-RADIUS,0, Point.CARTESIAN), new Point(0,0, Point.CARTESIAN)))
                .build();
        pPathPID = new AutonChassisRunnable(follower, circle);
        pPathThread = new Thread(pPathThread, "PedroPathingThread");
        waitForStart();
        pPathThread.start();
    }
}
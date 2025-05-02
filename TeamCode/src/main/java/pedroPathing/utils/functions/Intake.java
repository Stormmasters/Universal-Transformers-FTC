package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import pedroPathing.utils.runnables.SlidesRunnable;

public class Intake {
    private boolean isExtended = false, isInitialized = false;
    DcMotorEx intakeSlide, intake;
    private SlidesRunnable intakePID;
    private Thread intakeThread;
    private double targetPosition;
    public boolean initialize(HardwareMap hardwareMap){
        if (!isInitialized){
            Logger.info("Init started");
            intakeSlide = hardwareMap.get(DcMotorEx.class, "EM");
            intakeSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            intakeSlide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            intakeSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
            intakeSlide.setDirection(DcMotorSimple.Direction.REVERSE);
            isInitialized = true;
            intakePID = new SlidesRunnable(intakeSlide, 0, 0.01, 0.00003, 0.0008, true, 0.8);
            intakeThread = new Thread(intakePID);
            intakeThread.start();
            intakePID.setMaxPower(0.7);
            Logger.info("Successfully initialized");
            return true;
        }
        else {
            Logger.error("Failed to init; Already initialized");
            return false;
        }
    }
    public boolean extend() {
        if (isInitialized && !isExtended){
            Logger.info("Extending slides...");
            intakePID.setTarget(200);
            isExtended = true;
            return true;
        }
        else if (!isInitialized){
            Logger.error("Failed to extend slides; Slides not initialized");
            return false;
        }
        else {
            Logger.error("Failed to extend slides; already extended");
            return false;
        }
    }
    public boolean retract() {
        if (isInitialized && isExtended){
            Logger.info("Retracting slides...");
            intakePID.setTarget(40);
            isExtended = false;
            return true;
        }
        else if (!isInitialized){
            Logger.error("Failed to retract slides; Slides not initialized");
            return false;
        }
        else {
            Logger.error("Failed to retract slides; already retracted");
            return false;
        }
    }
    public boolean isExtended() {
        return isExtended;
    }
    public double getTargetPosition(){
        return intakePID.getTargetPosiion();
    }
    public void stopIntakeThread() {
        Logger.info("Terminating intake thread...");
        if (intakeThread != null) {
            intakeThread.interrupt();
            intakeThread = null;
            Logger.info("Successfully terminated intakeThread");
        } else {
            Logger.warn("intakeThread is null, aborting termination");
        }
    }
}
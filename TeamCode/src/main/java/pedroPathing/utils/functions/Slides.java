package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import pedroPathing.utils.runnables.SlidesRunnable;

public class Slides {
    private DcMotorEx slideMotor = null;
    private boolean isExtended = false, isInitialized = false;
    private Thread slideThread, armThread;
    private SlidesRunnable slidePID;
    private double armRetracted = 0, armExtended = 1;
    ServoImplEx arm;
    public boolean isExtended(){
        return isExtended;
    }
    public boolean initialize(HardwareMap hardwareMap){
        if (!isInitialized){
            Logger.info("Init started");
            slideMotor = hardwareMap.get(DcMotorEx.class, "LM");
            slideMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            slideMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            slideMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
            arm = hardwareMap.get(ServoImplEx.class, "arm");
            isInitialized = true;
            slidePID = new SlidesRunnable(slideMotor, 0, 0.005, 0.00001, 0.0005, false);
            slideThread = new Thread(slidePID, "SlidePIDThread");
            slideThread.start();
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
            slidePID.setTarget(500);
            arm.setPosition(armExtended);
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
            slidePID.setTarget(0);
            arm.setPosition(armRetracted);
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
    public void stopSlideThreads(){
        Logger.info("Terminating slide threads...");
        if (slideThread != null) {
            slideThread.interrupt();
            slideThread = null;
            Logger.info("Successfully terminated slideThread");
        }
        else {
            Logger.warn("slideThread is null, aborting termination");
        }
        if (armThread != null) {
            armThread.interrupt();
            armThread = null;
            Logger.info("Successfully terminated armThread");
        }
        else {
            Logger.warn("armThread is null, aborting termination");
        }
    }
}
package pedroPathing.utils.functions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import pedroPathing.utils.runnables.SlidesRunnable;

public class Intake {
    private boolean isExtended = false, isInitialized = false;
    DcMotorEx intakeSlide, intake;
    private SlidesRunnable intakePID;
    private Thread intakeThread;
    public boolean initialize(HardwareMap hardwareMap){
        if (!isInitialized){
            Logger.info("Init started");
            intakeSlide = hardwareMap.get(DcMotorEx.class, "intakeSlide");
            intakeSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            intakeSlide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            intakeSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
            intake = hardwareMap.get(DcMotorEx.class, "intake");
            intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            intake.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
            isInitialized = true;
            intakePID = new SlidesRunnable(intakeSlide, 0, 0.005, 0.00001, 0.0005, true);
            intakeThread = new Thread(intakePID);
            intakeThread.start();
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
            intakePID.setTarget(400);
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
            intakePID.setTarget(0);
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
}
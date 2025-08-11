package pedroPathing.utils.functions;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

import pedroPathing.constants.HardwareConstants;

public class RobotHardware {
    public final List<LynxModule> hubs;

    public final DcMotorEx intakeMotor;
    public final DcMotorEx extensionMotor;
    public final DcMotorEx liftMotor1, liftMotor2;
    public final DcMotorEx FL, BL, FR, BR;
    public final Servo IS1, IS2;
    public RobotHardware(HardwareMap hardwareMap) {
        hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : hubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        // Intake motor
        intakeMotor = hardwareMap.get(DcMotorEx.class, HardwareConstants.intakeMotor);
        intakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        // Extension motor
        extensionMotor = hardwareMap.get(DcMotorEx.class, HardwareConstants.extensionMotor);
        extensionMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Lift motors
        liftMotor1 = hardwareMap.get(DcMotorEx.class, HardwareConstants.liftMotor1);
        liftMotor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        liftMotor2 = hardwareMap.get(DcMotorEx.class, HardwareConstants.liftMotor2);
        liftMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Drive motors
        FL = hardwareMap.get(DcMotorEx.class, HardwareConstants.frontLeftMotor);
        BL = hardwareMap.get(DcMotorEx.class, HardwareConstants.backLeftMotor);
        FR = hardwareMap.get(DcMotorEx.class, HardwareConstants.frontRightMotor);
        BR = hardwareMap.get(DcMotorEx.class, HardwareConstants.backRightMotor);

        IS1 = hardwareMap.get(Servo.class, "IS1");
        IS2 = hardwareMap.get(Servo.class, "IS2");
    }

    public void clearBulkCache() {
        for (LynxModule module : hubs) {
            module.clearBulkCache();
        }
    }
}
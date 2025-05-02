package pedroPathing.code;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.List;

import pedroPathing.utils.functions.ChassisController;
import pedroPathing.utils.functions.Intake;
import pedroPathing.utils.functions.Logger;
import pedroPathing.utils.functions.Slides;

@TeleOp(name = "FraserTeleOp Version 0.5")
public class FraserTeleOp extends OpMode {
    private Slides slides = new Slides();
    private Intake intake = new Intake();
    private ChassisController chassis = new ChassisController();
    private DcMotorEx intakeMotor, intakeSlide, hangMotor;
    private double lStickX, lStickY, rStickX;
    double sensitivity = 1;
    private boolean lBumper = false, rBumper = false;
    private DcMotorEx FL, BL, FR, BR;

    @Override
    public void init() {
        Logger.disable();
        Logger.info("TeleOp Initialized");
        slides.initialize(hardwareMap);
        chassis.initialize(hardwareMap);
        intake.initialize(hardwareMap);
        intakeMotor = hardwareMap.get(DcMotorEx.class, "IM");
        intakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        intakeSlide = hardwareMap.get(DcMotorEx.class, "EM");
        intakeSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        intakeSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        hangMotor = hardwareMap.get(DcMotorEx.class, "HM");
        hangMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        hangMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        hangMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    @Override
    public void loop() {
        //chassis.update(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, sensitivity);
        if (gamepad1.x){FL.setPower(0.3);} else {FL.setPower(0);} // rv
        if (gamepad1.y){FR.setPower(0.3);} else {FR.setPower(0);} // no
        if (gamepad1.a){BR.setPower(0.3);} else {BR.setPower(0);} // no
        if (gamepad1.b){BL.setPower(0.3);} else {BL.setPower(0);} // rv
        if (gamepad1.right_bumper) {
            if (slides.isExtended() && !rBumper){
                slides.retract();
            }
            else if (!rBumper){
                slides.extend();
            }
            rBumper = true;
            gamepad1.rumbleBlips(2);
        }
        else {
            rBumper = false;
        }
        if (gamepad1.left_bumper) {
            if (intake.isExtended() && !lBumper){
                intake.retract();
            }
            else if (!lBumper){
                intake.extend();
            }
            lBumper = true;
            gamepad1.rumbleBlips(4);
        }
        else {
            lBumper = false;
        }
        if (gamepad1.dpad_up && sensitivity < 1){
            sensitivity += 0.01;
        }
        if (gamepad1.dpad_down && sensitivity > 0.1){
            sensitivity -= 0.01;
        }
        if (gamepad1.dpad_left){
            hangMotor.setPower(1);
        }
        else if (gamepad1.dpad_right){
            hangMotor.setPower(-1);
        }
        else {
            hangMotor.setPower(0);
        }
        if (gamepad1.a){
            slides.resetSlides();
        }
        intakeMotor.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
        telemetry.addLine("Intake slide position: " + intakeSlide.getCurrentPosition());
        telemetry.addLine("Intake slide power: " + intakeSlide.getPower());
        telemetry.addLine("Intake target position" + intake.getTargetPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        Logger.info("Asking Slides to terminate threads...");
        slides.stopSlideThreads();
        intake.stopIntakeThread();
    }
}
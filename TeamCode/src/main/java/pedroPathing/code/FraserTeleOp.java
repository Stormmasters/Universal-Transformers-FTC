package pedroPathing.code;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import pedroPathing.utils.functions.RobotHardware;
import pedroPathing.utils.functions.ChassisController;
import pedroPathing.utils.functions.Extension;
import pedroPathing.utils.functions.Logger;
import pedroPathing.utils.functions.Lift;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "FraserTeleOp Version 0.5")
public class FraserTeleOp extends OpMode {
    private Lift lift = new Lift();
    private Extension extension = new Extension();
    private ChassisController chassis = new ChassisController();
    double sensitivity = 1;
    private boolean lBumper = false, rBumper = false;
    RobotHardware robotHardware;
    Servo IS1, IS2;
    @Override
    public void init() {
        Logger.disable();
        Logger.info("TeleOp Initialized");
        robotHardware = new RobotHardware(hardwareMap);
        chassis.initialize(robotHardware.FL, robotHardware.BL, robotHardware.FR, robotHardware.BR);
        lift.initialize(robotHardware.liftMotor1, robotHardware.liftEnc1, robotHardware.liftMotor2, robotHardware.liftEnc2);
        extension.initialize(robotHardware.extensionMotor, robotHardware.extEnc ,robotHardware.IS1);
    }

    @Override
    public void loop() {
        robotHardware.clearBulkCache();
        chassis.update(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, sensitivity);
        if (gamepad1.right_bumper) {
            if (lift.isExtended() && !rBumper){
                lift.retract();
            }
            else if (!rBumper){
                lift.extend();
            }
            rBumper = true;
            gamepad1.rumbleBlips(2);
        }
        else {
            rBumper = false;
        }
        if (gamepad1.right_bumper) {
            if (extension.isExtended() && !lBumper){
                extension.retract();
            }
            else if (!lBumper){
                extension.extend();
            }
            lBumper = true;
            gamepad1.rumbleBlips(4);
        }
        else {
            lBumper = false;
        }
        if (gamepad1.dpad_up && sensitivity < 1){
            sensitivity += 0.004;
        }
        if (gamepad1.dpad_down && sensitivity > 0.1){
            sensitivity -= 0.004;
        }
        if (gamepad1.dpad_up && gamepad1.left_bumper){
            sensitivity = 1;
        }
        if (gamepad1.dpad_down && gamepad1.left_bumper){
            sensitivity = 0;
        }
        robotHardware.intakeMotor.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
        telemetry.addData("extension", extension.getExtensionPosition());
        telemetry.update();
        extension.update();
    }

    @Override
    public void stop() {
        Logger.info("Stopping...");
    }
}
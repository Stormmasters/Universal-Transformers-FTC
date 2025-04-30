package pedroPathing.code;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import pedroPathing.utils.functions.ChassisController;
import pedroPathing.utils.functions.Logger;
import pedroPathing.utils.functions.Slides;

@TeleOp(name = "FraserTeleOp Version 0.3")
public class FraserTeleOp extends OpMode {
    private Slides slides = new Slides();
    private ChassisController chassis = new ChassisController();
    private DcMotorEx slideMotor, linearMotor, FL, BL, FR, BR;
    private double lStickX, lStickY, rStickX;
    double sensitivity = 1;

    @Override
    public void init() {
        Logger.info("TeleOp Initialized");
        slides.initialize(hardwareMap);
        chassis.initialize(hardwareMap);
    }

    @Override
    public void loop() {
        chassis.update(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, sensitivity);
        if (gamepad1.right_bumper && !slides.isExtended()) {
            slides.extend();
            Logger.info("Asking Slides to extend");
        }
        if (gamepad1.left_bumper && slides.isExtended()){
            Logger.info("Asking Slides to retract");
            slides.retract();
        }
        if (gamepad1.dpad_up && sensitivity < 1){
            sensitivity += 0.01;
        }
        if (gamepad1.dpad_down && sensitivity > 0.1){
            sensitivity -= 0.01;
        }
    }

    @Override
    public void stop() {
        Logger.info("Asking Slides to terminate threads...");
        slides.stopSlideThreads();
    }
}

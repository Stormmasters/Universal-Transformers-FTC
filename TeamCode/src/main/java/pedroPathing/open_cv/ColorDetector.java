package pedroPathing.open_cv;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.os.Looper;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import org.openftc.easyopencv.OpenCvPipeline;

public class ColorDetector implements Runnable {
    private HardwareMap hardwareMap;
    private OpenCvCamera cam;
    public ColorDetector(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }
    @Override
    public void run(){
        while (!Thread.interrupted()){
            cam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "WebCam 1"));
            cam.setPipeline(new CameraPipeline());
            cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {@Override public void onOpened() {cam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT); } @Override public void onError(int errorCode) {}});
        }
    }
}
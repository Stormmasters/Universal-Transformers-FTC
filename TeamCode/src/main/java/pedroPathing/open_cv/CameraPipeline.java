package pedroPathing.open_cv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CameraPipeline extends OpenCvPipeline {
    Mat hsv = new Mat();
    Mat mask = new Mat();
    @Override
    public Mat processFrame(Mat input){
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
        Scalar lowerBlue = new Scalar(100, 150, 0);
        Scalar upperBlue = new Scalar(140, 255, 255);
        Core.inRange(hsv, lowerBlue, upperBlue, mask);
        return mask;
    }
}

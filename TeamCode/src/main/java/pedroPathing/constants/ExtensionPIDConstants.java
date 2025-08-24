package pedroPathing.constants;
import com.acmerobotics.dashboard.config.Config;

@Config
public class ExtensionPIDConstants {
    public static double retractedPosition = -50;
    public static double extendedPosition = -350;
    public static double kP = 0.004;
    public static double kD = 0.02;
    public static double maxPower = 0.8;
}

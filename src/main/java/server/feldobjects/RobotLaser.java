package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The FeldObject Robot laser: Robot lasers fire in the direction a robot is facing.
 * Their range has no limit. Any robot in the line of sight is shot.
 * Robot lasers cannot fire through walls or shoot more than one robot.
 * (Remember to take a SPAM damage card for each laser that hits you.)
 *
 * @author can ren
 * @create $(YEAR) -$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RobotLaser extends FeldObject {
}

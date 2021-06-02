package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The FeldObject Push panel: Push panels push any robot resting on them into the
 * next space in the direction the push panel faces. The panels activate only in
 * the register that corresponds to the number on them.
 *
 * @author Jonas Gottal
 * @author can ren
 * @create $(YEAR) -$(MONTH)-$(DAY)
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushPanel extends FeldObject{

    private String type;
    private String isOnBoard;
    private List<String> orientations;
    private List<Integer> registers;

    public PushPanel() {
    }

    public PushPanel(String type, String isOnBoard, List<String> orientations, List<Integer> registers) {
        this.type = type;
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
        this.registers = registers;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public List<String> getOrientations() {
        return orientations;
    }

    @Override
    public List<Integer> getRegisters() {
        return registers;
    }

    @Override
    public void doBoardFunction(int clientID) {
        //TODO
    }
}

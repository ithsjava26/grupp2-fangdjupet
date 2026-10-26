package fangdjupet.contract;

import java.util.Objects;

/**
 * En händelse i spelet, t.ex. "Hjälten besegrade Trollet".
 */
public record GameEvent(fangdjupet.contract.GameEventType type, String message) {
    public GameEvent {
        Objects.requireNonNull(type, "type får inte vara null");
        Objects.requireNonNull(message, "message får inte vara null");
    }
}

package fangdjupet.contract;

/**
 * Observer-mönstret: den som vill få veta när något händer i spelet.
 */
@FunctionalInterface
public interface GameEventListener {
    void onEvent(GameEvent event);
}

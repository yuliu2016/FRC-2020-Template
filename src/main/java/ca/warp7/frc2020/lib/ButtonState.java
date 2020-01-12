package ca.warp7.frc2020.lib;

/**
 * A ButtonState represents one of the four states of the button
 *
 * Pressed:  The button has just turned from false to true
 * Released: The button has just turned from true to false
 * HeldDown: The button was already true and stays true
 * None:     The button was already false and stays false
 */
public enum ButtonState {
    Pressed, Released, HeldDown, None;

    /**
     * @return if the button is pressed
     */
    public boolean isPressed() {
        return this == Pressed;
    }

    /**
     * @return if the button is released
     */
    public boolean isReleased() {
        return this == Released;
    }

    /**
     * @return if the button is held down
     */
    public boolean isHeldDown() {
        return this == HeldDown;
    }

    /**
     * @return if the button is kept up
     */
    public boolean isKeptUp() {
        return this == None;
    }

    /**
     * @return if the button is up
     */
    public boolean isUp() {
        return isReleased() || isKeptUp();
    }

    /**
     * @return if the button is down
     */
    public boolean isDown() {
        return isPressed() || isHeldDown();
    }

    /**
     * Update a ButtonState based on an old state and a new value
     *
     * @param newState the new button value
     * @return the appropriate new ButtonState given the inputs
     */
    public ButtonState update(boolean newState) {
        return newState ? isDown() ? HeldDown : Pressed : isUp() ? None : Released;
    }
}

package vekta;

interface Context {
	/**
	 * Draws the context each loop
	 */
	void render();

	/**
	 * What to do when a key is pressed
	 */
	void keyPressed(char key);

	/**
	 * What to do when a key is released
	 */
	void keyReleased(char key);

	/**
	 * What to do when the mouse wheel is scrolled
	 */
	void mouseWheel(int amount);
}

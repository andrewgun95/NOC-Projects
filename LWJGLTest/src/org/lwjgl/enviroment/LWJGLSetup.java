package org.lwjgl.enviroment;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class LWJGLSetup {

	private float sWidth, sHeight, mpp;
	private float lastDelta;

	public LWJGLSetup(float sWidth, float sHeight) {
		this(sWidth, sHeight, 1.0f);
	}

	public LWJGLSetup(float mWidth, float mHeight, float ppm) {
		sWidth = mWidth * ppm;
		sHeight = mHeight * ppm;
		// meter per pixel = 1 / pixel per meter
		mpp = 1.0f / ppm;
	}

	public void start(Application application) {
		try {
			Display.setDisplayMode(new DisplayMode((int) sWidth, (int) sHeight));
			Display.setTitle("FPS : " + application.fps);
			Display.create();

			glSetup();
			application.setup();

			float lastFrame = getTime();
			int counterFPS = 0;

			lastDelta = getTime();

			while (!Display.isCloseRequested()) {
				Display.sync(application.fps);
				Display.update();

				glScreen();
				glDefColor();

				application.draw();
				application.update(getDelta());

				// calculate current frame per second
				if (getTime() - lastFrame > 1000) {
					Display.setTitle("FPS : " + counterFPS);
					counterFPS = 0;
					lastFrame = getTime();
				}
				counterFPS++;
			}

			Display.destroy();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private float getDelta() {
		long time = getTime();
		float delta = (float) (time - lastDelta);
		lastDelta = time;
		return delta / 1000f;
	}

	/**
	 * get time in millisecond
	 * 
	 * @return millisecond
	 */
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private void glDefColor() {
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
	}

	private void glScreen() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	private void glSetup() {
		glMatrixMode(GL_PROJECTION);
		glOrtho(0, sWidth * mpp, 0, sHeight * mpp, 1, -1);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

}

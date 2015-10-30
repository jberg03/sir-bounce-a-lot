package com.hislightgamestudio.sirbouncealot.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hislightgamestudio.sirbouncealot.SirBounceALot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = SirBounceALot.TITLE + ": " + SirBounceALot.VERSION;
		config.vSyncEnabled = true;
		config.width = 640;
		config.height = 960;
		new LwjglApplication(new SirBounceALot(), config);
	}
}

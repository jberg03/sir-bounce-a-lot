package com.hislightgamestudio.sirbouncealot;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = SirBounceALot.TITLE + SirBounceALot.VERSION;
		cfg.vSyncEnabled = true;
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 960;
		
		new LwjglApplication(new SirBounceALot(), cfg);
	}
}

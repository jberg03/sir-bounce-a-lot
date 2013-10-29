////////////////////////////////////////////////////////////////////////////////////
//Author: John Berg
//Date: 8/13/13
//Purpose: Uses libgdx's Texture packer to take images from input folder and creates
//a spritesheet and .pack file in the Output file
////////////////////////////////////////////////////////////////////////////////////

package com.hislightgamestudio.sirbouncealot.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class TexturePacker {

	public static void main(String[] args){
		Settings settings = new Settings();
		settings.pot = false;
		settings.maxHeight = 65;
		settings.maxWidth = 64;
		settings.paddingX = 0;
		settings.paddingY = 0;
		
		TexturePacker2.process(settings, "C:/Users/John/Documents/Sir Bounce-A-Lot/input",
				"C:/Users/John/Documents/Sir Bounce-A-Lot/spritesheets", "menuAtlas.pack");
	}
}

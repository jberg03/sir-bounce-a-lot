/************************************************************************
* Copyright 2013 His Light Game Studio
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
**************************************************************************
* PauseMenu.java is the class that will build the pause menu that will 
* pop up when the player hits the back button on Android or ENTER on PC
*************************************************************************/
package com.hislightgamestudio.sirbouncealot.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PauseMenu extends MenuAbstractScreen {

	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void show() {
		Label heading = new Label("PAUSED", menuSkin, "big");

		TextButton resume = new TextButton("RESUME", menuSkin, "small");
		
		
		super.show();
	}
	
	//The pause menu for now will have a resume, settings, and quit button
	//The heading for the table will simply say "PAUSE"
	//The menu will be on top of the screen and be slightly transparent
	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
}

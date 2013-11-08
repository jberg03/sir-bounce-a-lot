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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseMenu extends MenuAbstractScreen {
	private Screen screen;
	
	public PauseMenu(Screen screen){
		this.screen = screen;
	}
	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	//The pause menu for now will have a resume, settings, and quit button
	//The heading for the table will simply say "PAUSE"
	//The menu will be on top of the screen and be slightly transparent
	@Override
	public void show() {
		//create the heading
		Label heading = new Label("PAUSED", menuSkin, "big");

		//create the resume button
		TextButton resume = new TextButton("RESUME", menuSkin, "small");
		resume.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				stage.addAction(sequence(parallel(fadeOut(.15f), moveTo(0, -20, .15f)), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(screen);
					}
				})));
			}
		});
		resume.pad(10);
		
		//create the settings button
		TextButton settings = new TextButton("SETTINGS", menuSkin);
		settings.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				stage.addAction(sequence(parallel(fadeOut(.15f), moveTo(0, -20, .15f)), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
					}
				})));
			}
		});
		settings.pad(10f);

		//creating exit button		
		TextButton exit = new TextButton("EXIT", menuSkin, "big");
		exit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(parallel(fadeOut(.15f), moveTo(0, -20, .15f)), run(new Runnable(){

					@Override
					public void run() {
						Gdx.app.exit();								
					}
				})));
			}
		});
		exit.pad(10f);
		
		table.add(heading).spaceBottom(25f).row();
		table.add(resume).spaceBottom(15f).row();
		table.add(settings).spaceBottom(15f).row();
		table.add(exit);

		stage.addActor(table);
		
		super.show();
	}
		
	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
}

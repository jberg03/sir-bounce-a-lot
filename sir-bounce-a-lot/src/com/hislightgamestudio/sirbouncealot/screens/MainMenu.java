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
* MainMenu.java is where we layout the main menu
*************************************************************************/
package com.hislightgamestudio.sirbouncealot.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hislightgamestudio.sirbouncealot.SirBounceALot;

public class MainMenu extends MenuAbstractScreen {

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
		//creating heading 		
		Label heading = new Label(SirBounceALot.TITLE, menuSkin);
		heading.setFontScale(2f);


		TextButton play = new TextButton("PLAY", menuSkin, "big");
		play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
					}
				})));
			}
		});
		play.pad(15);

		TextButton settings = new TextButton("SETTINGS", menuSkin);
		settings.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
					}
				})));
			}
		});
		settings.pad(15f);

		//creating exit button		
		TextButton exit = new TextButton("EXIT", menuSkin, "big");
		exit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(parallel(fadeOut(.75f), moveTo(0, -40, .75f)), run(new Runnable(){

					@Override
					public void run() {
						Gdx.app.exit();								
					}
				})));
			}
		});
		exit.pad(15f);	

		table.add(heading).spaceBottom(100f).row();
		table.add(play).spaceBottom(15f).row();
		table.add(settings).spaceBottom(15f).row();
		table.add(exit);

		stage.addActor(table);

		//Animate Main Menu				
		//heading color change
		heading.addAction(forever(sequence(color(new Color().set(0, 0, 1, 1), 0.5f)
				,color(new Color().set(0, 1, 0, 1), 0.5f)
				,color(new Color().set(1, 0, 0, 1), 0.5f)
				,color(new Color().set(1, 1, 0, 1), 0.5f)
				,color(new Color().set(0.3f, 0.3f, 0.3f, 1), 0.5f)
				,color(new Color().set(1, 1, 1, 1), 0.5f))));

		//heading and button fade in
		play.addAction(sequence(alpha(0), delay(0.05f), fadeIn(0.25f)));
		settings.addAction(sequence(alpha(0), delay(0.15f), fadeIn(0.25f)));
		exit.addAction(sequence(alpha(0), delay(0.25f), fadeIn(0.25f)));

		//table fade in
		table.addAction(sequence(alpha(0), parallel(moveTo(0, -Gdx.graphics.getHeight() / 32, 0.5f), alpha(1, 0.5f))));
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		super.dispose();
	}
}

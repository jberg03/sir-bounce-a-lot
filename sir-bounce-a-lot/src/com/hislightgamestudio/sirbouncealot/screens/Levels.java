package com.hislightgamestudio.sirbouncealot.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hislightgamestudio.sirbouncealot.control.InputController;

public class Levels extends MenuAbstractScreen {
	
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
		List list = new List(new String[] {"one", "two", "three", "four", "five"}, menuSkin);
		
		ScrollPane scrollPane = new ScrollPane(list, menuSkin);
		
		TextButton play = new TextButton("PLAY", menuSkin, "big");
		play.addListener(new ClickListener(){			
			@Override
			public void clicked(InputEvent event, float x, float y){
				InputController.playing = true;
				((Game)Gdx.app.getApplicationListener()).setScreen(new Play());
			}
		});
		play.pad(15f);
		
		TextButton back = new TextButton("Back", menuSkin, "small");
		back.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
					}

				})));
			}
		});
		back.pad(10f);
		
		//putting it all together
		table.add(new Label("SELECT LEVEL", menuSkin)).colspan(3).expandX().spaceBottom(50).row();
        table.add(scrollPane).uniformX().expandY().top().left();
        table.add(play).uniformX();
        table.add(back).uniformX().bottom().right();
		
		stage.addActor(table);
		
		stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f)));
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}

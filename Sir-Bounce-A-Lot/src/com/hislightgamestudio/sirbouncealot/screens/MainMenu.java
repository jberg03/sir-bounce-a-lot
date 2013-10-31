package com.hislightgamestudio.sirbouncealot.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.hislightgamestudio.sirbouncealot.SirBounceALot;
import com.hislightgamestudio.sirbouncealot.tween.ActorAccessor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu extends AbstractScreen {

	@Override
	public void render(float delta) {
		super.render(delta);
		
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void show() {
		//creating heading 		
				Label heading = new Label(SirBounceALot.TITLE, menuSkin, "big");
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
						Timeline.createParallel().beginParallel()
						.push(Tween.to(table, ActorAccessor.ALPHA, .75f).target(0))
						.push(Tween.to(table, ActorAccessor.Y, .75f).target(table.getY() - 50)
								.setCallback(new TweenCallback() {

									@Override
									public void onEvent(int type, BaseTween<?> source) {
										Gdx.app.exit();
									}
								}))
								.end().start(tweenManager);
					}
				});
				exit.pad(15f);	
				
				table.add(heading).spaceBottom(100f).row();
				table.add(play).spaceBottom(15f).row();
				table.add(settings).spaceBottom(15f).row();
				table.add(exit);
				
				stage.addActor(table);
				
				//Animate Main Menu
				Tween.registerAccessor(Actor.class, new ActorAccessor());
				
				//heading color change
				Timeline.createSequence().beginSequence()
						.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 0, 1))
						.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 1, 0))
						.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 0, 0))
						.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 1, 0))
						.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 1, 1))
						.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 0, 1))
						.push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(1, 1, 1))
						.end().repeat(Tween.INFINITY, 0).start(tweenManager);
				
				//heading and button fade in
				Timeline.createSequence().beginSequence()
						.push(Tween.set(play, ActorAccessor.ALPHA).target(0))
						.push(Tween.set(settings, ActorAccessor.ALPHA).target(0))
						.push(Tween.set(exit, ActorAccessor.ALPHA).target(0))
						.push(Tween.from(heading, ActorAccessor.ALPHA, 0.125f).target(0))
						.push(Tween.to(play, ActorAccessor.ALPHA, 0.125f).target(1))
						.push(Tween.to(settings, ActorAccessor.ALPHA, 0.125f).target(1))
						.push(Tween.to(exit, ActorAccessor.ALPHA, 0.125f).target(1))
						.end().start(tweenManager);
				
				//table fade in
				Tween.from(table, ActorAccessor.ALPHA, 0.5f).target(0).start(tweenManager);
				Tween.from(table, ActorAccessor.Y, 0.5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);
				
				tweenManager.update(Gdx.graphics.getDeltaTime());
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

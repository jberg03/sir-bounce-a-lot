package com.hislightgamestudio.sirbouncealot.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hislightgamestudio.sirbouncealot.SirBounceALot;

public class SettingsScreen extends MenuAbstractScreen {        
        
        public static FileHandle levelDirectory(){
                String prefsDir = Gdx.app.getPreferences(SirBounceALot.TITLE).getString("leveldirectory").trim();
                if(prefsDir != null && !prefsDir.equals(""))
                        return Gdx.files.absolute(prefsDir);
                else
                        return Gdx.files.absolute(Gdx.files.external(SirBounceALot.TITLE + "/levels").path());
        }

        public static boolean vSync(){
                return Gdx.app.getPreferences(levelDirectory().toString()).getBoolean("vSync");
        }

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
                final CheckBox vSyncCheckBox = new CheckBox("vSync", menuSkin);
                vSyncCheckBox.setChecked(vSync());

                final TextField levelDirInput = new TextField(levelDirectory().path(), menuSkin);
                levelDirInput.setMessageText("level directory");

                final TextButton back = new TextButton("BACK", menuSkin, "small");
                back.pad(10f);

                ClickListener buttonHandler = new ClickListener() {

                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                                // event.getListenerActor() returns the source of the event, e.g. a button that was clicked
                                if(event.getListenerActor() == vSyncCheckBox) {
                                        // save vSync
                                        Gdx.app.getPreferences(levelDirectory().toString()).putBoolean("vSync", vSyncCheckBox.isChecked());

                                        Gdx.app.log(SirBounceALot.TITLE, "vSync " + (vSync() ? "enabled" : "disabled"));

                                } else if(event.getListenerActor() == back) {
                                        // save level directory
                                        String actualLevelDirectory = levelDirInput.getText().trim().equals("") ? Gdx.files.getExternalStoragePath() 
                                                                                                        + SirBounceALot.TITLE + "/levels" : levelDirInput.getText().trim(); 
                                        Gdx.app.getPreferences(SirBounceALot.TITLE).putString("leveldirectory", actualLevelDirectory);

                                        // save the settings to preferences file (Preferences#flush() writes the preferences in memory to the file)
                                        Gdx.app.getPreferences(SirBounceALot.TITLE).flush();

                                        Gdx.app.log(SirBounceALot.TITLE, "settings saved");

                                        stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

                                                @Override
                                                public void run() {
                                                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                                                }
                                        })));
                                }
                        }
                };

                vSyncCheckBox.addListener(buttonHandler);

                back.addListener(buttonHandler);

                //putting it all together
                table.add(new Label("SETTINGS", menuSkin, "big")).spaceBottom(50).colspan(3).expandX().row();
                table.add();
                table.add("level directory");
                table.add().row();
                table.add(vSyncCheckBox).top().expandY();
                table.add(levelDirInput).top().fillX();
                table.add(back).bottom().right();

                stage.addActor(table);

                stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
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
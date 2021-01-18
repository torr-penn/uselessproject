package com.gtasoft.uselessproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.gtasoft.uselessproject.intro.IntroStoryScreen;
import com.gtasoft.uselessproject.menu.MenuScreen;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UselessProject  extends ManagedGame<ManagedScreen, ScreenTransition> {
    private IntroStoryScreen introStoryScreen;
    private MenuScreen menuScreen;
    private SpriteBatch batch;
    private boolean vertical;
    public UselessProject(boolean vertical) {

        this.setVertical(vertical);



    }
    @Override
    public void create() {
        super.create();
        setMenuScreen(new MenuScreen(this));
        setIntroStoryScreen(new IntroStoryScreen(this));
        batch = new SpriteBatch();
        this.screenManager.addScreen("intro", getIntroStoryScreen());
        this.screenManager.addScreen("menu", getMenuScreen());

        BlendingTransition blendingTransition = new BlendingTransition(batch, 1F, Interpolation.pow2In);
        screenManager.addScreenTransition("blending_transition", blendingTransition);

        this.screenManager.pushScreen("intro", "blending_transition");

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        this.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }


    public IntroStoryScreen getIntroStoryScreen() {
        return introStoryScreen;
    }

    public void setIntroStoryScreen(IntroStoryScreen introStoryScreen) {
        this.introStoryScreen = introStoryScreen;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
}
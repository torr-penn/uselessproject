package com.gtasoft.uselessproject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.gtasoft.uselessproject.screen.BuggyScreen;
import com.gtasoft.uselessproject.screen.WorkingScreen;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UselessProject  extends ManagedGame<ManagedScreen, ScreenTransition> {
    private BuggyScreen buggyScreen;
    private WorkingScreen workingScreen;
    private SpriteBatch batch;
    private boolean vertical;
    public UselessProject(boolean vertical) {

        this.setVertical(vertical);



    }
    @Override
    public void create() {
        super.create();
        setMenuScreen(new WorkingScreen(this));
        setIntroStoryScreen(new BuggyScreen(this));
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

      //  this.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }


    public BuggyScreen getIntroStoryScreen() {
        return buggyScreen;
    }

    public void setIntroStoryScreen(BuggyScreen buggyScreen) {
        this.buggyScreen = buggyScreen;
    }

    public WorkingScreen getMenuScreen() {
        return workingScreen;
    }

    public void setMenuScreen(WorkingScreen workingScreen) {
        this.workingScreen = workingScreen;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
}
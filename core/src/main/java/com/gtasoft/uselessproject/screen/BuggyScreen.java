package com.gtasoft.uselessproject.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.gtasoft.uselessproject.UselessProject;
import de.eskalon.commons.screen.ManagedScreen;


public class BuggyScreen extends ManagedScreen implements InputProcessor {



    private ImageButton btnExit;


    private ImageButton btnNext;


    private Stage stage;


    Skin skin;


    Texture imgTitle;
    private Label displayInfo;

    private Label explainProblem;

    Texture imgBackground;

    Texture imgNext;

    int w;
    int h;
    float stateTime;
    private UselessProject game;
    boolean translated = false;
    private static final int MenuScreenID = 100;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;

    // constructor to keep a reference to the main Game class
    public BuggyScreen(UselessProject game) {
        this.setGame(game);
        camera = new OrthographicCamera();
        if (game.isVertical()) {
            h = 1280;
            w = 768;
        } else {
            w = 1280;
            h = 768;
        }
        camera.setToOrtho(false, w, h);
        viewport = new FitViewport(w, h, camera);
        initializeScreen();
    }


    @Override
    public void dispose() {
        getStage().dispose();

    }

    @Override
    public void create() {

        stateTime = 0f;
        sb = new SpriteBatch();
        skin =new Skin(Gdx.files.internal("ui/azzzleep.json"));

        imgBackground = new Texture(Gdx.files.internal("img/bg_greysand.png"));

        Texture imgExitSmall = new Texture(Gdx.files.internal("img/menu/cross-icon-small.png"));
        skin.add("imgExitSmall", imgExitSmall);

        ImageButton.ImageButtonStyle ibtnStyle = new ImageButton.ImageButtonStyle();
        ibtnStyle.up = skin.getDrawable("imgExitSmall");
        btnExit = new ImageButton(ibtnStyle);
        Label.LabelStyle lblStyleMe = new Label.LabelStyle();
        lblStyleMe.fontColor = Color.GOLDENROD;
        lblStyleMe.font = skin.getFont("bar-font");

        displayInfo=new Label("useful info",skin);
        displayInfo.setStyle(lblStyleMe);
        displayInfo.setPosition(5, h/2+200,Align.left);


        Label.LabelStyle lblStylecomment = new Label.LabelStyle();
        lblStylecomment.fontColor = Color.GOLDENROD;
        lblStylecomment.font = skin.getFont("comment2");

        explainProblem=new Label("In this version resizing screen sometimes \n make fitviewport to expand to fullscreen",skin);
        explainProblem.setStyle(lblStylecomment);
        explainProblem.setPosition(5, h/5);

        imgNext = new Texture(Gdx.files.internal("img/menu/next-icon.png"));
        skin.add("imgNext", imgNext);
        ImageButton.ImageButtonStyle ibtnStyleHelp = new ImageButton.ImageButtonStyle();
        ibtnStyleHelp.up = skin.getDrawable("imgNext");
        btnNext = new ImageButton(ibtnStyleHelp);



        setStage(new Stage());


        int wmiddle = (int) w / 2;
        int hmiddle = (int) h / 2;


        imgTitle = new Texture(Gdx.files.internal("img/menu/title-menu.png"));


        if (getGame().isVertical()) {
            btnExit.setPosition(w - 24 - 32, h - 24 - 32);
            btnNext.setPosition(w - 296, hmiddle - 200);

        } else {
            btnExit.setPosition(w - 24 - 32, h - 24 - 32);
            btnNext.setPosition(w - 296, hmiddle - 200);
        }


        btnExit.setSize(32, 32);
        btnExit.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {

                getGame().dispose();
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });








        btnNext.setSize(96, 96);
        btnNext.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {
                getGame().getScreenManager().pushScreen("menu", null);
                //game.setScreen(game.getHelpScreen());
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                return true;

            }
        });


        getStage().setViewport(viewport);
        getStage().addActor(btnNext);
        getStage().addActor(btnExit);
        getStage().addActor(displayInfo);
        getStage().addActor(explainProblem);
        addInputProcessor(getStage());
        addInputProcessor(this);

    }

    public Texture getImgBackground() {
        return imgBackground;
    }

    @Override
    public void render(float delta) {
        //viewport.apply(); NO CALL
        displayInfo.setText(//"Window w "+width+ "h "+height+ "\n"+
                " viewport  width "+viewport.getScreenWidth()+ " height "+viewport.getScreenHeight()+"\n"+
                        " viewport  x "+viewport.getScreenX()+ " y "+viewport.getScreenY()+"\n"+
                        " vport World  width "+viewport.getWorldWidth()+ " height "+viewport.getWorldHeight()+"\n"+
                        " camera  width "+camera.viewportWidth+ " height "+camera.viewportHeight+"\n"+
                        " gdx graphics  width "+Gdx.graphics.getWidth()+ " height "+Gdx.graphics.getHeight()+"\n"+
                        " gdx buffer width "+Gdx.graphics.getBackBufferWidth()+ " height "+Gdx.graphics.getBackBufferHeight()+"\n"

        );

        Gdx.gl.glClearColor(0f, 0f, 0f, 0.22f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!translated) {
            //           camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
            translated = true;
        }
        //    sb.setProjectionMatrix(camera.combined);
        camera.update();

        sb.begin();
        sb.draw(getImgBackground(), 0, 0, w, h, 0, 1, 1, 0);
        sb.draw(imgTitle, (int) ((w - imgTitle.getWidth()) / 2), h - imgTitle.getHeight() - 70);
        sb.end();
        getStage().act(delta);
        getStage().draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        super.show();
        if (sb == null) {
            create();

        }



        //   Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void hide() {


        //Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            getGame().dispose();
            return true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    public UselessProject getGame() {
        return game;
    }

    public void setGame(UselessProject game) {
        this.game = game;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

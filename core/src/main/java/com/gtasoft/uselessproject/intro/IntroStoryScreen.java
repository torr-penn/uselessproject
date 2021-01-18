package com.gtasoft.uselessproject.intro;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.gtasoft.uselessproject.UselessProject;
import de.eskalon.commons.screen.ManagedScreen;


public class IntroStoryScreen extends ManagedScreen {
    //implements} Screen, ApplicationListener, InputProcessor {


    private ImageButton btnNext;


    Stage stage;

    Label lbl_neuron_left;
    Label lbl_neuron_right;
    Label lbl_skip;
    Skin skin;
    boolean bleft = true;
    boolean bright = false;

    private float alpha = 0;
    Texture imgBackground;
    Texture imgBubbleLeft;
    Texture imgBubbleRight;
    Texture img_neuron_awake_green;
    Texture img_neuron_awake_purple;
    Texture img_neuron_sleeping_green;
    Texture img_neuron_sleeping_purple;
    InputMultiplexer multiplexer;
    int w;
    int h;
    float stateTime;
    UselessProject game;
    boolean translated = false;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch sb;
    private ShapeRenderer sr;

    int step = 0;
    long timer = 0;

    // constructor to keep a reference to the main Game class
    public IntroStoryScreen(UselessProject game) {
        this.game = game;

        camera = new OrthographicCamera();

        if (game.isVertical()) {
            w = 768;
            h = 1280;


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
        sb.dispose();
        stage.dispose();


    }

    @Override
    public void create() {

        stateTime = 0f;
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        skin =new Skin(Gdx.files.internal("ui/azzzleep.json"));


        imgBackground = new Texture(Gdx.files.internal("img/story.png"));
        imgBubbleLeft = new Texture(Gdx.files.internal("img/bubble_left.png"));
        imgBubbleRight = new Texture(Gdx.files.internal("img/bubble_right.png"));
        img_neuron_awake_green = new Texture(Gdx.files.internal("img/neurone_awake_green.png"));
        img_neuron_awake_purple = new Texture(Gdx.files.internal("img/neurone_awake_purple.png"));

        img_neuron_sleeping_green = new Texture(Gdx.files.internal("img/neurone_sleep_green.png"));
        img_neuron_sleeping_purple = new Texture(Gdx.files.internal("img/neurone_sleep_purple.png"));
        Texture imgNext = new Texture(Gdx.files.internal("img/menu/next-icon-small.png"));
        skin.add("imgNext", imgNext);
        ImageButton.ImageButtonStyle ibtnStyle = new ImageButton.ImageButtonStyle();
        ibtnStyle.up = skin.getDrawable("imgNext");
        btnNext = new ImageButton(ibtnStyle);


        stage = new Stage();


        btnNext.setSize(64, 64);
        btnNext.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button) {

                System.out.println("----btnNext  touchup i ahve been clicked here x:" + x + " y : " + y);
                System.out.println(" w is : " + w);
                System.out.println(" h is : " + h);
                game.getScreenManager().pushScreen("menu", null);
                return;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                System.out.println("----btnNext touchDown  NEXT i ahve been clicked here x:" + x + " y : " + y);
                System.out.println(" w is : " + w);
                System.out.println(" h is : " + h);
                game.getScreenManager().pushScreen("menu", null);
                return true;

            }
        });


        Label.LabelStyle lblStyleLeft = new Label.LabelStyle();
        lblStyleLeft.fontColor = Color.LIME;
        lblStyleLeft.font = skin.getFont("comment2");


        Label.LabelStyle lblStyleRight = new Label.LabelStyle();
        lblStyleRight.fontColor = Color.WHITE;
        lblStyleRight.font = skin.getFont("comment2");

        lbl_neuron_left = new Label("", skin);
        lbl_neuron_left.setStyle(lblStyleLeft);

        lbl_neuron_right = new Label("", skin);
        lbl_neuron_right.setStyle(lblStyleRight);

        if (game.isVertical()) {
            lbl_neuron_left.setPosition(150, 4 * h / 5 + 100);
            btnNext.setPosition(w - 24 - 64, h - 24 - 64);


            lbl_neuron_right.setPosition(200, 720);

        } else {
            lbl_neuron_right.setPosition(w / 2 + 50, h - h / 5);
            lbl_neuron_left.setPosition(w / 4 - 30, 580);
            btnNext.setPosition(w - 24 - 32, h - 24 - 32);
        }

        stage.setViewport(viewport);
        stage.addActor(btnNext);
        stage.addActor(lbl_neuron_left);
        stage.addActor(lbl_neuron_right);
        processText(0);
        addInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                    System.out.println(" story Q type");
                    game.dispose();
                    return true;
                }

                return true;
            }


        });
        addInputProcessor(stage);
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 0.22f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!translated) {
            //    camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
              translated = true;
        }

        camera.update();
        sb.setProjectionMatrix(camera.combined);


        sb.begin();
        sb.draw(imgBackground, 0, 0, w, h, 0, 1, 1, 0);
        int xs = 0;
        int ys = 0;
        if (step == 16) {
            xs = (int) (Math.random() * 8) - 2;
            ys = (int) (Math.random() * 8) - 2;
        } else {
            xs = (int) (Math.random() * 2);
            ys = (int) (Math.random() * 2);
        }

        if (game.isVertical()) {
            if (bleft) {
                sb.draw(img_neuron_awake_green, (int) (60) + xs, img_neuron_awake_green.getHeight() / 4 + h / 3 + 100 + ys);
                sb.draw(imgBubbleLeft, (int) (85), 2 * h / 3 + 112);
            } else {
                sb.draw(img_neuron_sleeping_green, (int) (60), img_neuron_awake_green.getHeight() / 4 + h / 3 + 100);
            }
            if (bright) {
                sb.draw(img_neuron_awake_purple, (int) (w - 300) + xs, 100 + ys);
                sb.draw(imgBubbleRight, (int) 130, 600);
            } else {
                sb.draw(img_neuron_sleeping_purple, (int) (w - 300), 100);
            }
        } else {

            if (bleft) {
                sb.draw(img_neuron_awake_green, (int) (100 + xs), img_neuron_awake_green.getHeight() / 4 + ys);
                sb.draw(imgBubbleLeft, (int) (160), 420);
            } else {
                sb.draw(img_neuron_sleeping_green, (int) (100), img_neuron_awake_green.getHeight() / 4);
            }
            if (bright) {
                sb.draw(img_neuron_awake_purple, (int) (w - 160) + xs, 300 + ys);
                sb.draw(imgBubbleRight, (int) w - (560), 700);
            } else {
                sb.draw(img_neuron_sleeping_purple, (int) (w - 160), 300);
            }
        }

        sb.end();

        stage.act(delta);
        stage.draw();
        if (Gdx.input.justTouched() || timer < System.currentTimeMillis()) {
            step = step + 1;
            processText(step);
        }
    }

    private void processText(int step) {
        if (step == 0) {
            timer = System.currentTimeMillis() + 3500;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("Hello, my name is\n          Tidyneuron");
            lbl_neuron_right.setText("");
        }
        if (step == 1) {
            timer = System.currentTimeMillis() + 3500;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("I am proud to be\none of your neuron ");
            lbl_neuron_right.setText("");
        }
        if (step == 2) {
            timer = System.currentTimeMillis() + 3500;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("Sometimes,\n I can be active!");
            lbl_neuron_right.setText("");
        }

        if (step == 3) {
            timer = System.currentTimeMillis() + 3500;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("I remember one day...");
            lbl_neuron_right.setText("");
        }

        if (step == 4) {
            timer = System.currentTimeMillis() + 3500;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("...we were able\n to tidy your place!");
            lbl_neuron_right.setText("");
        }
        if (step == 5) {
            timer = System.currentTimeMillis() + 3500;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("But now... \nI am a bit tired...");
            lbl_neuron_right.setText("");
        }
        if (step == 6) {
            timer = System.currentTimeMillis() + 5000;

            bleft = false;
            bright = false;
            lbl_neuron_left.setText("...zzz ZZZ zzzz ...");
            lbl_neuron_right.setText("");
        }
        if (step == 7) {
            timer = System.currentTimeMillis() + 6000;
            bleft = false;
            bright = true;
            lbl_neuron_right.setText("My name is Artyneuron\n I am your\n\"Pixel Art Expert Neuron\"");
            lbl_neuron_left.setText("");
        }
        if (step == 8) {
            timer = System.currentTimeMillis() + 6000;
            bleft = false;
            bright = true;
            lbl_neuron_right.setText("To be honest with you, \n I do not like to work\n with Tidyneuron");
            lbl_neuron_left.setText("");
        }
        if (step == 9) {
            timer = System.currentTimeMillis() + 6000;
            bleft = false;
            bright = true;
            lbl_neuron_right.setText("He is not creative enough!\n It's soooooo  boring!");
            lbl_neuron_left.setText("");
        }
        if (step == 10) {
            timer = System.currentTimeMillis() + 3000;


            bleft = false;
            bright = false;
            lbl_neuron_left.setText("...zzz ZZZ zzz ...");
            lbl_neuron_right.setText("...ZZZ zzz ZZZ ...");
        }
        if (step == 11) {
            timer = System.currentTimeMillis() + 3000;
            bleft = false;
            bright = false;
            lbl_neuron_left.setText("...ZZZ zzzz ...");
            lbl_neuron_right.setText("...zzz ZZZ ...");
        }

        if (step == 12) {
            timer = System.currentTimeMillis() + 2000;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("What???");
            lbl_neuron_right.setText("");
        }
        if (step == 13) {
            timer = System.currentTimeMillis() + 4000;
            bleft = false;
            bright = true;
            lbl_neuron_left.setText("");
            lbl_neuron_right.setText("you really want\n us to work together?");
        }
        if (step == 14) {
            timer = System.currentTimeMillis() + 4000;
            bleft = true;
            bright = false;
            lbl_neuron_left.setText("Maybe... we could... ");
            lbl_neuron_right.setText("");
        }
        if (step == 15) {
            timer = System.currentTimeMillis() + 4000;
            bleft = false;
            bright = true;
            lbl_neuron_left.setText("");
            lbl_neuron_right.setText("Reconcile on today's activity?");
        }
        if (step == 16) {

            timer = System.currentTimeMillis() + 4000;
            bleft = true;
            bright = true;
            lbl_neuron_left.setText("Let's see if we can!");
            lbl_neuron_right.setText("Let's see if we can!");
        }
        if (step == 17) {


            game.getScreenManager().pushScreen("menu", "blending_transition");
        }

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


    }

    @Override
    public void hide() {


    }


}

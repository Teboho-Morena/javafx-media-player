package com.example.mediaplayer;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController implements Initializable {

    @FXML
    private MediaView mediaView;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button browseButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label volume;

    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean running;
    private Timer timer;
    private TimerTask timerTask;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(volumeSlider.getValue() * 0.01));
    }

    public void beginTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                progressBar.setProgress(current/end);

                if (current/end == 1) {
                    cancelTimer();
                }
            }
            Slider progressSlider = new Slider();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                progressSlider.setValue(mediaPlayer.getCurrentTime().toSeconds());
            }));
        };

        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void play(ActionEvent event) {
        beginTimer();
        mediaPlayer.play();
    }

    public void pause(ActionEvent event) {
        cancelTimer();
        mediaPlayer.pause();
    }

    public void stop(ActionEvent event) {
        progressBar.setProgress(0);
        mediaPlayer.stop();
    }

    /*
    some videos play but with no sound
    some songs & other videos refuse to play
     */
    public void browse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Media");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Media Files", "*.mp3", "*.mp4")
        );

        File selectedFile = fileChooser.showOpenDialog(HelloApplication.getStage());

        if (selectedFile != null) {
            media = new Media(selectedFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }
}
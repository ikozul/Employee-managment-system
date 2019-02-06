/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Ivan Kožul
 */
public class ClockThread extends TimerTask {

    private Label LabelTimeInThread;
    public ClockThread(Label LabelCurrentTime) {
        this.LabelTimeInThread = LabelCurrentTime;
    }

    @Override
    public void run() {

        Platform.runLater(() -> {
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateHelper.GetDateTimeFormat());
            String timeTextFormat = formatter.format(currentTime);
            LabelTimeInThread.setText(timeTextFormat);
        });

    }

}

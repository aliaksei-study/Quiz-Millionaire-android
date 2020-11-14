package com.example.quizmillionaire.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.quizmillionaire.R;
import com.example.quizmillionaire.model.Answer;
import com.example.quizmillionaire.model.Question;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnswerHistogramDialog extends DialogFragment {
    private Map<Long, Integer> answerHistogram;
    private Question currentQuestion;

    public AnswerHistogramDialog(Map<Long, Integer> answerHistogram, Question currentQuestion) {
        this.answerHistogram = answerHistogram;
        this.currentQuestion = currentQuestion;
    }

    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        View statisticsDialogLayout = getActivity().getLayoutInflater().inflate(R.layout.statistics_dialog, null);
        GraphView statisticsView = statisticsDialogLayout.findViewById(R.id.statistics_graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(fillDataPointArray(findTotalValueInHistogram()));
        series.setSpacing(20);
        statisticsView.addSeries(series);
        statisticsView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    if(value == 0.d) {
                        return "";
                    } else {
                        return String.valueOf((int)value);
                    }
                } else {
                    return super.formatLabel(value, isValueX) + " %";
                }
            }
        });
        statisticsView.getViewport().setYAxisBoundsManual(true);
        statisticsView.getViewport().setMinY(0);
        statisticsView.getViewport().setMaxY(100);
        statisticsView.getViewport().setXAxisBoundsManual(true);
        statisticsView.getViewport().setMinX(0);
        statisticsView.getViewport().setMaxX(4);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialogAlert = builder.create();
        dialogAlert.setTitle("Статистика ответов");
        dialogAlert.setView(statisticsDialogLayout);
        dialogAlert.setButton(-1, "Продолжить", (dialog, id) -> dialog.cancel());

        return dialogAlert;
    }

    public int findTotalValueInHistogram() {
        int totalValue = 0;
        for(Map.Entry<Long, Integer> entry: answerHistogram.entrySet()) {
            totalValue += entry.getValue();
        }
        return totalValue;
    }

    public DataPoint[] fillDataPointArray(int histogramMaxValue) {
        DataPoint[] dataPoints = new DataPoint[4];
        List<Answer> answers = currentQuestion.getAnswers();
        int numberOfQuestion = 1;
        Integer questionAnswers;
        for(Answer answer: answers) {
            questionAnswers = answerHistogram.get(answer.getId());
            if(questionAnswers != null) {
                dataPoints[numberOfQuestion - 1] = new DataPoint(numberOfQuestion++, questionAnswers * 100 / histogramMaxValue);
            } else {
                dataPoints[numberOfQuestion - 1] = new DataPoint(numberOfQuestion++, 0);
            }
        }
        return dataPoints;
    }
}

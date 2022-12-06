package com.example.quizzapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class QuizzActivityFragment extends Fragment {


    View view; //Vì fragment phải ánh xạ lại chính nó.

    private String getSelectedTopicName = "";

    private TextView questions;
    private TextView question;
    private Button option1, option2, option3, option4;
    private AppCompatButton nextBtn;

    private Timer quizzTimer;

    private int totalTimeInMins = 1;

    private int seconds = 0;

    private List<QuestionList> questionList;

    private int currentQuestionPosition = 0;

    private String selectedOptionByUser = "";



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizzActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizzActivityFragment newInstance(String param1, String param2) {
        QuizzActivityFragment fragment = new QuizzActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quizz_activity, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questions = view.findViewById(R.id.questionsNumbers);
        question = view.findViewById(R.id.question);

        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);

        nextBtn = view.findViewById(R.id.nextBtn);

        getParentFragmentManager().setFragmentResultListener("dataFromMenu", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                getSelectedTopicName = bundle.getString("selectedTopicName");
                TextView selectedTopicName = view.findViewById(R.id.topicName);
                selectedTopicName.setText(getSelectedTopicName);

                final TextView timer = view.findViewById(R.id.timer);

                questionList = QuestionsBank.getQuestions(getSelectedTopicName);

                startTimer(timer);

                questions.setText((currentQuestionPosition + 1) + "/" + questionList.size());
                question.setText(questionList.get(0).getQuestion());
                option1.setText(questionList.get(0).getOption1());
                option2.setText(questionList.get(0).getOption2());
                option3.setText(questionList.get(0).getOption3());
                option4.setText(questionList.get(0).getOption4());

                option1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedOptionByUser.isEmpty()){
                            selectedOptionByUser = option1.getText().toString();

                            option1.setBackgroundResource(android.R.color.holo_red_dark);

                            revealAnswer();

                            questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                        }
                    }
                });

                option2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedOptionByUser.isEmpty()){
                            selectedOptionByUser = option2.getText().toString();

                            option2.setBackgroundResource(android.R.color.holo_red_dark);

                            revealAnswer();

                            questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                        }
                    }
                });

                option3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedOptionByUser.isEmpty()){
                            selectedOptionByUser = option3.getText().toString();

                            option3.setBackgroundResource(android.R.color.holo_red_dark);

                            revealAnswer();

                            questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                        }
                    }
                });

                option4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedOptionByUser.isEmpty()){
                            selectedOptionByUser = option4.getText().toString();

                            option4.setBackgroundResource(android.R.color.holo_red_dark);

                            revealAnswer();

                            questionList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                        }
                    }
                });

                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedOptionByUser.isEmpty()) {
                            Toast.makeText(getActivity(),"Plase choose answer",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            changeNextQuestion();
                        }
                    }
                });
            }
        });
    }

    private void changeNextQuestion(){
        currentQuestionPosition++;
        if((currentQuestionPosition+1) == questionList.size()){
            nextBtn.setText("Submit Quiz");
        }
        if(currentQuestionPosition < questionList.size()){
            selectedOptionByUser = "";
            option1.setBackgroundResource(R.color.purple_700);
            option2.setBackgroundResource(R.color.purple_700);
            option3.setBackgroundResource(R.color.purple_700);
            option4.setBackgroundResource(R.color.purple_700);

            questions.setText((currentQuestionPosition + 1) + "/" + questionList.size());
            question.setText(questionList.get(currentQuestionPosition).getQuestion());
            option1.setText(questionList.get(currentQuestionPosition).getOption1());
            option2.setText(questionList.get(currentQuestionPosition).getOption2());
            option3.setText(questionList.get(currentQuestionPosition).getOption3());
            option4.setText(questionList.get(currentQuestionPosition).getOption4());
        }
        else{
            Intent intent = new Intent(getActivity(), QuizzResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            intent.putExtra("incorrect", getInCorrectAnswers());
            startActivity(intent);

            getActivity().finish();
        }
    }

    private void startTimer(TextView timerTextView) {
        quizzTimer = new Timer();

        quizzTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                }
                else if(seconds == 0 && totalTimeInMins == 0){
                    quizzTimer.purge();
                    quizzTimer.cancel();

                    Toast.makeText(getActivity(), "TimeOver", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), QuizzResults.class);
                    intent.putExtra("Correct", getCorrectAnswers());
                    intent.putExtra("Incorrect", getInCorrectAnswers());
                    startActivity(intent);

                    getActivity().finish();
                }
                else{
                    seconds--;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1){
                            finalMinutes = "0" + finalMinutes;
                        }
                        if(finalSeconds.length() == 1){
                            finalSeconds = "0" + finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + " : " + finalSeconds);
                    }
                });
            }
        }, 1000, 1000);
    }

    private int getCorrectAnswers() {
        int correctAnswers = 0;

        for(int i = 0; i<questionList.size(); i++){
            final String getUserSelectedAnswer = questionList.get(i).getUserSelectedAnswer();
            final String getAnswer = questionList.get(i).getAnswer();

            if(getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers ++;

            }
        }
        return  correctAnswers;
    }

    private int getInCorrectAnswers() {
        int correctAnswers = 0;

        for(int i = 0; i<questionList.size(); i++){
            final String getUserSelectedAnswer = questionList.get(i).getUserSelectedAnswer();
            final String getAnswer = questionList.get(i).getAnswer();

            if(!getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers ++;

            }
        }
        return  correctAnswers;
    }

    public void revealAnswer(){
        final String getAnswer = questionList.get(currentQuestionPosition).getAnswer();
        if(option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(android.R.color.holo_green_dark);
        }
        else if(option2.getText().toString().equals(getAnswer)){
            option2.setBackgroundResource(android.R.color.holo_green_dark);
        }
        else if(option3.getText().toString().equals(getAnswer)){
            option3.setBackgroundResource(android.R.color.holo_green_dark);
        }
        else if(option4.getText().toString().equals(getAnswer)){
            option4.setBackgroundResource(android.R.color.holo_green_dark);
        }
    }
}
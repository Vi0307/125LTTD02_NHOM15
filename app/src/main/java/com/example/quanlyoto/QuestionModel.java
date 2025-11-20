package com.example.quanlyoto;

public class QuestionModel {

    private String question, A, B, C, D, correct;

    public QuestionModel(String question, String a, String b, String c, String d, String correct) {
        this.question = question;
        A = a;
        B = b;
        C = c;
        D = d;
        this.correct = correct;
    }

    public String getQuestion() { return question; }
    public String getA() { return A; }
    public String getB() { return B; }
    public String getC() { return C; }
    public String getD() { return D; }
    public String getCorrect() { return correct; }
}

package nicolas.johan.iem.pokecard.pojo.Model;

/**
 * Created by iem on 23/01/2018.
 */

public class QuestionGameModel {
    public String question;
    public String correct;

    public QuestionGameModel(String question, String correct) {
        this.question = question;
        this.correct = correct;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}

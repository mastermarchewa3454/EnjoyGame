using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System.Text.RegularExpressions;

/// <summary>
/// Gets questions and checks answers
/// </summary>
public class TextboxAnswer : MonoBehaviour
{

    [SerializeField]
    private GameObject inputField;
    List<Quest> quests = new List<Quest>();
    static Quest selectedq;
    [SerializeField]
    private GameObject changingtext;
    int qnNo;

    /// <summary>
    /// Checks if users answer is correct
    /// </summary>
    /// 
    public void Start()
    {
        if (PlayerPrefs.GetInt("customLobby") == 1)
        {
            qnNo = PlayerPrefs.GetInt("level") / 3 - 1;
            string question = PlayerPrefs.GetString("qns" + qnNo);
            DisplayQuestion(question);
        }
        else
        {
            CSVQuestion();
            DisplayQuestion();
        }
    }

    /// <summary>
    /// Gets questions from CSV file
    /// </summary>
    public void CSVQuestion()
    {
        TextAsset questdata = Resources.Load<TextAsset>("questdata");
        string[] data = questdata.text.Split(new char[] { '\n' });

        for (int i = 0; i < data.Length - 1; i++)
        {
            string[] row = data[i].Split(';');
            Quest q = new Quest();
            int.TryParse(row[0], out q.QuestionID);
            int.TryParse(row[1], out q.DifficultyLevel);
            q.Question = row[2];
            q.Answer = row[3];
            quests.Add(q);
        }
    }

    /// <summary>
    /// Filters questions based on difficulty
    /// </summary>
    /// <param name="q">List of questions</param>
    /// <returns>FIltered list of questions</returns>
    public List<Quest> FilterQuestion(List<Quest> q)
    {
        List<Quest> filteredquests = new List<Quest>();
        string difficulty = PlayerPrefs.GetString("difficulty");
        int lvl;

        if (difficulty.Equals("Easy"))
        {
            lvl = 1;
        }
        else if (difficulty.Equals("Medium"))
        {
            lvl = 2;
        }
        else
        {
            lvl = 3;
        }

        for (int i = 0; i < q.Count; i++)
        {
            if (q[i].DifficultyLevel == lvl)
            {
                filteredquests.Add(q[i]);
            }
        }
        return filteredquests;
    }

    /// <summary>
    /// Picks a question based on the filtered questions
    /// </summary>
    /// <param name="q">List of questions</param>
    /// <returns>Question ID</returns>
    public int PickRandomQuestion(List<Quest> q)
    {
        List<int> questions = new List<int>();
        for (int k = 0; k < q.Count; k++)
        {
            questions.Add(q[k].QuestionID);
            Debug.Log(q[k].QuestionID);
        }
        int randomquestion = questions[Random.Range(0, questions.Count)];
        return randomquestion;
    }

    /// <summary>
    /// Displayes selected question
    /// </summary>
    /// <param name="question"></param>
    public void DisplayQuestion(string question = null)
    {
        if (question == null)
        {
            List<Quest> filteredquests = FilterQuestion(quests);
            int questID = PickRandomQuestion(filteredquests);
            for (int j = 0; j < filteredquests.Count; j++)
            {
                if (questID == filteredquests[j].QuestionID)
                {
                    selectedq = filteredquests[j];
                    Debug.Log(selectedq.Answer);
                    break;
                }
            }

            question = selectedq.Question;
        }
        else
        {
            Debug.Log(PlayerPrefs.GetString("ans" + qnNo));
        }
        changingtext.GetComponent<Text>().text = question;
        
    }

    /// <summary>
    /// Checks user's answer against correct answer
    /// </summary>
    public void CheckUserAnswer()
    {
        string correctAnswer;

        if (PlayerPrefs.GetInt("customLobby") == 1)
        {
            correctAnswer = PlayerPrefs.GetString("ans" + qnNo);
        }
        else
        {
            correctAnswer = selectedq.Answer;
        }

        string userAnswer = inputField.GetComponent<Text>().text;
        userAnswer = Regex.Replace(userAnswer, @"\s+", "");
        correctAnswer = Regex.Replace(correctAnswer, @"\s+", "");
        Debug.Log(userAnswer);
        Debug.Log(correctAnswer);
        string difficulty = PlayerPrefs.GetString("difficulty", "easy").ToLower();
        //if (userAnswer.CompareTo(selectedq.Answer) == 0)
        if (string.Equals(userAnswer, correctAnswer))
        {
            PlayerPrefs.SetInt(difficulty + "Correct", PlayerPrefs.GetInt(difficulty + "Correct", 0) + 1);
            SceneManager.LoadScene("AnswerCorrect");
        }
        else
        {
            PlayerPrefs.SetInt(difficulty + "Wrong", PlayerPrefs.GetInt(difficulty + "Wrong", 0) + 1);
            SceneManager.LoadScene("AnswerWrong");
        }
    }
}

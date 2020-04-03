using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

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
    /// <summary>
    /// Checks if users answer is correct
    /// </summary>
    /// 

    public void Start()
    {
        CSVQuestion();
        DisplayQuestion();
    }

    public void CSVQuestion()
    {
        TextAsset questdata = Resources.Load<TextAsset>("questdata");
        string[] data = questdata.text.Split(new char[] { '\n' });

        for (int i = 0; i < data.Length - 1; i++)
        {
            string[] row = data[i].Split(new char[] { ';' });
            Quest q = new Quest();
            int.TryParse(row[0], out q.QuestionID);
            int.TryParse(row[1], out q.DifficultyLevel);
            q.Question = row[2];
            q.Answer = row[3];
            quests.Add(q);
        }
    }

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

    public int PickRandomQuestion(List<Quest> q)
    {
        List<int> questions = new List<int>();
        for (int k = 0; k < q.Count; k++)
        {
            questions.Add(q[k].QuestionID);
        }
        int randomquestion = questions[Random.Range(0, questions.Count)];
        return randomquestion;
    }

    public void DisplayQuestion()
    {
        string question;
        List<Quest> filteredquests = FilterQuestion(quests);
        int questID = PickRandomQuestion(filteredquests);
        Debug.Log(questID);
        for (int j = 0; j < filteredquests.Count; j++)
        {
            if (questID == filteredquests[j].QuestionID)
            {
                selectedq = filteredquests[j];
                break;
            }
        }
        question = selectedq.Question;
        changingtext.GetComponent<Text>().text = question;
        Debug.Log(questID);
        Debug.Log("Answer is:" + selectedq.Answer);
    }


    public void CheckUserAnswer()
    {
        
        string userAnswer = inputField.GetComponent<Text>().text;
        Debug.Log(userAnswer);
        Debug.Log(selectedq.Answer);
        string difficulty = PlayerPrefs.GetString("difficulty", "easy").ToLower();
        //if (userAnswer.CompareTo(selectedq.Answer) == 0)
        if (string.Equals(userAnswer, selectedq.Answer))
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

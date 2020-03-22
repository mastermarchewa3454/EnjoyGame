using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class TextboxAnswer : MonoBehaviour
{
    public string userAnswer;
    public GameObject inputField;

    public void CheckUserAnswer()
    {
        userAnswer = inputField.GetComponent<Text>().text;
        string difficulty = PlayerPrefs.GetString("difficulty", "easy");
        if (string.Equals(userAnswer, "1"))
        {
            PlayerPrefs.SetInt(difficulty + "Correct", PlayerPrefs.GetInt(difficulty + "Correct", 0) + 1);
            SceneManager.LoadScene("AnswerCorrect");
        }
        else
        {
            PlayerPrefs.SetInt(difficulty + "Wrong", PlayerPrefs.GetInt(difficulty + "easyWrong", 0) + 1);
            SceneManager.LoadScene("AnswerWrong");
        }
    }

    public void GetBack()
    {
        SceneManager.LoadScene("Item Choice");
    }
}

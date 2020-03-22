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
    private string userAnswer;
    [SerializeField]
    private GameObject inputField;

    /// <summary>
    /// Checks if users answer is correct
    /// </summary>
    public void CheckUserAnswer()
    {
        userAnswer = inputField.GetComponent<Text>().text;
        string difficulty = PlayerPrefs.GetString("difficulty", "easy").ToLower();
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
}

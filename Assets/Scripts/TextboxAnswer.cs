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
        if (string.Equals(userAnswer, "1"))
        {
            SceneManager.LoadScene("AnswerCorrect");
        }
        else
            SceneManager.LoadScene("AnswerWrong");
    }

    public void GetBack()
    {
        SceneManager.LoadScene("Item Choice");
    }
}

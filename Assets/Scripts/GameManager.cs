using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    [SerializeField]
    private InputField questionInput;
    [SerializeField]
    private InputField answerInput;
    [SerializeField]
    private Button submitButton;

    public void getQuestion(string question)
    {
        Debug.Log("Your question is " + question);
    }

    public void getAnswer(string answer)
    {
        Debug.Log("Your answer is " + answer);
    }

    public void OnMouseEnter()
    {
        Debug.Log("Button Selected");
        questionInput.text = "";
        answerInput.text = "";
    }
}

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{

    public Text counterText; 
    public int counterValue=1;

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
        if (counterValue == 20)
        {
            return;
        }
        else
        {
            bool questionBoolean = string.IsNullOrEmpty(questionInput.text);
            bool answerBoolean = string.IsNullOrEmpty(answerInput.text);
        

            if(questionBoolean || answerBoolean)
            {
                Debug.Log("Please complete all fields");
            }
            else
            {
                Debug.Log("Button Selected");
                questionInput.text = "";
                answerInput.text = "";
                counterValue++;  
                counterText.text = counterValue.ToString() + "/20";

            }
        }
    }
}
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;

/// <summary>
/// Class to contain the functions required during custom creation lobby
/// </summary>
public class GameManager : MonoBehaviour
{
    /// <summary>
    /// A Text variable that is a Unity variable to store the counter text to output for users to see.
    /// </summary>
    public Text counterText; 
    public int counterValue=1;

    /// <summary>
    /// InputField Unity variables to store the questions and answers that are being created by the user.
    /// </summary>
    [SerializeField]
    private InputField questionInput;
    [SerializeField]
    private InputField answerInput;
    [SerializeField]
    private Button submitButton;


    public DBCustomLobbyManager db;
    public string[] questionArr = new string[20];
    public string[] answerArr = new string[20];


    public void Start()
    {
        db = FindObjectOfType<DBCustomLobbyManager>();
    }




    /// <summary>
    /// Method to get question from the SerializeField questionInput
    /// </summary>
    /// <param name="question"> To store the question from the user that will be achieved from questionInput and outputted.</param>
    public void getQuestion(string question)
    {
        Debug.Log("Your question is " + question);
        questionArr[counterValue - 1] = question;
    }
    /// <summary>
    /// Method to get answer from the SerializeField questionInput
    /// </summary>
    /// <param name="answer"> To store the answer from the user that will be achieved from answerInput and outputted.</param>
    public void getAnswer(string answer)
    {
        Debug.Log("Your answer is " + answer);
        answerArr[counterValue - 1] = answer;
    }
    
    /// <summary>
    /// On mouse click function for clicking on button.
    /// Checks if the questionField or the answerField is empty
    /// If empty, and output will be shown and the user will have to enter all fields before 
    /// proceeeding.
    /// </summary>
    public void OnMouseEnter()
    {
        if (counterValue == 20)
        {
            StartCoroutine(db.CreateCustomLobby(questionArr, answerArr, loadingofScenings));
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

    public void loadingofScenings()
    {
        SceneManager.LoadScene("CustomLobbyComplete");
    }

}
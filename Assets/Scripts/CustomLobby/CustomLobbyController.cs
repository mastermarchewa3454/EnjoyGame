using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class CustomLobbyController : MonoBehaviour
{
    /// <summary>
    /// This will create a variable name of db of class DBCustomLobbyManager
    /// </summary>
    DBCustomLobbyManager db;

    /// <summary>
    /// This will create a variable name, errorMsg of class TMP_Text
    /// </summary>
    public TMP_Text errorMsg;

    /// <summary>
    /// This will create a variable name lobbyID of class TMP_InputField
    /// </summary>
    [SerializeField]
    TMP_InputField lobbyId;

    /// <summary>
    /// When the scene is first called, unity will find the component of DBCustomLobbyManager and interact with the database.
    /// </summary>
    void Start()
    {
        db = GetComponent<DBCustomLobbyManager>();
    }

    /// <summary>
    /// This function will get the customlobbyid and verify with the database to ensure that the lobbyID is correct. 
    /// Once verified, the game will then be loaded. Otherwise, it will not be loaded.
    /// </summary>
    public void JoinLobby()
    {
        StartCoroutine(db.JoinCustomLobby(lobbyId.text, questions => {
            if (questions == null)
            {
                Debug.Log("Nope");
                errorMsg.text = "The lobbyID you have entered is incorrect. Please try again.";
            }
            else
            {
                FormatQuestions(questions);
                PlayerPrefs.SetInt("customLobby", 1);
                SceneManager.LoadScene("CharacterSelection");
            }
        }));
    }

    /// <summary>
    /// This function will call a split based on the JSON string obtained in the database and converted it into
    /// a list of strings that will be used by unity in the game process.
    /// </summary>
    /// <param name="questions">questions parameter will be a string of question.</param>
    void FormatQuestions(string questions)
    {
        string[] split = questions.Split(new char[] { '{', '}', ',' }, System.StringSplitOptions.RemoveEmptyEntries);
        for (int i=0; i<split.Length / 2; i++)
        {
            string[] qns = split[2*i].Split('"');
            PlayerPrefs.SetString("qns" + i, qns[3]);
            string[] ans = split[2*i+1].Split('"');
            PlayerPrefs.SetString("ans" + i, ans[3]);
        }
    }
}
